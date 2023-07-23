package Tavi007.Materia.capabilities.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class LevelData implements INBTSerializable<CompoundTag> {

    public int level;
    public int ap;

    public LevelData() {
        level = 0;
        ap = 0;
    }

    public LevelData(int level, int ap) {
        this.level = 0;
        this.ap = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getAp() {
        return ap;
    }

    public boolean isMaxLevel(int[] apToNextLevel) {
        return level >= apToNextLevel.length;
    }

    public void addAP(int amount, int[] apToNextLevel) {
        if (isMaxLevel(apToNextLevel)) {
            return;
        }

        int extraAmount = (ap + amount) - apToNextLevel[level];
        if (extraAmount >= 0) {
            level++;
            ap = 0;
            addAP(extraAmount, apToNextLevel);
        } else {
            ap += amount;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("level", level);
        nbt.putInt("ap", ap);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        level = nbt.getInt("level");
        ap = nbt.getInt("ap");
    }
}
