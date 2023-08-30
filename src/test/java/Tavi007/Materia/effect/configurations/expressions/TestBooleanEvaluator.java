package Tavi007.Materia.effect.configurations.expressions;

import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.BooleanEvaluator;

public class TestBooleanEvaluator {

    @Test
    public void testComparisonGreaterTrue() {
        Assert.assertTrue(new BooleanEvaluator("1 > 0").parseBoolean());
    }

    @Test
    public void testComparisonGreaterFalse() {
        Assert.assertFalse(new BooleanEvaluator("0 > 1").parseBoolean());
    }

    @Test
    public void testComparisonGreaterEqualTrue() {
        Assert.assertTrue(new BooleanEvaluator("1 >= 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 >= 0").parseBoolean());
    }

    @Test
    public void testComparisonGreaterEqualFalse() {
        Assert.assertFalse(new BooleanEvaluator("0 >= 1").parseBoolean());
    }

    @Test
    public void testComparisonLessTrue() {
        Assert.assertTrue(new BooleanEvaluator("0 < 1").parseBoolean());
    }

    @Test
    public void testComparisonLessFalse() {
        Assert.assertFalse(new BooleanEvaluator("1 < 0").parseBoolean());
    }

    @Test
    public void testComparisonLessEqualTrue() {
        Assert.assertTrue(new BooleanEvaluator("0 <= 1").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("1 <= 1").parseBoolean());
    }

    @Test
    public void testComparisonLessEqualFalse() {
        Assert.assertFalse(new BooleanEvaluator("1 <= 0").parseBoolean());
    }

    @Test
    public void testComparisonEqualTrue() {
        Assert.assertTrue(new BooleanEvaluator("1 = 1").parseBoolean());
    }

    @Test
    public void testComparisonEqualFalse() {
        Assert.assertFalse(new BooleanEvaluator("1 = 0").parseBoolean());
    }

    @Test
    public void testExpressionANDTrue() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & 1 = 1").parseBoolean());
    }

    @Test
    public void testExpressionANDFalse() {
        Assert.assertFalse(new BooleanEvaluator("0 = 0 & 1 = 0").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 1").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 0").parseBoolean());
    }

    @Test
    public void testExpressionORTrue() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 1").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | 1 = 1").parseBoolean());
    }

    @Test
    public void testExpressionORFalse() {
        Assert.assertFalse(new BooleanEvaluator("0 = 1 | 1 = 0").parseBoolean());
    }

    @Test
    public void testMultiExpressionsOR() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 1 | 2 = 2").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 1 | 2 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 0 | 2 = 2").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | 1 = 1 | 2 = 2").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 0 | 2 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | 1 = 0 | 2 = 2").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | 1 = 1 | 2 = 0").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 | 1 = 0 | 2 = 0").parseBoolean());
    }

    @Test
    public void testMultiExpressionsAND() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & 1 = 1 & 2 = 2").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & 1 = 1 & 2 = 0").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 0 & 1 = 0 & 2 = 2").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 1 & 2 = 2").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & 1 = 0 & 2 = 0").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 0 & 2 = 2").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 1 & 2 = 0").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 0 & 2 = 0").parseBoolean());
    }

    @Test
    public void testMultiExpressionsMIXED() { // order of operation is always read from left to right (without brackets)
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & 1 = 1 | 2 = 2").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 & 1 = 1 | 2 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & 1 = 0 | 2 = 2").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 & 1 = 1 | 2 = 2").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & 1 = 0 | 2 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 & 1 = 0 | 2 = 2").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 1 | 2 = 0").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 & 1 = 0 | 2 = 0").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 1 & 2 = 2").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 | 1 = 1 & 2 = 0").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | 1 = 0 & 2 = 2").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | 1 = 1 & 2 = 2").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 | 1 = 0 & 2 = 0").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 | 1 = 0 & 2 = 2").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 | 1 = 1 & 2 = 0").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 | 1 = 0 & 2 = 0").parseBoolean());
    }

    @Test
    public void testSimpleBracket() {
        Assert.assertTrue(new BooleanEvaluator("[0 = 0]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("[0 = 1]").parseBoolean());
    }

    @Test
    public void testSimpleBracketLeft() {
        Assert.assertTrue(new BooleanEvaluator("[0 = 0] & 1 = 1").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("[0 = 1] & 1 = 1").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("[0 = 1] & 0 = 1").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("[0 = 1] & 0 = 1").parseBoolean());
    }

    @Test
    public void testSimpleBracketRight() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & [1 = 1]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 1]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [0 = 1]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [0 = 1]").parseBoolean());
    }

    @Test
    public void testBrackedMultiExpressionsOR() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 1 | 2 = 2]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 1 | 2 = 0]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 0 | 2 = 2]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | [1 = 1 | 2 = 2]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 0 | 2 = 0]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | [1 = 0 | 2 = 2]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | [1 = 1 | 2 = 0]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 | [1 = 0 | 2 = 0]").parseBoolean());
    }

    @Test
    public void testBrackedMultiExpressionsAND() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & [1 = 1 & 2 = 2]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & [1 = 1 & 2 = 0]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 0 & [1 = 0 & 2 = 2]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 1 & 2 = 2]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & [1 = 0 & 2 = 0]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 0 & 2 = 2]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 1 & 2 = 0]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 0 & 2 = 0]").parseBoolean());
    }

    @Test
    public void testBrackedMultiExpressionsMIXED() {
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & [1 = 1 | 2 = 2]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 & [1 = 1 | 2 = 0]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 & [1 = 0 | 2 = 2]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 1 | 2 = 2]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 0 & [1 = 0 | 2 = 0]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 0 | 2 = 2]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 1 | 2 = 0]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 & [1 = 0 | 2 = 0]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 1 & 2 = 2]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 1 & 2 = 0]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 0 & 2 = 2]").parseBoolean());
        Assert.assertTrue(new BooleanEvaluator("0 = 1 | [1 = 1 & 2 = 2]").parseBoolean());

        Assert.assertTrue(new BooleanEvaluator("0 = 0 | [1 = 0 & 2 = 0]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 | [1 = 0 & 2 = 2]").parseBoolean());
        Assert.assertFalse(new BooleanEvaluator("0 = 1 | [1 = 1 & 2 = 0]").parseBoolean());

        Assert.assertFalse(new BooleanEvaluator("0 = 1 | [1 = 0 & 2 = 0]").parseBoolean());
    }
}
