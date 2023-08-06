package Tavi007.Materia.effect.configurations.expressions;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.compress.utils.Sets;
import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;

public class TestArithmeticExpression {

    @Test
    public void testSingleNumber() {
        ArithmeticExpression expression = new ArithmeticExpression("9");
        Assert.assertEquals(9, expression.evaluate(Collections.emptyList()), 0);
    }

    @Test
    public void testSingleInputName() {
        ArithmeticExpression expression = new ArithmeticExpression("#materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testSingleInputNameWithNumber() {
        ArithmeticExpression expression = new ArithmeticExpression("#materia:test1");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test1"), inputNames);
    }

    @Test
    public void testSingleInputNameWithLowerScore() {
        ArithmeticExpression expression = new ArithmeticExpression("#materia:test_");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test_"), inputNames);
    }

    @Test
    public void testSingleInputNameMultipleTimes() {
        ArithmeticExpression expression = new ArithmeticExpression("#materia:test + #materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testMultipleInputNames() {
        ArithmeticExpression expression = new ArithmeticExpression("#materia:test + #materia:testing");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test", "materia:testing"), inputNames);
    }
}
