package Tavi007.Materia.recipes.effects.configuration;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("level")
    LevelConfiguration levelConfiguration;
    String recipe;

    private RecipeConfiguration() {
        super();
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        RecipeConfiguration copy = new RecipeConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.levelConfiguration = levelConfiguration.copy();
        copy.recipe = new String(recipe);
        return copy;
    }

    @Override
    public boolean isValid() {
        return recipe != null && levelConfiguration.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        levelConfiguration.encode(buf);
        buf.writeUtf(recipe);
    }

    public RecipeConfiguration(FriendlyByteBuf buf) {
        super(buf);
        levelConfiguration = new LevelConfiguration(buf);
        recipe = buf.readUtf();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RecipeConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((levelConfiguration == null && otherConfiguration.levelConfiguration == null)
                    || levelConfiguration.equals(otherConfiguration.levelConfiguration))
                && ((recipe == null && otherConfiguration.recipe == null)
                    || recipe.equals(otherConfiguration.recipe));
        }
        return false;
    }

    public List<ItemStack> applyRecipe(List<ItemStack> stackIn) {
        List<ItemStack> stackOut = new ArrayList<ItemStack>();

        return stackOut;
    }
}
