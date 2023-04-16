package Tavi007.Materia.items;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MateriaItem extends Item {

    protected final int[] apToNextLevel;

    public MateriaItem(Properties properties, int[] apToNextLevel) {
        super(properties);
        this.apToNextLevel = apToNextLevel;
    }

    public int[] getApToNextLevel() {
        return apToNextLevel;
    }

    @Override
    @Nullable
    public CompoundTag getShareTag(ItemStack stack) {
        return stack.getTag();
    }

    @Override
    @Nullable
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.setTag(nbt);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !CapabilityHelper.getLevelData(stack).isMaxLevel(apToNextLevel);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        LevelData data = CapabilityHelper.getLevelData(stack);
        if (data.isMaxLevel(apToNextLevel)) {
            return 0;
        }
        return (apToNextLevel[data.level] - data.ap) / apToNextLevel[data.level];
    }

    // @Override
    // public void addInformation(ItemStack stack, @Nullable Level worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    // LevelData data = CapabilityHelper.getLevelData(stack);
    // if (data.isMaxLevel(apToNextLevel)) {
    // tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Max Level" + TextFormatting.RESET));
    // } else {
    // tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Level: " + String.valueOf(data.level) + TextFormatting.RESET));
    // tooltip.add(new StringTextComponent(
    // "" + TextFormatting.GRAY + "Ap: " + String.valueOf(data.ap) + "/" + String.valueOf(apToNextLevel[data.level]) + TextFormatting.RESET));
    // }
    // }

    // testing
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        LevelData data = CapabilityHelper.getLevelData(stack);
        data.addAP(1, apToNextLevel);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
