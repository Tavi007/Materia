package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface IMateriaEffectRecipe {
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn);
}
