package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.objects.items.MateriaItemIce;
import net.minecraft.item.ItemStack;

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
}