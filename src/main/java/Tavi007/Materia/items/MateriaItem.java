package Tavi007.Materia.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MateriaItem extends Item {

	protected final int[] apToNextLevel;
	
	public MateriaItem(Properties properties, int[] apToNextLevel) {
		super(properties);
		this.apToNextLevel = apToNextLevel;
	}
	
	public int[] getApToNextLevel() {
		return apToNextLevel;
	}
	
	//add enchanted glint, if maxLevel is reached
	@Override
	public boolean hasEffect(ItemStack stack) {
		return CapabilityHelper.getLevelData(stack).isMaxLevel(apToNextLevel);
   }
	
	@Override
    @Nullable
    public CompoundNBT getShareTag(ItemStack stack) {
        return stack.getTag();
    }
	
	@Override
    @Nullable
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
    }
	
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !CapabilityHelper.getLevelData(stack).isMaxLevel(apToNextLevel);
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		LevelData data = CapabilityHelper.getLevelData(stack);
		if (data.isMaxLevel(apToNextLevel)) {
			return 0;
		}
        return ( (double) (apToNextLevel[data.level] -  data.ap) ) / apToNextLevel[data.level];
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		LevelData data = CapabilityHelper.getLevelData(stack);
		if(data.isMaxLevel(apToNextLevel)) {
			tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Max Level" + TextFormatting.RESET));
		}
		else {
			tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Level: " + String.valueOf(data.level) + TextFormatting.RESET));
			tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Ap: " + String.valueOf(data.ap) + "/" + String.valueOf(apToNextLevel[data.level]) + TextFormatting.RESET));
		}
	}
	
	// testing
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		LevelData data = CapabilityHelper.getLevelData(stack);
		data.addAP(1, apToNextLevel);
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
