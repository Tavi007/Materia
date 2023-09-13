package Tavi007.Materia.data.pojo.effects.configurations.expressions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class BooleanExpression extends Expression {

    private Boolean value;

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
    public boolean isValid() {
        try {
            evaluate(Collections.emptyList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean evaluate(List<ItemStack> stacks) {
        return Optional.ofNullable(value).orElse(new BooleanEvaluator(getFinalExpression(stacks)).parseBoolean());
    }
}
