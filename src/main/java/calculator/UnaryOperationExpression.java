package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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

    @Override
    public @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        return new UnaryOperationExpression(expression.applySubstitution(substitution));
    }

    @Override
    public void link(@NotNull FunctionExecutor functionExecutor) throws FunctionNotFoundException {
        expression.link(functionExecutor);
    }

    public UnaryOperationExpression(@NotNull Expression expression) {
        this.expression = expression;
    }
}
