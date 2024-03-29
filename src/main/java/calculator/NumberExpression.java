package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/** Stores number */
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

    @Override
    public @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        return this;
    }

    @Override
    public void link(@NotNull FunctionLinker functionLinker) throws FunctionNotFoundException {

    }

    @Override
    @NotNull public String toString() {
        return Integer.toString(value);
    }
}
