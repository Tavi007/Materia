package Tavi007.Materia.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MateriaItem extends Item {

    public MateriaItem(Properties properties) {
        super(properties);
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
        return !CapabilityHelper.getLevelData(stack).isMaxLevel();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return CapabilityHelper.getLevelData(stack).getBarWidth();
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return CapabilityHelper.getLevelData(stack).getBarColor();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CapabilityHelper.getLevelData(stack).appendHoverText(tooltip);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
