package Tavi007.Materia.data.pojo.configurations.expressions;

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
                x = parseComparison() && x; // and
            } else if (eat('|')) {
                x = parseComparison() || x; // or
            } else {
                return x;
            }
        }
    }

    private boolean parseComparison() {
        if (eat('[')) {
            boolean x = parseBooleanExpression();
            if (!eat(']')) {
                throw new RuntimeException("Missing ']'");
            }
            return x;
        }

        double leftValue = new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
        for (;;) {
            if (eat('<')) {
                if (eat('=')) {
                    return leftValue <= new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                } else {
                    return leftValue < new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                }
            } else if (eat('>')) {
                if (eat('=')) {
                    return leftValue >= new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                } else {
                    return leftValue > new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
                }
            } else if (eat('=')) {
                return leftValue == new ArithmeticEvaluator(getNextArithmeticExpression()).parseArithmetic();
            } else {
                throw new RuntimeException("Unexpected: " + (char) charAtPos);
            }
        }
    }

    private String getNextArithmeticExpression() {
        int startPos = pos;
        for (;;) {
            nextChar();
            if (pos == expression.length()
                || charAtPos == '<'
                || charAtPos == '='
                || charAtPos == '>'
                || charAtPos == '|'
                || charAtPos == '&'
                || charAtPos == '['
                || charAtPos == ']') {
                break;
            }
        }
        return expression.substring(startPos, pos);
    }

}
