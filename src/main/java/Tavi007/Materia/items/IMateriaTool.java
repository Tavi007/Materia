package Tavi007.Materia.items;

import Tavi007.Materia.effects.MateriaEffect;

public interface IMateriaTool {
	public int[] getTopCollectionSizes();
	public int[] getBotCollectionSizes();
	public String getEffectTooltip(MateriaEffect effect);
}
