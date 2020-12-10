package Tavi007.Materia.items;

import java.util.ArrayList;

import Tavi007.Materia.capabilities.effects.MateriaEffect;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaPickaxe extends PickaxeItem implements IMateriaTool {

	ArrayList<MateriaEffect> effectList;
	
	
	public MateriaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		
		//this need to be changed, once the crafting/equipping is working
		this.effectList = new ArrayList<MateriaEffect>();
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Materia Pickaxe");
	}
	
	@Override
	public ArrayList<MateriaEffect> getMateriaEffectList() {
		return this.effectList;
	}

	@Override
	public void setMateriaEffectList(ArrayList<MateriaEffect> list) {
		this.effectList = list;
	}
	
	

}
