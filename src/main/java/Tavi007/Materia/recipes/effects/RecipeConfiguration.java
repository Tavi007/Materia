package Tavi007.Materia.recipes.effects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("level")
    LevelConfiguration levelConfiguration;

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
        return copy;
    }
}
