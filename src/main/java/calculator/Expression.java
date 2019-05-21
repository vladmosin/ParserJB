package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Expression {
    int calculate() throws CalculationException, ParameterNotFoundException;
    boolean isEqual(Expression expression);
    @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution);
    void link(@NotNull FunctionExecutor functionExecutor) throws FunctionNotFoundException, ArgumentNumberMismatchException;
    @NotNull String toString();
}
