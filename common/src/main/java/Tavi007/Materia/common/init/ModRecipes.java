package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.recipes.MateriaIncubatorRecipe;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final Supplier<RecipeType<MateriaIncubatorRecipe>> MATERIA_INCUBATOR =
            Suppliers.memoize(() -> new RecipeType<>() {
                @Override
                public String toString() {
                    return Constants.MATERIA_INCUBATOR_RECIPE;
                }
            });
}
