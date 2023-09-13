package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class StatEffect extends AbstractMateriaEffect {

    private Stat stat;
    private float value;

    public StatEffect(ResourceLocation id, int tooltipColor, Stat stat, float value) {
        super(id, tooltipColor);
        this.stat = stat;
        this.value = value;
    }

    public StatEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public Stat getStat() {
        return stat;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("stat", stat.toString());
        tag.putFloat("value", value);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        stat = Stat.valueOf(tag.getString("stat"));
        value = tag.getFloat("value");
    }
}
