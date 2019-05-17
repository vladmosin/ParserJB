package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class UnaryOperationException implements Expression {
    @NotNull private Expression expression;

    @Override
    public int calculate() throws CalculationException {
        return -expression.calculate();
    }

    public UnaryOperationException(@NotNull Expression expression) {
        this.expression = expression;
    }
}
