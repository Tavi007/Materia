package Tavi007.Materia.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.ChatFormatting;
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
        return 13 * ((apToNextLevel[data.getLevel()] - data.getAp()) / apToNextLevel[data.getLevel()]);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        LevelData data = CapabilityHelper.getLevelData(stack);
        if (data.isMaxLevel(apToNextLevel)) {
            tooltip.add(Component.literal("Max Level").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.literal("Level: " + (data.getLevel())).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("AP: " + data.getAp() + "/" + apToNextLevel[data.getLevel()]).withStyle(ChatFormatting.GRAY));
        }
    }

    // testing
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        LevelData data = CapabilityHelper.getLevelData(stack);
        data.addAP(7, apToNextLevel);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
