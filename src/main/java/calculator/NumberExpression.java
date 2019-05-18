package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class NumberExpression implements Expression {
    private int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int calculate() throws CalculationException {
        return value;
    }

    @Override
    public boolean isEqual(Expression expression) {
        if (expression == null) {
            return false;
        }

        if (expression instanceof NumberExpression) {
            var identifierExpression = (NumberExpression) expression;
            return value == identifierExpression.value;
        }

        return false;
    }
}
