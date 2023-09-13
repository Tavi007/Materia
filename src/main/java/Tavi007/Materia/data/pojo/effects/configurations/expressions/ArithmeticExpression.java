package Tavi007.Materia.data.pojo.effects.configurations.expressions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    }

    @Override
    public boolean isValid() {
        try {
            evaluateToDouble(Collections.emptyList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double evaluateToDouble(List<ItemStack> stacks) {
        return Optional.ofNullable(value).orElse(new ArithmeticEvaluator(getFinalExpression(stacks)).parseArithmetic());
    }

    public int evaluateToInt(List<ItemStack> stacks) {
        return (int) Math.round(evaluateToDouble(stacks));
    }

    public float evaluateToFloat(List<ItemStack> stacks) {
        return (float) Math.round(evaluateToDouble(stacks));
    }
}
