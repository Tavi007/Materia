package Tavi007.Materia.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
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

    // testing
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        LevelData data = CapabilityHelper.getLevelData(stack);
        data.addAP(3);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
