package Tavi007.Materia.recipes.effects;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Tavi007.Materia.Materia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MateriaEffectConfigurationManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    private static ThreadLocal<Deque<MateriaEffectCofigurationContext>> dataContext = new ThreadLocal<Deque<MateriaEffectCofigurationContext>>();

    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations = ImmutableMap.of();

    public MateriaEffectConfigurationManager() {
        super(GSON, "effect_configurations");
    }

    public AbstractMateriaEffectConfiguration getConfiguration(ResourceLocation id) {
        return registeredEffectConfigurations.get(id).copy();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, AbstractMateriaEffectConfiguration> effectMapBuilder = ImmutableMap.builder();
        Map<Class<? extends AbstractMateriaEffectConfiguration>, Integer> counterMap = new HashMap<>();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));

                ResourceLocation effectType = getEffectType(json.getAsJsonObject());
                if (effectType == null) {
                    throw new Exception("Could not find 'effect_type' property.");
                }

                Class<? extends AbstractMateriaEffectConfiguration> clazz = MateriaEffectTypeRegistry.get(effectType);
                if (clazz == null) {
                    throw new Exception("Unknown effect type '" + effectType + "'.");
                }

                AbstractMateriaEffectConfiguration configuration = loadData(GSON, rl, json, clazz);
                if (!configuration.isValid()) {
                    throw new Exception("Invalid configuration encountered: " + configuration.toString());
                }

                configuration.setId(rl);
                effectMapBuilder.put(rl, configuration);
                counterMap.put(clazz, counterMap.getOrDefault(clazz, 0) + 1);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect configuration {}", rl, exception);
            }
        });

        registeredEffectConfigurations = effectMapBuilder.build();
        counterMap.forEach((type, count) -> logLoading("Server", count, type.toString()));
    }

    private ResourceLocation getEffectType(JsonObject json) {
        if (json.has("effect_type")) {
            return new ResourceLocation(json.get("effect_type").getAsString());
        }
        return null;
    }

    @Nullable
    private <T> T loadData(Gson gson, ResourceLocation name, JsonElement data, Class<T> classOfT) {
        Deque<MateriaEffectCofigurationContext> que = dataContext.get();
        if (que == null) {
            que = Queues.newArrayDeque();
            dataContext.set(que);
        }

        T ret = null;
        try {
            que.push(new MateriaEffectCofigurationContext(name, false));
            ret = gson.fromJson(data, classOfT);
            que.pop();
        } catch (JsonParseException e) {
            que.pop();
            throw e;
        }
        return ret;
    }

    private void logLoading(String side, int size, String type) {
        Materia.LOGGER.info(side + " loaded " + size + " materia effect configuration for " + type);
    }
}
