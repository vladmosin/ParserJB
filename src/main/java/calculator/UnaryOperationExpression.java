package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class UnaryOperationExpression implements Expression {
    @NotNull private Expression expression;

    @Override
    public int calculate() throws CalculationException {
        return -expression.calculate();
    }

    @Override
    public boolean isEqual(Expression otherExpression) {
        if (otherExpression == null) {
            return false;
        }

        if (otherExpression instanceof UnaryOperationExpression) {
            var unaryOperationExpression = (UnaryOperationExpression) otherExpression;
            return expression.isEqual(unaryOperationExpression.expression);
        }

        return false;
    }

    public UnaryOperationExpression(@NotNull Expression expression) {
        this.expression = expression;
    }
}
