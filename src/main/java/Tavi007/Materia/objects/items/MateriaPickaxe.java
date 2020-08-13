package Tavi007.Materia.objects.items;

import java.util.ArrayList;

import Tavi007.Materia.effects.IMateriaEffect;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;

public class MateriaPickaxe extends PickaxeItem implements IMateriaTool {

	ArrayList<IMateriaEffect> effectList;
	
	
	public MateriaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public ArrayList<IMateriaEffect> getMateriaEffectList() {
		return this.effectList;
	}

	@Override
	public void setMateriaEffectList(ArrayList<IMateriaEffect> list) {
		this.effectList = list;
	}
	
	

}
