package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.MateriaItemFire;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

public class MateriaEffectFire implements IMateriaEffect, IMateriaEffectRecipe {
	
	public MateriaItemFire fireMateria;
	
	public MateriaEffectFire(MateriaItemFire fireMateria){
		this.fireMateria = fireMateria;
	}

	@Override
	public void addAP(int amount) {this.fireMateria.addAP(amount);}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public INBT writeNBT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readNBT(INBT nbt) {
		// TODO Auto-generated method stub
		
	}
}
