package Tavi007.Materia.effect.configurations.expressions;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class TestArithmeticExpression {

    @Test
    public void testSingleNumber() {
        ArithmeticExpression expression = new ArithmeticExpression("9", Collections.emptyList());
        Assert.assertEquals(9, expression.evaluate(Collections.emptyList()), 0);
    }

    // @Test
    // public void testSingleNumber() {
    // ArithmeticExpression expression = new ArithmeticExpression("9", Arrays.asList(new ItemStack(ItemList.BASE_MATERIA, 1)));
    // Assert.assertEquals(9, expression.evaluate(Collections.emptyList()), 0);
    // }
}
