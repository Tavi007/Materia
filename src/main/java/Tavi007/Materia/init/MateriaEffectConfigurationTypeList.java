package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.effects.configurations.AttackConfiguration;
import Tavi007.Materia.effects.configurations.MiningConfiguration;
import Tavi007.Materia.effects.configurations.RecipeConfiguration;
import Tavi007.Materia.effects.configurations.StatConfiguration;
import Tavi007.Materia.recipes.effects.MateriaEffectTypeRegistry;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectConfigurationTypeList {

    public static void init() {
        register("attack", AttackConfiguration.class);
        register("stat", StatConfiguration.class);
        register("recipe", RecipeConfiguration.class);
        register("mining", MiningConfiguration.class);
    }

    private static void register(String name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        MateriaEffectTypeRegistry.register(new ResourceLocation(Materia.MOD_ID, name), clazz);
    }
}
