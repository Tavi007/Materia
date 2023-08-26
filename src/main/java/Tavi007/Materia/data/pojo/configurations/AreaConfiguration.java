package Tavi007.Materia.data.pojo.configurations;

import java.util.List;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticEvaluator;
import Tavi007.Materia.data.pojo.configurations.expressions.Expression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AreaConfiguration {

    Expression width;
    Expression range;
    Expression height;

    private AreaConfiguration() {
        super();
    }

    public int getWidth(List<ItemStack> stacks) {
        return (int) Math.round(new ArithmeticEvaluator(width.getFinalExpression(stacks)).parseArithmetic());
    }

    public int getRange(List<ItemStack> stacks) {
        return (int) Math.round(new ArithmeticEvaluator(range.getFinalExpression(stacks)).parseArithmetic());
    }

    public int getHeight(List<ItemStack> stacks) {
        return (int) Math.round(new ArithmeticEvaluator(height.getFinalExpression(stacks)).parseArithmetic());
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
        width = new Expression(buf);
        range = new Expression(buf);
        height = new Expression(buf);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AreaConfiguration otherConfiguration) {
            return ((width == null && otherConfiguration.width == null)
                || width.equals(otherConfiguration.width))
                && ((range == null && otherConfiguration.range == null)
                    || range.equals(otherConfiguration.range))
                && ((height == null && otherConfiguration.height == null)
                    || height.equals(otherConfiguration.height));
        }
        return false;
    }

}
