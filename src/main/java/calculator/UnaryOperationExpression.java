package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class UnaryOperationExpression implements Expression {
    @NotNull private Expression expression;

    @Override
    public int calculate() throws CalculationException {
        return -expression.calculate();
    }

    public UnaryOperationExpression(@NotNull Expression expression) {
        this.expression = expression;
    }
}
