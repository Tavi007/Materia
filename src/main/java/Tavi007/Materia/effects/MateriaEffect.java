package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.Materia;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class MateriaEffect {
	private ResourceLocation rl = new ResourceLocation(Materia.MOD_ID, "base");
	
	protected MateriaEffect(ResourceLocation rl) {
		this.rl = rl;	
	}

	public MateriaEffect() {
	}

	public String getName() {
		//change to get text from lang file
		return rl.toString();
	}
	
	public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
		return null;
	}
	
	public abstract void applyDefaultEffect(ItemStack stack);
	
	public void applyPickaxeEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applyAxeEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applyShovelEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applyHoeEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applySwordEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applyWandEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	public void applyAccessoryEffect(ItemStack stack) {
		applyDefaultEffect(stack);
	}
	
	public abstract String getDefaultTooltip();
	
	public String getPickaxeTooltip() {
		return getDefaultTooltip();
	}
	public String getAxeTooltip() {
		return getDefaultTooltip();
	}
	public String getShovelTooltip() {
		return getDefaultTooltip();
	}
	public String getHoeTooltip() {
		return getDefaultTooltip();
	}
	public String getSwordTooltip() {
		return getDefaultTooltip();
	}
	public String getWandTooltip() {
		return getDefaultTooltip();
	}
	public String getAccessoryTooltip() {
		return getDefaultTooltip();
	}
}
