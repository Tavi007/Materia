package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.recipes.effects.MateriaEffectTypeRegistry;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.recipes.effects.configuration.AttackConfiguration;
import Tavi007.Materia.recipes.effects.configuration.RecipeConfiguration;
import Tavi007.Materia.recipes.effects.configuration.StatConfiguration;
import net.minecraft.resources.ResourceLocation;

public class MateriaEffectConfigurationTypeList {

    public static void init() {
        register("attack", AttackConfiguration.class);
        register("stat", StatConfiguration.class);
        register("recipe", RecipeConfiguration.class);
    }

    private static void register(String name, Class<? extends AbstractMateriaEffectConfiguration> clazz) {
        MateriaEffectTypeRegistry.register(new ResourceLocation(Materia.MOD_ID, name), clazz);
    }
}