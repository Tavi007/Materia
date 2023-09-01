package Tavi007.Materia.data.pojo.configurations;

import java.util.List;
import java.util.Objects;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AreaConfiguration {

    ArithmeticExpression width;
    ArithmeticExpression range;
    ArithmeticExpression height;

    private AreaConfiguration() {
        super();
    }

    public int getWidth(List<ItemStack> stacks) {
        return width.evaluateToInt(stacks);
    }

    public int getRange(List<ItemStack> stacks) {
        return range.evaluateToInt(stacks);
    }

    public int getHeight(List<ItemStack> stacks) {
        return height.evaluateToInt(stacks);
    }

    public AreaConfiguration copy() {
        AreaConfiguration copy = new AreaConfiguration();
        copy.width = width.copy();
        copy.range = range.copy();
        copy.height = height.copy();
        return copy;
    }

    public boolean isValid() {
        return width.isValid()
            && range.isValid()
            && height.isValid();
    }

    public void encode(FriendlyByteBuf buf) {
        width.encode(buf);
        range.encode(buf);
        height.encode(buf);
    }

    public AreaConfiguration(FriendlyByteBuf buf) {
        width = new ArithmeticExpression(buf);
        range = new ArithmeticExpression(buf);
        height = new ArithmeticExpression(buf);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof AreaConfiguration otherConfiguration) {
            return Objects.equals(width, otherConfiguration.width)
                && Objects.equals(range, otherConfiguration.range)
                && Objects.equals(height, otherConfiguration.height);
        }
        return false;
    }

}
