package Tavi007.Materia.effects;

import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;

public interface IRecipeEffect {

    public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn);
}
