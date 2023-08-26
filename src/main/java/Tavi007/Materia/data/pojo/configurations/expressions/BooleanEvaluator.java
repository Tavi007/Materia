package Tavi007.Materia.data.pojo.configurations.expressions;

// Note: found this solution on stackoverflow
public class BooleanEvaluator extends ExpressionEvaluator {

    public BooleanEvaluator(String expression) {
        super(expression);
    }

    public boolean parseBoolean() {
        nextChar();
        boolean x = parseBooleanExpression();
        if (pos < expression.length())
            throw new RuntimeException("Unexpected: " + (char) charAtPos);
        return x;
    }

    // Grammar:
    // expression = comparison | expression `&` | `|` comparison | expression
    // comparison = arithmeticExpression `<` | `<=` | `=` | `>=` | `>` arithmeticExpression

    private boolean parseBooleanExpression() {
        boolean x = parseComparison();
        for (;;) {
            if (eat('&')) {
                return x && parseComparison(); // and
            } else if (eat('|')) {
                return x || parseComparison(); // or
            } else {
                return x;
            }
        }
    }

    private boolean parseComparison() {
        double x = new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
        for (;;) {
            if (eat('<')) {
                if (eat('=')) {
                    return x <= new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                } else {
                    return x < new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                }
            } else if (eat('>')) {
                if (eat('=')) {
                    return x >= new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                } else {
                    return x > new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                }
            } else if (eat('=')) {
                return x == new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
            } else {
                throw new RuntimeException("Unexpected: " + (char) charAtPos);
            }
        }
    }

    private String getNextArithmeticExpression() {
        return "0"; // TODO: fix this part
    }

}
