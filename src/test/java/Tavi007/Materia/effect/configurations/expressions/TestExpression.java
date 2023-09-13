package Tavi007.Materia.effect.configurations.expressions;

import java.util.Set;

import org.apache.commons.compress.utils.Sets;
import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.effects.configurations.expressions.Expression;

public class TestExpression {

    private class ExpressionTester extends Expression {

        public ExpressionTester(String expression) {
            super(expression);
        }

        public ExpressionTester(String expression, Set<String> inputNames) {
            super(expression, inputNames);
        }

        @Override
        public Set<String> getInputNames() {
            return super.getInputNames();
        }

        @Override
        protected boolean isValid() {
            return true;
        }
    }

    @Test
    public void testSingleInputName() {
        ExpressionTester expression = new ExpressionTester("#materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testSingleInputNameWithNumber() {
        ExpressionTester expression = new ExpressionTester("#materia:test1");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test1"), inputNames);
    }

    @Test
    public void testSingleInputNameWithLowerScore() {
        ExpressionTester expression = new ExpressionTester("#materia:test_");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test_"), inputNames);
    }

    @Test
    public void testSingleInputNameMultipleTimes() {
        ExpressionTester expression = new ExpressionTester("#materia:test + #materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testMultipleInputNames() {
        ExpressionTester expression = new ExpressionTester("#materia:test + #materia:testing");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test", "materia:testing"), inputNames);
    }
}
