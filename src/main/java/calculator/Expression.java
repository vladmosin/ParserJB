package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Expression {
    int calculate() throws CalculationException;
    boolean isEqual(Expression expression);
    @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution);
    void link(@NotNull FunctionExecutor functionExecutor) throws FunctionNotFoundException;
}
