package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.objects.items.MateriaItemArea;
import Tavi007.Materia.objects.items.MateriaItemFire;
import net.minecraft.item.ItemStack;

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
}
