package Tavi007.Materia.data.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.MateriaEffectRecipe;
import Tavi007.Materia.network.clientbound.SyncMateriaEffectRecipesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MateriaEffectRecipeManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, MateriaEffectRecipe> registeredEffectRecipes = ImmutableMap.of();

    public MateriaEffectRecipeManager() {
        super(GSON, "materia_effect_recipes");
    }

    public void applySyncPacket(SyncMateriaEffectRecipesPacket packet) {
        this.registeredEffectRecipes = packet.getEffectRecipes();
    }

    public SyncMateriaEffectRecipesPacket getSyncPacket() {
        return new SyncMateriaEffectRecipesPacket(registeredEffectRecipes);
    }

    public List<ResourceLocation> getEffects(List<ItemStack> stacks) {
        List<ResourceLocation> effects = new ArrayList<>();
        ResourceLocation combinedEffect = getMatch(stacks);
        if (combinedEffect != null) {
            effects.add(combinedEffect);
            return effects;
        }

        for (ItemStack stack : stacks) {
            ResourceLocation singleEffect = getMatch(Arrays.asList(stack));
            if (singleEffect != null) {
                effects.add(combinedEffect);
            }
        }
        return effects;
    }

    public ResourceLocation getMatch(List<ItemStack> stacks) {
        for (Entry<ResourceLocation, MateriaEffectRecipe> entry : registeredEffectRecipes.entrySet()) {
            if (entry.getValue().doesInputMatch(stacks)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public MateriaEffectRecipe getRecipePojo(ResourceLocation id) {
        MateriaEffectRecipe pojo = registeredEffectRecipes.get(id);
        if (pojo != null) {
            return registeredEffectRecipes.get(id).copy();
        }
        return null;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, MateriaEffectRecipe> recipesBuilder = ImmutableMap.builder();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                MateriaEffectRecipe recipe = GSON.fromJson(json, MateriaEffectRecipe.class);
                recipesBuilder.put(rl, recipe);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect recipe {}", rl, exception);
            }
        });
        registeredEffectRecipes = recipesBuilder.build();
        Materia.LOGGER.info("Finished loading {} materia effect recipes.", registeredEffectRecipes.size());
    }

}
