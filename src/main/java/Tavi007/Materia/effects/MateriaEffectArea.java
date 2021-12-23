package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class MateriaEffectArea extends MateriaEffect implements IMateriaEffectArea {
	
	LevelData area = null;
	
	public MateriaEffectArea() {
		super(new ResourceLocation(Materia.MOD_ID, "area"));
	}

	@Override
	public int getAreaLevel() {
		return area.level;
	}
	
	@Override
	public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
		if(itemstacks.size() != 1) {
			return null;
		}
		
		MateriaEffectArea effect = new MateriaEffectArea();
		boolean hasArea = false;
		for(ItemStack itemstack : itemstacks) {
			if(itemstack.getItem() == ItemList.AREA_MATERIA.get()) {
				effect.area = CapabilityHelper.getLevelData(itemstack);
				hasArea = true;
			}
		}
		
		if(!hasArea) {
			return null;
		}
		
		return effect;
	}

	@Override
	public String getDefaultTooltip() {
		return "" + TextFormatting.DARK_PURPLE + "Area" + area.level + TextFormatting.RESET;
	}

	@Override
	public String getPickaxeTooltip() {
		return "" + TextFormatting.DARK_PURPLE + "Area Pick " + area.level + TextFormatting.RESET;
	}
}
