package Tavi007.Materia.effect.configurations;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;
    @SerializedName("tooltip_color")
    private Integer tooltipColor;

    protected AbstractMateriaEffectConfiguration() {
    }

    public abstract AbstractMateriaEffectConfiguration copy();

    public abstract boolean isValid();

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

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id);
        buf.writeInt(tooltipColor);
    };

    public AbstractMateriaEffectConfiguration(FriendlyByteBuf buf) {
        id = buf.readResourceLocation();
        tooltipColor = buf.readInt();
    };

    public String getDescriptionId(String toolSuffix) {
        return Util.makeDescriptionId("materia_effect", id) + "." + toolSuffix;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AbstractMateriaEffectConfiguration otherConfiguration) {
            return ((id == null && otherConfiguration.id == null) || id.equals(otherConfiguration.id))
                && ((tooltipColor == null && otherConfiguration.tooltipColor == null) || tooltipColor.equals(otherConfiguration.tooltipColor));
        }
        return false;
    }

}
