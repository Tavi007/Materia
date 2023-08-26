package Tavi007.Materia.effect.configurations.expressions;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.compress.utils.Sets;
import org.junit.Assert;
import org.junit.Test;

import Tavi007.Materia.data.pojo.configurations.expressions.Expression;

public class TestExpression {

    @Test
    public void testSingleInputName() {
        Expression expression = new Expression("#materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testSingleInputNameWithNumber() {
        Expression expression = new Expression("#materia:test1");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test1"), inputNames);
    }

    @Test
    public void testSingleInputNameWithLowerScore() {
        Expression expression = new Expression("#materia:test_");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test_"), inputNames);
    }

    @Test
    public void testSingleInputNameMultipleTimes() {
        Expression expression = new Expression("#materia:test + #materia:test");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test"), inputNames);
    }

    @Test
    public void testMultipleInputNames() {
        Expression expression = new Expression("#materia:test + #materia:testing");
        Set<String> inputNames = expression.getInputNames();
        Assert.assertEquals(Sets.newHashSet("materia:test", "materia:testing"), inputNames);
    }
}
