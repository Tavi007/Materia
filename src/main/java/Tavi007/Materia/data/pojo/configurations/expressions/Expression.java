package Tavi007.Materia.data.pojo.configurations.expressions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.RegistryHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class Expression {

    protected String expression;
    private transient Set<String> inputNames;

    private static final Pattern ITEM_INPUT = Pattern.compile("(?=#)([#a-zA-Z0-9_\\:]*)");

    protected Expression(String expression) {
        this.expression = expression;
    }

    protected Expression(String expression, Set<String> inputNames) {
        this.expression = expression;
        this.inputNames = inputNames;
    }

    protected Set<String> getInputNames() {
        if (inputNames == null) {
            inputNames = new HashSet<>();
            Matcher matcher = ITEM_INPUT.matcher(expression);
            while (matcher.find()) {
                String foundInputName = matcher.group().replace("#", "");
                inputNames.add(foundInputName);
            }
        }
        return inputNames;
    }

    protected Map<String, Integer> getInputValues(List<ItemStack> stacks) {
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

    public abstract Expression copy();

    protected Set<String> copyInputNames() {
        Set<String> inputNamesCopy = null;
        if (getInputNames() != null) {
            inputNamesCopy = new HashSet<>();
            for (String inputName : getInputNames()) {
                inputNamesCopy.add(inputName);
            }
        }
        return inputNamesCopy;
    }

    protected String getFinalExpression(List<ItemStack> stacks) {
        String expressionCopy = new String(expression);
        Map<String, Integer> inputValues = getInputValues(stacks);
        for (String input : inputValues.keySet()) {
            Integer level = inputValues.get(input);
            expressionCopy = expressionCopy.replaceAll("#" + input, String.valueOf(level));
        }
        return expressionCopy;
    }

    public boolean isValid() {
        return expression != null;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(expression);
    }

    protected Expression(FriendlyByteBuf buf) {
        expression = buf.readUtf();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Expression otherExpression) {
            return Objects.equals(expression, otherExpression.expression)
                && Objects.equals(getInputNames(), otherExpression.getInputNames());
        }
        return false;
    }
}
