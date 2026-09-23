package Tavi007.Materia.common.data.capabilities;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MateriaLevelData {
    private List<Integer> apToLevelUp;
    private int currentLevel;
    private int currentAp;

    public MateriaLevelData(List<Integer> apToLevelUp, int currentLevel, int currentAp) {
        this.apToLevelUp = apToLevelUp;
        this.currentLevel = currentLevel;
        this.currentAp = currentAp;
    }

    public MateriaLevelData() {
        this.apToLevelUp = new ArrayList<>();
        this.currentLevel = 0;
        this.currentAp = 0;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentAp() {
        return currentAp;
    }

    public boolean isMaxLevel() {
        return currentLevel >= apToLevelUp.size();
    }

    public int getCurrentLevelUpData() {
        if(isMaxLevel()) {
            return 0;
        }
        return apToLevelUp.get(currentLevel);
    }

    // true if level up happens
    public boolean addAP(int amount) {
        if (isMaxLevel()) {
            return false;
        }

        int extraAmount = (currentAp + amount) - getCurrentLevelUpData();
        if (extraAmount >= 0) {
            currentLevel++;
            currentAp = 0;
            addAP(extraAmount);
            return true;
        } else {
            currentAp += amount;
            return false;
        }
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("ap_to_level", new IntArrayTag(apToLevelUp));
        nbt.putInt("level", currentLevel);
        nbt.putInt("ap", currentAp);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        IntArrayTag apToLevelUpTag = (IntArrayTag) nbt.get("ap_to_level");
        IntTag levelTag = (IntTag) nbt.get("level");
        IntTag apTag = (IntTag) nbt.get("ap");
        currentLevel = levelTag.getAsInt();
        currentAp = apTag.getAsInt();
        apToLevelUp = Arrays.stream(apToLevelUpTag.getAsIntArray()).boxed().toList();
    }

    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Level: " + currentLevel).withStyle(ChatFormatting.GRAY));
        if (isMaxLevel()) {
            tooltip.add(Component.literal("Max reached").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.literal("AP: " + currentAp + "/" + getCurrentLevelUpData()).withStyle(ChatFormatting.GRAY));
        }
    }


    public int getBarWidth() {
        if (isMaxLevel()) {
            return 0;
        }
        float fraction = ((float) currentAp) / getCurrentLevelUpData();
        return Math.round(13 * fraction);
    }

    public int getBarColor() {
        return Mth.color(0, 0, 1.0F);
    }
}
