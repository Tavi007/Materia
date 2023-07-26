package Tavi007.Materia.effect.configurations;

import java.util.List;

import Tavi007.Materia.effect.configurations.expressions.ArithmeticExpression;
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
        return (int) Math.round(width.evaluate(stacks));
    }

    public int getRange(List<ItemStack> stacks) {
        return (int) Math.round(range.evaluate(stacks));
    }

    public int getHeight(List<ItemStack> stacks) {
        return (int) Math.round(height.evaluate(stacks));
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
