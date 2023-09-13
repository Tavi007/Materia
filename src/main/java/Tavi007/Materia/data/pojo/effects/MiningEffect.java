package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class MiningEffect extends AbstractMateriaEffect {

    AreaEffect area;
    boolean veinMiner;

    public MiningEffect(ResourceLocation id, int tooltipColor, AreaEffect area, boolean veinMiner) {
        super(id, tooltipColor);
        this.area = area;
        this.veinMiner = veinMiner;
    }

    public MiningEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public int getRange() {
        return area.getRange();
    }

    public int getHeight() {
        return area.getHeight();
    }

    public int getWidth() {
        return area.getWidth();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putBoolean("vein_miner", veinMiner);
        tag.put("area", area.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        veinMiner = tag.getBoolean("vein_miner");
        area = new AreaEffect(0, 0, 0);
        area.deserializeNBT(tag.getCompound("area"));
    }
}
