package Tavi007.Materia.data.pojo.configurations;

import java.util.Objects;

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
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof AbstractMateriaEffectConfiguration otherConfiguration) {
            return Objects.equals(id, otherConfiguration.id)
                && Objects.equals(tooltipColor, otherConfiguration.tooltipColor);
        }
        return false;
    }

}
