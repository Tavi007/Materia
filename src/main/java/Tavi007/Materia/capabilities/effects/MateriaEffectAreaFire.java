package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class MateriaEffectAreaFire extends MateriaEffect implements IMateriaEffectRecipe, IMateriaEffectArea {
	
	public MateriaEffectAreaFire(int[] maxLevel, int[] abilityPower, int[] level, int[] nextLevelAP){
		super("Area Fire", maxLevel, abilityPower, level, nextLevelAP);
	}

	public MateriaEffectAreaFire(){
		super("Area Fire");
	}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public int getAreaLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
}
