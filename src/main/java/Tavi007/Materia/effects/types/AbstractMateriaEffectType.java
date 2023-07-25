package Tavi007.Materia.effects.types;

import Tavi007.Materia.effects.configurations.AbstractMateriaEffectConfiguration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMateriaEffectType<T extends AbstractMateriaEffectConfiguration> {

    private ResourceLocation registryName;

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    public Component getBookDescLang() {
        return Component.translatable(getRegistryName().getNamespace() + ".effect_type." + getRegistryName().getPath());
    }

}
