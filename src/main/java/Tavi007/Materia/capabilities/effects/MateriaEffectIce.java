package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.MateriaItemIce;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

public class MateriaEffectIce implements IMateriaEffect, IMateriaEffectRecipe {

	public MateriaItemIce iceMateria;
	
	public MateriaEffectIce(MateriaItemIce iceMateria){
		this.iceMateria = iceMateria;
	}

	@Override
	public void addAP(int amount) {this.iceMateria.addAP(amount);}

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