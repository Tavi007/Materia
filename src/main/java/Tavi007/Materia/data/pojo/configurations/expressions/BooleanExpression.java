package Tavi007.Materia.data.pojo.configurations.expressions;

import java.util.List;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class BooleanExpression extends Expression {

    public BooleanExpression(String expression) {
        super(expression);
    }

    public BooleanExpression(String expression, Set<String> inputNames) {
        super(expression, inputNames);
    }

    public BooleanExpression(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public BooleanExpression copy() {
        return new BooleanExpression(new String(expression), copyInputNames());
    }

    public boolean evaluate(List<ItemStack> stacks) {
        return new BooleanEvaluator(getFinalExpression(stacks)).parseBoolean();
    }
}
