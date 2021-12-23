package Tavi007.Materia.items;

import Tavi007.Materia.effects.MateriaEffect;
import net.minecraft.item.ItemStack;

public interface IMateriaTool {
	public int[] getTopCollectionSizes();
	public int[] getBotCollectionSizes();
	public String getEffectTooltip(MateriaEffect effect);
	public void applyMateriaEffect(ItemStack stack, MateriaEffect effect);
}
