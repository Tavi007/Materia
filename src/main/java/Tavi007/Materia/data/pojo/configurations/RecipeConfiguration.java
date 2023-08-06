package Tavi007.Materia.data.pojo.configurations;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class RecipeConfiguration extends AbstractMateriaEffectConfiguration {

    ArithmeticExpression level;
    String recipe;

    private RecipeConfiguration() {
        super();
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        RecipeConfiguration copy = new RecipeConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.level = level.copy();
        copy.recipe = new String(recipe);
        return copy;
    }

    @Override
    public boolean isValid() {
        return recipe != null && level.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        level.encode(buf);
        buf.writeUtf(recipe);
    }

    public RecipeConfiguration(FriendlyByteBuf buf) {
        super(buf);
        level = new ArithmeticExpression(buf);
        recipe = buf.readUtf();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RecipeConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((level == null && otherConfiguration.level == null)
                    || level.equals(otherConfiguration.level))
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
