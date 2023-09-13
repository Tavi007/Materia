package Tavi007.Materia.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import net.minecraft.nbt.CompoundTag;

public class MateriaEffectRegistry {

    private static Map<String, Function<CompoundTag, ? extends AbstractMateriaEffect>> registeredEffectClasses = new HashMap<>();

    public static void register(Class<? extends AbstractMateriaEffect> clazz, Function<CompoundTag, ? extends AbstractMateriaEffect> factory) {
        registeredEffectClasses.put(clazz.getName(), factory);
    }

    public static AbstractMateriaEffect get(String className, CompoundTag tag) {
        return registeredEffectClasses.get(className).apply(tag);
    }

}
