package Tavi007.Materia.effect.configurations.expressions;

import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticEvaluator;

public class TestArithmeticEvaluator {

    @Test
    public void testSingleNumber() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9");
        Assert.assertEquals(9, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAddition() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 + 1");
        Assert.assertEquals(10, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionNoWhitespace() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9+1");
        Assert.assertEquals(10, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionWithRational1() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9.5 + 1");
        Assert.assertEquals(10.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleAdditionWithRational2() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 + 1.5");
        Assert.assertEquals(10.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtraction() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 - 1");
        Assert.assertEquals(8, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionWithRational1() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9.5 - 1");
        Assert.assertEquals(8.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionWithRational2() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 - 1.5");
        Assert.assertEquals(7.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleSubtractionNoWhitespace() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9-1");
        Assert.assertEquals(8, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplication() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 * 2");
        Assert.assertEquals(18, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionNoWhitespace() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9*2");
        Assert.assertEquals(18, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionWithRational1() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9.5*2");
        Assert.assertEquals(19, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleMultiplactionWithRational2() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9*2.5");
        Assert.assertEquals(22.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivision() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 / 2");
        Assert.assertEquals(4.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionNoWhitespace() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9/2");
        Assert.assertEquals(4.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionWithRational1() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9.5/2");
        Assert.assertEquals(4.75, expression.parseArithmetic(), 0);
    }

    @Test
    public void testSingleDivisionWithRational2() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9/2.5");
        Assert.assertEquals(3.6, expression.parseArithmetic(), 0);
    }

    @Test
    public void testDoubleAddition() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 + 1 + 3");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketAddition() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("(9 + 1) + 3");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketAddition() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 + (1 + 3)");
        Assert.assertEquals(13, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketSubtraction() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("(9 - 1) + 3");
        Assert.assertEquals(11, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketSubtraction() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 - (1 + 3)");
        Assert.assertEquals(5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketMultiplication() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("(9 * 2) + 3");
        Assert.assertEquals(21, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketMultiplication() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 * (2 + 3)");
        Assert.assertEquals(45, expression.parseArithmetic(), 0);
    }

    @Test
    public void testFrontBracketDivision() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("(9 / 2) + 3");
        Assert.assertEquals(7.5, expression.parseArithmetic(), 0);
    }

    @Test
    public void testBackBracketDivision() {
        ArithmeticEvaluator expression = new ArithmeticEvaluator("9 / (2 + 3)");
        Assert.assertEquals(1.8, expression.parseArithmetic(), 0);
    }
}
