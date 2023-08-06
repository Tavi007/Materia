package Tavi007.Materia.data.pojo.configurations.expressions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.RegistryHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ArithmeticExpression {

    private String expression;
    private transient Set<String> inputNames;

    private static final Pattern REGEX_PATTERN = Pattern.compile("(?=#)([#a-zA-Z0-9_\\:]*)");

    public ArithmeticExpression(String expression) {
        this.expression = expression;
    }

    private ArithmeticExpression(String expression, Set<String> inputNames) {
        this.expression = expression;
        this.inputNames = inputNames;
    }

    // public for testing
    public Set<String> getInputNames() {
        if (inputNames == null) {
            inputNames = new HashSet<>();
            Matcher matcher = REGEX_PATTERN.matcher(expression);
            while (matcher.find()) {
                String foundInputName = matcher.group().replace("#", "");
                if (!inputNames.contains(foundInputName)) {
                    inputNames.add(foundInputName);
                }
            }
        }
        return inputNames;
    }

    private Map<String, Integer> getInputValues(List<ItemStack> stacks) {
        Map<String, Integer> inputValues = new HashMap<>();
        getInputNames().forEach(input -> {
            Item inputItem = RegistryHelper.getItem(new ResourceLocation(input));
            int value = 0;
            for (ItemStack stack : stacks) {
                if (stack.getItem().equals(inputItem)) {
                    value = CapabilityHelper.getLevelData(stack).getLevel();
                    break;
                }
            }
            inputValues.put(input, value);
        });
        return inputValues;
    }

    public double evaluate(List<ItemStack> stacks) {
        String expressionCopy = new String(expression);
        Map<String, Integer> inputValues = getInputValues(stacks);
        for (String input : inputValues.keySet()) {
            Integer level = inputValues.get(input);
            expressionCopy = expressionCopy.replaceAll("#" + input, String.valueOf(level));
        }
        ExpressionEvaluator evaluator = new ExpressionEvaluator(expressionCopy);
        return evaluator.parseArithmetic();
    }

    public ArithmeticExpression copy() {
        Set<String> inputNamesCopy = null;
        if (getInputNames() != null) {
            inputNamesCopy = new HashSet<>();
            for (String inputName : getInputNames()) {
                inputNamesCopy.add(inputName);
            }
        }
        return new ArithmeticExpression(new String(expression), inputNamesCopy);
    }

    public boolean isValid() {
        return expression != null;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(expression);
    }

    public ArithmeticExpression(FriendlyByteBuf buf) {
        expression = buf.readUtf();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArithmeticExpression otherArithmeticExpression) {
            return (expression == null && otherArithmeticExpression.expression == null)
                || expression.equals(otherArithmeticExpression.expression);
        }
        return false;
    }
}
