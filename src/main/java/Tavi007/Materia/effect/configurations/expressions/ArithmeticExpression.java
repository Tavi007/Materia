package Tavi007.Materia.effect.configurations.expressions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<String> inputNames;

    private static final Pattern REGEX_PATTERN = Pattern.compile("(?<=(#):)(\\w+)");

    private ArithmeticExpression(String expression, List<String> inputNames) {
        this.expression = expression;
        this.inputNames = inputNames;
    }

    private List<String> getInputNames() {
        if (inputNames == null) {
            inputNames = new ArrayList<>();
            Matcher matcher = REGEX_PATTERN.matcher(expression);
            while (matcher.find()) {
                inputNames.add(matcher.group());
            }
        }
        return inputNames;
    }

    private Map<String, Integer> getInputValues(List<ItemStack> stacks) {
        Map<String, Integer> inputValues = new HashMap<>();
        getInputNames().forEach(input -> {
            Item inputItem = RegistryHelper.getItem(new ResourceLocation(input));
            stacks.forEach(stack -> {
                if (stack.getItem().equals(inputItem)) {
                    inputValues.put(input, CapabilityHelper.getLevelData(stack).getLevel());
                } else {
                    inputValues.put(input, 0);
                }
            });
        });
        return inputValues;
    }

    public double evaluate(List<ItemStack> stacks) {
        String expressionCopy = new String(expression);
        Map<String, Integer> inputValues = getInputValues(stacks);
        for (String input : inputValues.keySet()) {
            Integer level = inputValues.get(input);
            expressionCopy = expressionCopy.replaceAll(input, String.valueOf(level));
        }
        ExpressionEvaluator evaluator = new ExpressionEvaluator(expressionCopy);
        return evaluator.parseArithmetic();
    }

    public ArithmeticExpression copy() {
        List<String> inputNamesCopy = null;
        if (getInputNames() != null) {
            inputNamesCopy = new ArrayList<>();
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
