package Tavi007.Materia.data.pojo.configurations.expressions;

public class ExpressionEvaluator {

    private String expression;

    private int charAtPos;
    private int pos = -1;

    protected ExpressionEvaluator(String expression) {
        this.expression = expression;
    }

    protected void nextChar() {
        charAtPos = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    protected boolean eat(int charToEat) {
        while (charAtPos == ' ')
            nextChar();
        if (charAtPos == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }
}
