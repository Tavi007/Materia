package Tavi007.Materia.recipes.effects.configuration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("level")
    LevelConfiguration levelConfiguration;
    String recipe;

    private RecipeConfiguration() {
        super();
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        RecipeConfiguration copy = new RecipeConfiguration();
        copy.setId(getId());
        copy.levelConfiguration = levelConfiguration.copy();
        copy.recipe = new String(recipe);
        return copy;
    }

    @Override
    public boolean isValid() {
        return recipe != null && levelConfiguration.isValid();
    }
}
