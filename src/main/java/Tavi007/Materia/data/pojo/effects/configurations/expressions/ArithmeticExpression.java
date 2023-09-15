package Tavi007.Materia.data.pojo.effects.configurations.expressions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ArithmeticExpression extends Expression {

    private Double value;

    public ArithmeticExpression(String expression) {
        super(expression);
    }

    public ArithmeticExpression(String expression, Set<String> inputNames) {
        super(expression, inputNames);
    }

    public ArithmeticExpression(FriendlyByteBuf buf) {
        super(buf);
        if (buf.readBoolean()) {
            value = buf.readDouble();
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        boolean valueNonNull = value != null;
        buf.writeBoolean(valueNonNull);
        if (valueNonNull) {
            buf.writeDouble(value);
        }
    }

    @Override
    public boolean isValid() {
        if (value != null) {
            return true;
        }
        try {
            evaluateToDouble(Collections.emptyList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double evaluateToDouble(List<ItemStack> stacks) {
        if (value != null) {
            return value;
        }
        if (expression != null) {
            return new ArithmeticEvaluator(getFinalExpression(stacks)).parseArithmetic();
        }
        return 0;
    }

    public int evaluateToInt(List<ItemStack> stacks) {
        return (int) Math.round(evaluateToDouble(stacks));
    }

    public float evaluateToFloat(List<ItemStack> stacks) {
        return (float) Math.round(evaluateToDouble(stacks));
    }
}
