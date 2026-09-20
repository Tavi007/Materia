package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.AttackConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.MiningConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.MorphItemConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.SpellConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.StatConfiguration;
import Tavi007.Materia.registries.MateriaEffectConfigurationRegistry;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectConfigurationTypeList {

    public static void init() {
        register("attack", AttackConfiguration.class);
        register("stat", StatConfiguration.class);
        register("morphing", MorphItemConfiguration.class);
        register("mining", MiningConfiguration.class);
        register("spell", SpellConfiguration.class);
    }

    private static void register(String name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        MateriaEffectConfigurationRegistry.register(new ResourceLocation(Materia.MOD_ID, name), clazz);
    }
}
