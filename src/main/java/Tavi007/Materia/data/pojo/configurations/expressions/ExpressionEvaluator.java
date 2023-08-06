package Tavi007.Materia.data.pojo.configurations.expressions;

// Note: found this solution on stackoverflow
public class ExpressionEvaluator {

    private String expression;

    private int charAtPos;
    private int pos = -1;

    public ExpressionEvaluator(String expression) {
        this.expression = expression;
    }

    private void nextChar() {
        charAtPos = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (charAtPos == ' ')
            nextChar();
        if (charAtPos == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    public double parseArithmetic() {
        nextChar();
        double x = parseArithmeticExpression();
        if (pos < expression.length())
            throw new RuntimeException("Unexpected: " + (char) charAtPos);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)` | number
    // | functionName `(` expression `)` | functionName factor
    // | factor `^` factor

    private double parseArithmeticExpression() {
        double x = parseTerm();
        for (;;) {
            if (eat('+'))
                x += parseTerm(); // addition
            else if (eat('-'))
                x -= parseTerm(); // subtraction
            else
                return x;
        }
    }

    private double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if (eat('*'))
                x *= parseFactor(); // multiplication
            else if (eat('/'))
                x /= parseFactor(); // division
            else
                return x;
        }
    }

    private double parseFactor() {
        if (eat('+'))
            return +parseFactor(); // unary plus
        if (eat('-'))
            return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
            x = parseArithmeticExpression();
            if (!eat(')'))
                throw new RuntimeException("Missing ')'");
        } else if ((charAtPos >= '0' && charAtPos <= '9') || charAtPos == '.') { // numbers
            while ((charAtPos >= '0' && charAtPos <= '9') || charAtPos == '.')
                nextChar();
            x = Double.parseDouble(expression.substring(startPos, this.pos));
        } else if (charAtPos >= 'a' && charAtPos <= 'z') { // functions
            while (charAtPos >= 'a' && charAtPos <= 'z')
                nextChar();
            String func = expression.substring(startPos, this.pos);
            if (eat('(')) {
                x = parseArithmeticExpression();
                if (!eat(')'))
                    throw new RuntimeException("Missing ')' after argument to " + func);
            } else {
                x = parseFactor();
            }
            if (func.equals("sqrt"))
                x = Math.sqrt(x);
            else if (func.equals("sin"))
                x = Math.sin(Math.toRadians(x));
            else if (func.equals("cos"))
                x = Math.cos(Math.toRadians(x));
            else if (func.equals("tan"))
                x = Math.tan(Math.toRadians(x));
            else if (func.equals("exp"))
                x = Math.exp(x);
            else
                throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) charAtPos);
        }

        if (eat('^'))
            x = Math.pow(x, parseFactor()); // exponentiation

        return x;
    }
}
