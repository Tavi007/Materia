package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class MateriaEffectFire extends MateriaEffect implements IMateriaEffectRecipe {
	
	public MateriaEffectFire(int[] maxLevel, int[] abilityPower, int[] level, int[] nextLevelAP){
		super("Fire", maxLevel, abilityPower, level, nextLevelAP);
	}
	
	public MateriaEffectFire(){
		super("Fire");
	}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}
}
