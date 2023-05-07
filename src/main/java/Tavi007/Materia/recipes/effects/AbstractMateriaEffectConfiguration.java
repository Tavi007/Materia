package Tavi007.Materia.recipes.effects;

import com.google.gson.annotations.SerializedName;

import net.minecraft.resources.ResourceLocation;

public class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;
    @SerializedName("effect_type")
    private ResourceLocation effectType;

    protected AbstractMateriaEffectConfiguration(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }
}
