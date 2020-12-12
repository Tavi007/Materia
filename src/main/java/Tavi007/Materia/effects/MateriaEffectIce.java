package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.IceMateria;
import net.minecraft.item.ItemStack;

public class MateriaEffectIce extends MateriaEffect implements IMateriaEffectRecipe {
	
	public MateriaEffectIce(IceMateria ice){
		super("Ice");
		this.materiaList.add(ice);
	}

	public MateriaEffectIce(){
		super("Ice");
	}
	
	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}
}