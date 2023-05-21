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

    private void logLoading(String side, int size, String type) {
        Materia.LOGGER.info(side + " loaded " + size + " materia effect configuration for " + type);
    }

    public AbstractMateriaEffectConfiguration getConfiguration(ResourceLocation id) {
        return registeredEffectConfigurations.get(id).copy();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, AbstractMateriaEffectConfiguration> effectMapBuilder = ImmutableMap.builder();
        Map<String, Integer> counterMap = new HashMap<>();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));

                JsonObject jsonObject = json.getAsJsonObject();
                // TODO: add event as api entry point to add other configurations types
                String path = rl.getPath();
                AbstractMateriaEffectConfiguration configuration = null;
                if (path.contains("spells/")) {
                    configuration = loadData(GSON, rl, jsonObject, SpellConfiguration.class);
                    counterMap.put("spell", counterMap.getOrDefault("spell", 0) + 1);
                } else if (path.contains("recipes/")) {
                    configuration = loadData(GSON, rl, jsonObject, RecipeConfiguration.class);
                    counterMap.put("recipe", counterMap.getOrDefault("recipe", 0) + 1);
                } else if (path.contains("stats/")) {
                    configuration = loadData(GSON, rl, jsonObject, StatConfiguration.class);
                    counterMap.put("stat", counterMap.getOrDefault("stat", 0) + 1);
                } else {
                    Materia.LOGGER.warn("Don't know how to handle path: " + path);
                }

                if (configuration != null) {
                    configuration.setId(rl);
                    effectMapBuilder.put(rl, configuration);
                }

            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect configuration {}", rl, exception);
            }
        });

        registeredEffectConfigurations = effectMapBuilder.build();
        counterMap.forEach((type, count) -> logLoading("Server", count, type));
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
