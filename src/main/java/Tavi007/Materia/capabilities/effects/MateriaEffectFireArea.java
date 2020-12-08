package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.MateriaItemArea;
import Tavi007.Materia.items.MateriaItemFire;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

public class MateriaEffectFireArea implements IMateriaEffect, IMateriaEffectRecipe, IMateriaEffectArea  {

	public MateriaItemFire fireMateria;
	public MateriaItemArea areaMateria;
	
	public MateriaEffectFireArea(MateriaItemFire fireMateria, MateriaItemArea areaMateria){
		this.fireMateria = fireMateria;
		this.areaMateria = areaMateria;
	}

	@Override
	public void addAP(int amount) {
		this.fireMateria.addAP(amount);
		this.areaMateria.addAP(amount);
	}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public int getAreaLevel() {
		return this.areaMateria.getLevel();
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
