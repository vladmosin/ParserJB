package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/** Stores identifier */
public class IdentifierExpression implements Expression {
    @NotNull private String name;

    public IdentifierExpression(@NotNull String name) {
        this.name = name;
    }

    @NotNull public String getName() {
        return name;
    }

    @Override
    @NotNull public String toString() {
        return name;
    }

    @Override
    public int calculate() throws CalculationException, ParameterNotFoundException {
        throw new ParameterNotFoundException(name, -1);
    }

    @Override
    public boolean isEqual(Expression expression) {
        if (expression == null) {
            return false;
        }

        if (expression instanceof IdentifierExpression) {
            var identifierExpression = (IdentifierExpression) expression;
            return name.equals(identifierExpression.name);
        }

        return false;
    }

    @Override
    public @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        if (substitution.containsKey(name)) {
            return new NumberExpression(substitution.get(name));
        } else {
            return this;
        }
    }

    @Override
    public void link(@NotNull FunctionLinker functionLinker) throws FunctionNotFoundException {

    }
}
