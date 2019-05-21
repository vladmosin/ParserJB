package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class UnaryOperationExpression implements Expression {
    @NotNull private Expression expression;

    @Override
    public int calculate() throws CalculationException, ParameterNotFoundException {
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
    public void link(@NotNull FunctionExecutor functionExecutor)
            throws FunctionNotFoundException, ArgumentNumberMismatchException {
        expression.link(functionExecutor);
    }

    public UnaryOperationExpression(@NotNull Expression expression) {
        this.expression = expression;
    }

    @Override
    @NotNull public String toString() {
        return "-" + expression.toString();
    }
}
