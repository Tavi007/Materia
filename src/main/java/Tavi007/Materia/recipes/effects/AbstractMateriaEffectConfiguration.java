package Tavi007.Materia.recipes.effects;

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
}
