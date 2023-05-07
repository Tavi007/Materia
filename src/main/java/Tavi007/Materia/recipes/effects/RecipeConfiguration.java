package Tavi007.Materia.recipes.effects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("level")
    LevelConfiguration levelConfiguration;

    public RecipeConfiguration(ResourceLocation id) {
        super(id);
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }
}
