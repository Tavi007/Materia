package Tavi007.Materia.recipes.effects.configuration;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;

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

    public String getDescriptionId(String toolSuffix) {
        return Util.makeDescriptionId("materia_effect", id) + "." + toolSuffix;
    }

}
