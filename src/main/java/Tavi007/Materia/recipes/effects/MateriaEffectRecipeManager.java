package Tavi007.Materia.recipes.effects;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import Tavi007.Materia.Materia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MateriaEffectRecipeManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, MateriaEffectRecipePojo> registeredEffectRecipes = ImmutableMap.of();

    public MateriaEffectRecipeManager() {
        super(GSON, "materia_effect_recipes");
    }

    public MateriaEffectRecipePojo getConfiguration(ResourceLocation id) {
        return registeredEffectRecipes.get(id).copy();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, MateriaEffectRecipePojo> recipesBuilder = ImmutableMap.builder();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                MateriaEffectRecipePojo recipe = GSON.fromJson(json, MateriaEffectRecipePojo.class);
                recipesBuilder.put(rl, recipe);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect recipe {}", rl, exception);
            }
        });
        registeredEffectRecipes = recipesBuilder.build();
        Materia.LOGGER.info("Finished loading {} materia effect recipes.", registeredEffectRecipes.size());
    }

}
