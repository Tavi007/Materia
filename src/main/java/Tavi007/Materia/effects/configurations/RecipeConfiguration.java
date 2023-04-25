package Tavi007.Materia.effects.configurations;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeConfiguration {

    private Item levelItem;
    private ResourceLocation recipe;

    public RecipeConfiguration(String itemRl, String recipe) {
        this.levelItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemRl));
        this.recipe = new ResourceLocation(recipe);
    }

    public int getLevel(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getItem().equals(levelItem)) {
                return CapabilityHelper.getLevelData(stack).level;
            }
        }
        return 0;
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
