package Tavi007.Materia.effect.configurations.expressions;

import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.ExpressionEvaluator;

public class TestExpressionEvaluator {

    @Test
    public void testSingleNumber() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9");
        Assert.assertEquals(9, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAddition() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 + 1");
        Assert.assertEquals(10, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionNoWhitespace() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9+1");
        Assert.assertEquals(10, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionWithRational1() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9.5 + 1");
        Assert.assertEquals(10.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionWithRational2() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 + 1.5");
        Assert.assertEquals(10.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtraction() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 - 1");
        Assert.assertEquals(8, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionWithRational1() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9.5 - 1");
        Assert.assertEquals(8.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionWithRational2() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 - 1.5");
        Assert.assertEquals(7.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionNoWhitespace() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9-1");
        Assert.assertEquals(8, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplication() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 * 2");
        Assert.assertEquals(18, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionNoWhitespace() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9*2");
        Assert.assertEquals(18, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionWithRational1() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9.5*2");
        Assert.assertEquals(19, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionWithRational2() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9*2.5");
        Assert.assertEquals(22.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivision() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 / 2");
        Assert.assertEquals(4.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionNoWhitespace() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9/2");
        Assert.assertEquals(4.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionWithRational1() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9.5/2");
        Assert.assertEquals(4.75, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionWithRational2() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9/2.5");
        Assert.assertEquals(3.6, expression.parseArithmetic(), 0);
    }

    @Test
    public void testDoubleAddition() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 + 1 + 3");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketAddition() {
        ExpressionEvaluator expression = new ExpressionEvaluator("(9 + 1) + 3");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketAddition() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 + (1 + 3)");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketSubtraction() {
        ExpressionEvaluator expression = new ExpressionEvaluator("(9 - 1) + 3");
        Assert.assertEquals(11, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketSubtraction() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 - (1 + 3)");
        Assert.assertEquals(5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketMultiplication() {
        ExpressionEvaluator expression = new ExpressionEvaluator("(9 * 2) + 3");
        Assert.assertEquals(21, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketMultiplication() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 * (2 + 3)");
        Assert.assertEquals(45, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketDivision() {
        ExpressionEvaluator expression = new ExpressionEvaluator("(9 / 2) + 3");
        Assert.assertEquals(7.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketDivision() {
        ExpressionEvaluator expression = new ExpressionEvaluator("9 / (2 + 3)");
        Assert.assertEquals(1.8, expression.parseArithmetic(), 0);
    }
}