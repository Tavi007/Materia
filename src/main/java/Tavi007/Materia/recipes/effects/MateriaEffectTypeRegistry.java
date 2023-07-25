package Tavi007.Materia.recipes.effects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import Tavi007.Materia.effects.configurations.AbstractMateriaEffectConfiguration;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectTypeRegistry {

    private static BiMap<ResourceLocation, Class<? extends AbstractMateriaEffectConfiguration>> registeredEffectType = HashBiMap.create();

    public static void register(ResourceLocation name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        registeredEffectType.put(name, clazz);
    }

    public static Class<? extends AbstractMateriaEffectConfiguration> get(ResourceLocation name) {
        return registeredEffectType.get(name);
    }

    public static ResourceLocation get(Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        return registeredEffectType.inverse().get(clazz);
    }

}
