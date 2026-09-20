package Tavi007.Materia.data.pojo.effects;

import com.google.common.base.Optional;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class AbstractMateriaEffect implements INBTSerializable<CompoundTag> {

    private ResourceLocation id;
    private int tooltipColor;

    protected AbstractMateriaEffect(ResourceLocation id, int tooltipColor) {
        this.id = id;
        this.tooltipColor = tooltipColor;
    }

    protected AbstractMateriaEffect() {
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    protected void setTooltipColor(Integer tooltipColor) {
        this.tooltipColor = tooltipColor;
    }

    public ResourceLocation getId() {
        return id;
    }

    public int getTooltipColor() {
        return Optional.fromNullable(tooltipColor).or(16777215);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", id.toString());
        tag.putInt("tooltip_color", tooltipColor);
        tag.putString("class_name", getClass().getName());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = new ResourceLocation(tag.getString("id"));
        tooltipColor = tag.getInt("tooltip_color");
    }

    public String getDescriptionId(String toolSuffix) {
        return Util.makeDescriptionId("materia_effect", id) + "." + toolSuffix;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
