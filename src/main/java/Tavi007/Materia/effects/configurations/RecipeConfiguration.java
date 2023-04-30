package Tavi007.Materia.effects.configurations;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration {

    private LevelConfiguration levelConfiguration;
    private ResourceLocation recipe;

    // TODO make NullPointer Safe
    public RecipeConfiguration(JsonObject json) {
        this.levelConfiguration = new LevelConfiguration(json.getAsJsonObject("level"));
        this.recipe = new ResourceLocation(json.get("recipe").getAsString());
    }

    public RecipeConfiguration(LevelConfiguration levelConfiguration, ResourceLocation recipe) {
        this.levelConfiguration = levelConfiguration;
        this.recipe = recipe;
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }

    public List<ItemStack> applyRecipe(List<ItemStack> stacks) {
        List<ItemStack> appliedRecipe = new ArrayList<>();
        for (ItemStack stack : stacks) {
            stack.getItem();
            // TODO: get new item from old item and recipe.
            appliedRecipe.add(new ItemStack(stack.getItem(), stack.getCount()));
        }
        return appliedRecipe;
    }
}
