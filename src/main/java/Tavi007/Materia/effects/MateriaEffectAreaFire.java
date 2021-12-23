package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class MateriaEffectAreaFire extends MateriaEffect implements IMateriaEffectRecipe, IMateriaEffectArea {

	LevelData area = null;
	LevelData fire = null;
	
	public MateriaEffectAreaFire() {
		super(new ResourceLocation(Materia.MOD_ID, "area_fire"));
	}
	
	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public int getAreaLevel() {
		return area.level;
	}

	@Override
	public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
		if(itemstacks.size() != 2) {
			return null;
		}
		
		MateriaEffectAreaFire effect = new MateriaEffectAreaFire();
		boolean hasArea = false;
		boolean hasFire = false;
		for(ItemStack itemstack : itemstacks) {
			if(itemstack.getItem() == ItemList.AREA_MATERIA.get()) {
				effect.area = CapabilityHelper.getLevelData(itemstack);
				hasArea = true;
			} else if(itemstack.getItem() == ItemList.FIRE_MATERIA.get()) {
				effect.fire = CapabilityHelper.getLevelData(itemstack);
				hasFire = true;
			}
		}
		
		if(!(hasFire && hasArea)) {
			return null;
		}
		return effect;
	}

	@Override
	public String getDefaultTooltip() {
		return "" + TextFormatting.RED + "Area Fire " + area.level + "-" + fire.level + TextFormatting.RESET;
	}

	@Override
	public void applyDefaultEffect(ItemStack stack) {
		// TODO Auto-generated method stub
		
	}
}
