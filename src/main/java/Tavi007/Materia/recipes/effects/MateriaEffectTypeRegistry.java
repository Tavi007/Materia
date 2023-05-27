package Tavi007.Materia.recipes.effects;

import java.util.HashMap;
import java.util.Map;

import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectTypeRegistry {

    private static Map<ResourceLocation, Class<? extends AbstractMateriaEffectConfiguration>> registeredEffectType = new HashMap<>();

    public static <T extends AbstractMateriaEffectConfiguration> void register(ResourceLocation name,
            Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        registeredEffectType.put(name, clazz);
    }

    public static Class<? extends AbstractMateriaEffectConfiguration> get(ResourceLocation name) {
        return registeredEffectType.get(name);
    }

}
