package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.recipes.MateriaEffectRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeTypeList {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
        ForgeRegistries.Keys.RECIPE_SERIALIZERS,
        Materia.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
        ForgeRegistries.Keys.RECIPE_TYPES,
        Materia.MOD_ID);

    public static final String MATERIA_EFFECT_RECIPE = "materia_effect";

    public static final RegistryObject<RecipeType<MateriaEffectRecipe>> APPARATUS_TYPE = RECIPE_TYPES.register(MATERIA_EFFECT_RECIPE,
        () -> new ModRecipeType<>());
    public static final RegistryObject<RecipeSerializer<MateriaEffectRecipe>> APPARATUS_SERIALIZER = RECIPE_SERIALIZERS.register(MATERIA_EFFECT_RECIPE,
        () -> new MateriaEffectRecipe.Serializer());

    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {

        @Override
        public String toString() {
            return ForgeRegistries.RECIPE_TYPES.getKey(this).toString();
        }
    }
}
