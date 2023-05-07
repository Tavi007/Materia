package Tavi007.Materia.recipes.effects;

import java.util.Deque;
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
    public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(Materia.MOD_ID, "empty");

    private static ThreadLocal<Deque<MateriaEffectCofigurationContext>> dataContext = new ThreadLocal<Deque<MateriaEffectCofigurationContext>>();

    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations = ImmutableMap.of();

    public MateriaEffectConfigurationManager() {
        super(GSON, "materia_effects");
    }

    private void logLoading(String side, int size, String type) {
        Materia.LOGGER.info(side + " loaded " + size + " materia effect configuration for " + type);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        if (objectIn.remove(EMPTY_RESOURCELOCATION) != null) {
            Materia.LOGGER.warn("Datapack tried to redefine materia effect configuration {}, ignoring it", (Object) EMPTY_RESOURCELOCATION);
        }

        Builder<ResourceLocation, AbstractMateriaEffectConfiguration> effectMapBuilder = ImmutableMap.builder();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                String modid = rl.getNamespace();

                JsonObject jsonObject = json.getAsJsonObject();
                String effectType = jsonObject.get("effect_type").getAsString();

                // TODO resolve effect type by checking the registry

                effectMapBuilder.put(rl, null);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect configuration {}", rl, exception);
            }
        });

        // not sure if empty resourceLocation is necessary...
        effectMapBuilder.put(EMPTY_RESOURCELOCATION, new AbstractMateriaEffectConfiguration(EMPTY_RESOURCELOCATION));

        registeredEffectConfigurations = effectMapBuilder.build();

        logLoading("server", registeredEffectConfigurations.size(), "damage sources");
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
}
