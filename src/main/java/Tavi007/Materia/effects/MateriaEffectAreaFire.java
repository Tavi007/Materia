package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.AreaMateria;
import Tavi007.Materia.items.FireMateria;
import net.minecraft.item.ItemStack;

public class MateriaEffectAreaFire extends MateriaEffect implements IMateriaEffectRecipe, IMateriaEffectArea {
	
	public MateriaEffectAreaFire(FireMateria fire, AreaMateria area){
		super("Area Fire");
		this.materiaList.add(fire);
		this.materiaList.add(area);
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
