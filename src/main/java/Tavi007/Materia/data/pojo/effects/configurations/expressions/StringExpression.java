package Tavi007.Materia.data.pojo.effects.configurations.expressions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class StringExpression extends Expression {

    private String value;

    private StringExpression(String expression, String value) {
        super(expression);
        this.value = value;
    }

    public StringExpression(String expression, Set<String> inputNames) {
        super(expression, inputNames);
    }

    public StringExpression(FriendlyByteBuf buf) {
        super(buf);
        if (buf.readBoolean()) {
            value = buf.readUtf();
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        boolean valueNonNull = value != null;
        buf.writeBoolean(valueNonNull);
        if (valueNonNull) {
            buf.writeUtf(value);
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

    public String evaluate(List<ItemStack> stacks) {
        if (value != null) {
            return value;
        }
        if (expression != null) {
            return getFinalExpression(stacks);
        }
        return "";
    }
}
