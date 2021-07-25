package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class MateriaEffectFire extends MateriaEffect implements IMateriaEffectRecipe {

	LevelData fire = null;
	
	public MateriaEffectFire() {
		super(new ResourceLocation(Materia.MOD_ID, "fire"));
	}
	
	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}
	

	@Override
	public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
		if(itemstacks.size() != 1) {
			return null;
		}
		
		MateriaEffectFire effect = new MateriaEffectFire();
		boolean hasFire = false;
		for(ItemStack itemstack : itemstacks) {
			if(itemstack.getItem() == ItemList.FIRE_MATERIA.get()) {
				effect.fire = CapabilityHelper.getLevelData(itemstack);
				hasFire = true;
			}
		}
		
		if(!hasFire) {
			return null;
		}
		return effect;
	}

	@Override
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent("" + TextFormatting.RED + "Fire Pick " + fire.level + TextFormatting.RESET));
	}
}
