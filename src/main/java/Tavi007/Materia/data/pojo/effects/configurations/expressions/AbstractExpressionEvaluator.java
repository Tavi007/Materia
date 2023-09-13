package Tavi007.Materia.data.pojo.effects.configurations.expressions;

public abstract class AbstractExpressionEvaluator {

    protected String expression;

    protected int charAtPos;
    protected int pos = -1;

    protected AbstractExpressionEvaluator(String expression) {
        this.expression = expression;
    }

    protected void nextChar() {
        charAtPos = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    protected boolean eat(int charToEat) {
        while (charAtPos == ' ') {
            nextChar();
        }

        if (charAtPos == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }
}
