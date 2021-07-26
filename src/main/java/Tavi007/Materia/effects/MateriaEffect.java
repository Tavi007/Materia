package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.Materia;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MateriaEffect {
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
	
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addAxeToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addShovelToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addHoeToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addSwordToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addWandToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addAccessoryToolTip(List<ITextComponent> tooltip) {
		return;
	}
}
