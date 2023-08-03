package Tavi007.Materia.capabilities.level;

import java.util.Arrays;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.common.util.INBTSerializable;

public class LevelData implements INBTSerializable<CompoundTag> {

    private List<Integer> levelUpData;
    private int level;
    private int ap;

    public LevelData(List<Integer> levelUpData) {
        this.levelUpData = levelUpData;
        this.level = 0;
        this.ap = 0;
    }

    public int getLevel() {
        return level + 1;
    }

    public int getAp() {
        return ap;
    }

    public boolean isMaxLevel() {
        return level >= levelUpData.size();
    }

    public int getCurrentLevelUpData() {
        return levelUpData.get(level);
    }

    public void addAP(int amount) {
        if (isMaxLevel()) {
            return;
        }

        int extraAmount = (ap + amount) - getCurrentLevelUpData();
        if (extraAmount >= 0) {
            level++;
            ap = 0;
            addAP(extraAmount);
        } else {
            ap += amount;
        }
    }

    public int getBarWidth() {
        if (isMaxLevel()) {
            return 0;
        }
        float fraction = ((float) getAp()) / getCurrentLevelUpData();
        return Math.round(13 * fraction);
    }

    public int getBarColor() {
        float f = Math.max(0.0F, ((float) ap) / getCurrentLevelUpData());
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Level: " + (getLevel())).withStyle(ChatFormatting.GRAY));
        if (isMaxLevel()) {
            tooltip.add(Component.literal("Max reached").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.literal("AP: " + getAp() + "/" + getCurrentLevelUpData()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putIntArray("level_up_data", levelUpData);
        nbt.putInt("level", level);
        nbt.putInt("ap", ap);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        int[] ints = nbt.getIntArray("level_up_data");
        levelUpData = Arrays.stream(ints).boxed().toList();
        level = nbt.getInt("level");
        ap = nbt.getInt("ap");
    }
}
