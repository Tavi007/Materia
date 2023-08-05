package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effect.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.effect.configurations.AttackConfiguration;
import Tavi007.Materia.effect.configurations.MiningConfiguration;
import Tavi007.Materia.effect.configurations.RecipeConfiguration;
import Tavi007.Materia.effect.configurations.StatConfiguration;
import Tavi007.Materia.registries.MateriaEffectConfigurationRegistry;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectConfigurationTypeList {

    public static void init() {
        register("attack", AttackConfiguration.class);
        register("stat", StatConfiguration.class);
        register("recipe", RecipeConfiguration.class);
        register("mining", MiningConfiguration.class);
    }

    private static void register(String name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        MateriaEffectConfigurationRegistry.register(new ResourceLocation(Materia.MOD_ID, name), clazz);
    }
}
