package Tavi007.Materia.recipes.effects.configuration;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;
    @SerializedName("tooltip_color")
    private Integer tooltipColor;

    protected AbstractMateriaEffectConfiguration() {
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

    public abstract AbstractMateriaEffectConfiguration copy();

    public abstract boolean isValid();

    public String getDescriptionId(String toolSuffix) {
        return Util.makeDescriptionId("materia_effect", id) + "." + toolSuffix;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
