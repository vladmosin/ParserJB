package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class IdentifierExpression implements Expression {
    @NotNull private String name;

    public IdentifierExpression(@NotNull String name) {
        this.name = name;
    }

    @NotNull public String getName() {
        return name;
    }

    @Override
    public int calculate() throws CalculationException {
        throw new CalculationException("Identifier cannot be calculated");
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
    public void link(@NotNull FunctionExecutor functionExecutor) throws FunctionNotFoundException {

    }
}
