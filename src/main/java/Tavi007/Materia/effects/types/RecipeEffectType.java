package Tavi007.Materia.effects.types;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.recipes.effects.configuration.RecipeConfiguration;
import net.minecraft.world.item.ItemStack;

public class RecipeEffectType<T extends RecipeConfiguration> extends AbstractMateriaEffectType<T> {

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
