package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class MateriaEffectAreaFire extends MateriaEffect implements IMateriaEffectRecipe, IMateriaEffectArea {

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public int getAreaLevel() {
		return levelList.get(1);
	}

	@Override
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent("" + TextFormatting.RED + "Area Fire" + TextFormatting.RESET));
	};
}
