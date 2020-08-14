package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.objects.items.MateriaItemFire;
import net.minecraft.item.ItemStack;

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
}
