package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class AreaEffect implements INBTSerializable<CompoundTag> {

    int width;
    int range;
    int height;

    public AreaEffect(int width, int range, int height) {
        this.width = width;
        this.range = range;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getRange() {
        return range;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("width", width);
        tag.putInt("range", range);
        tag.putInt("height", height);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        width = tag.getInt("width");
        range = tag.getInt("range");
        height = tag.getInt("height");
    }

}
