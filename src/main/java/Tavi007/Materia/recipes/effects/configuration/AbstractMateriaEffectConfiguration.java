package Tavi007.Materia.recipes.effects.configuration;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;
    @SerializedName("description_id")
    private String descriptionId;

    protected AbstractMateriaEffectConfiguration() {
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public abstract AbstractMateriaEffectConfiguration copy();

    public abstract boolean isValid();

    public String getDescriptionId(IMateriaTool tool) {
        return descriptionId + "." + tool.getDescriptionIdSuffix();
    }

}
