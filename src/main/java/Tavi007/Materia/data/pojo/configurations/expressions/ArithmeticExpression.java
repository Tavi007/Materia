package Tavi007.Materia.data.pojo.configurations.expressions;

import java.util.List;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ArithmeticExpression extends Expression {

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
    public ArithmeticExpression copy() {
        return new ArithmeticExpression(new String(expression), copyInputNames());
    }

    public double evaluateToDouble(List<ItemStack> stacks) {
        return new ArithmeticEvaluator(getFinalExpression(stacks)).parseArithmetic();
    }

    public int evaluateToInt(List<ItemStack> stacks) {
        return (int) Math.round(evaluateToDouble(stacks));
    }
}
