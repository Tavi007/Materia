package Tavi007.Materia.capabilities.magic;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class MagicData implements INBTSerializable<CompoundTag> {

    private int spellIndex = 0;
    private int mana = 0;

    public MagicData() {
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getSpellIndex() {
        return spellIndex;
    }

    public void setSpellIndex(int index) {
        this.spellIndex = index;
    }

    public void incrementSpellIndex() {
        spellIndex++;
    }

    @Override
    public CompoundTag serializeNBT() {
        // fill nbt with data
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("index", spellIndex);
        nbt.putInt("mana", mana);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        spellIndex = nbt.getInt("index");
        mana = nbt.getInt("mana");

    }
}
