package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Tavi007.Materia.data.pojo.effects.MorphItemEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class MorphItemConfiguration extends AbstractMateriaEffectConfiguration {

    ArithmeticExpression level;
    String recipe;

    private MorphItemConfiguration() {
        super();
    }

    @Override
    public MorphItemEffect computeEffect(List<ItemStack> stacks) {
        return new MorphItemEffect(getId(), getTooltipColor(), level.evaluateToInt(stacks), recipe);
    }

    @Override
    public boolean isValid() {
        return recipe != null
            && level != null && level.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        level.encode(buf);
        buf.writeUtf(recipe);
    }

    public MorphItemConfiguration(FriendlyByteBuf buf) {
        super(buf);
        level = new ArithmeticExpression(buf);
        recipe = buf.readUtf();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof MorphItemConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(level, otherConfiguration.level)
                && Objects.equals(recipe, otherConfiguration.recipe);
        }
        return false;
    }

    public List<ItemStack> applyRecipe(List<ItemStack> stackIn) {
        List<ItemStack> stackOut = new ArrayList<ItemStack>();

        return stackOut;
    }
}
