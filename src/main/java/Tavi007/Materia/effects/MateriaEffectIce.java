package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.items.IceMateria;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaEffectIce extends MateriaEffect implements IMateriaEffectRecipe {
	
	public MateriaEffectIce(IceMateria ice){
		super("Ice");
		this.levelList.add(ice.level);
	}

	public MateriaEffectIce(){
		super("Ice");
	}
	
	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent("Ice Pick Effect"));
	};
}