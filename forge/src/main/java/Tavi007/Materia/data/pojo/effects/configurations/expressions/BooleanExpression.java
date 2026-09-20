package Tavi007.Materia.data.pojo.effects.configurations.expressions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class BooleanExpression extends Expression {

    public static final BooleanExpression DEFAULT_TRUE = new BooleanExpression(true);
    public static final BooleanExpression DEFAULT_FALSE = new BooleanExpression(false);

    private Boolean value;

    public BooleanExpression(String expression) {
        super(expression);
    }

    public BooleanExpression(Boolean value) {
        super("");
        this.value = value;
    }

    public BooleanExpression(String expression, Set<String> inputNames) {
        super(expression, inputNames);
    }

    public BooleanExpression(FriendlyByteBuf buf) {
        super(buf);
        if (buf.readBoolean()) {
            value = buf.readBoolean();
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        boolean valueNonNull = value != null;
        buf.writeBoolean(valueNonNull);
        if (valueNonNull) {
            buf.writeBoolean(value);
        }
    }

    @Override
    public boolean isValid() {
        if (value != null) {
            return true;
        }
        try {
            evaluate(Collections.emptyList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean evaluate(List<ItemStack> stacks) {
        if (value != null) {
            return value;
        }
        if (expression != null) {
            return new BooleanEvaluator(getFinalExpression(stacks)).parseBoolean();
        }
        return false;
    }
}
