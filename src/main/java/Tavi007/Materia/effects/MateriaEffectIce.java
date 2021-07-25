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

public class MateriaEffectIce extends MateriaEffect implements IMateriaEffectRecipe {
	
	LevelData ice = null;
	
	public MateriaEffectIce() {
		super(new ResourceLocation(Materia.MOD_ID, "ice"));
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
		
		MateriaEffectIce effect = new MateriaEffectIce();
		boolean hasIceData = false;
		for(ItemStack itemstack : itemstacks) {
			if(itemstack.getItem() == ItemList.ICE_MATERIA.get()) {
				effect.ice = CapabilityHelper.getLevelData(itemstack);
				hasIceData = true;
			}
		}
		
		if(!hasIceData) {
			return null;
		}
		
		return effect;
	}

	@Override
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent("" + TextFormatting.BLUE + "Ice Pick " + ice.level + TextFormatting.RESET));
	};
}