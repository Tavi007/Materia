package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class MateriaEffectIce extends MateriaEffect implements IMateriaEffectRecipe {
	
	public MateriaEffectIce(int[] maxLevel, int[] abilityPower, int[] level, int[] nextLevelAP){
		super("Ice");
	}

	public MateriaEffectIce(){
		super("Ice");
	}
	
	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}
}