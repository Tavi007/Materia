package Tavi007.Materia.common.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.common.capabilities.CapabilitiesAccessors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MateriaItem extends Item {

    public MateriaItem(Properties properties) {
        super(properties);
    }

//    @Override
//    @Nullable
//    public CompoundTag getShareTag(ItemStack stack) {
//        return stack.getTag();
//    }
//
//    @Override
//    @Nullable
//    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
//        stack.setTag(nbt);
//    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return !CapabilitiesAccessors.getMateriaLevelData(stack).isMaxLevel();
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return CapabilitiesAccessors.getMateriaLevelData(stack).getBarWidth();
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return CapabilitiesAccessors.getMateriaLevelData(stack).getBarColor();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CapabilitiesAccessors.getMateriaLevelData(stack).appendHoverText(tooltip);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
