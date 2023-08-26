package Tavi007.Materia.effect.configurations.expressions;

import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.BooleanEvaluator;

public class TestBooleanEvaluator {

    @Test
    public void testTrueGreater() {
        BooleanEvaluator expression = new BooleanEvaluator("1 > 0");
        Assert.assertTrue(expression.parseBoolean());
    }

    @Test
    public void testFalseGreater() {
        BooleanEvaluator expression = new BooleanEvaluator("0 > 1");
        Assert.assertFalse(expression.parseBoolean());
    }

    @Test
    public void testTrueGreaterEqual() {
        BooleanEvaluator expression = new BooleanEvaluator("1 >= 0");
        Assert.assertTrue(expression.parseBoolean());

        expression = new BooleanEvaluator("0 >= 0");
        Assert.assertTrue(expression.parseBoolean());
    }

    @Test
    public void testFalseGreaterEqual() {
        BooleanEvaluator expression = new BooleanEvaluator("0 >= 1");
        Assert.assertFalse(expression.parseBoolean());
    }

    @Test
    public void testTrueLess() {
        BooleanEvaluator expression = new BooleanEvaluator("0 < 1");
        Assert.assertTrue(expression.parseBoolean());
    }

    @Test
    public void testFalseLess() {
        BooleanEvaluator expression = new BooleanEvaluator("1 < 0");
        Assert.assertFalse(expression.parseBoolean());
    }

    @Test
    public void testTrueLessEqual() {
        BooleanEvaluator expression = new BooleanEvaluator("0 <= 1");
        Assert.assertTrue(expression.parseBoolean());

        expression = new BooleanEvaluator("1 <= 1");
        Assert.assertTrue(expression.parseBoolean());
    }

    @Test
    public void testFalseLessEqual() {
        BooleanEvaluator expression = new BooleanEvaluator("1 <= 0");
        Assert.assertFalse(expression.parseBoolean());
    }
}
