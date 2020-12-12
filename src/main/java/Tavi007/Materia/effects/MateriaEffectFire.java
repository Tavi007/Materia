package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.FireMateria;
import net.minecraft.item.ItemStack;

public class MateriaEffectFire extends MateriaEffect implements IMateriaEffectRecipe {
	
	public MateriaEffectFire(FireMateria fire){
		super("Fire");
		this.materiaList.add(fire);
	}
	
	public MateriaEffectFire(){
		super("Fire");
	}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}
}
