package Tavi007.Materia.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class MateriaShovel extends ShovelItem implements IMateriaTool {
	//change these later
	private final int[] topCollectionSizes;
	private final int[] botCollectionSizes;
	
	public MateriaShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, int[] topCollectionSizes, int[] botCollectionSizes) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		if (MateriaToolHelper.isCollectionSizesValid(topCollectionSizes)) {
			this.topCollectionSizes = topCollectionSizes;
		}
		else {
			this.topCollectionSizes = new int[]{0}; // might need to change this to 1
		}
		if (MateriaToolHelper.isCollectionSizesValid(botCollectionSizes)) {
			this.botCollectionSizes = botCollectionSizes;
		}
		else {
			this.botCollectionSizes = new int[]{0};
		}
	}
	
	@Override
	public int[] getTopCollectionSizes() {
		return topCollectionSizes;
	}

	@Override
	public int[] getBotCollectionSizes() {
		return botCollectionSizes;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		MateriaToolHelper.addToolTip(stack, tooltip);
	}

	@Override
	public String getEffectTooltip(MateriaEffect effect) {
		return effect.getShovelTooltip();
	}

	@Override
	public void applyMateriaEffect(ItemStack stack, MateriaEffect effect) {
		effect.applyShovelEffect(stack);
	}
}
