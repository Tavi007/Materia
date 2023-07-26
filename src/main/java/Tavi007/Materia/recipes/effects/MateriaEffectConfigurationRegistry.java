package Tavi007.Materia.recipes.effects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import Tavi007.Materia.effect.configurations.AbstractMateriaEffectConfiguration;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectConfigurationRegistry {

    private static BiMap<ResourceLocation, Class<? extends AbstractMateriaEffectConfiguration>> registeredEffectConfigurations = HashBiMap.create();

    public static void register(ResourceLocation name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        registeredEffectConfigurations.put(name, clazz);
    }

    public static Class<? extends AbstractMateriaEffectConfiguration> get(ResourceLocation name) {
        return registeredEffectConfigurations.get(name);
    }

    public static ResourceLocation get(Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        return registeredEffectConfigurations.inverse().get(clazz);
    }

}
