package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Expression {
    /** Calculates expression */
    int calculate() throws CalculationException, ParameterNotFoundException;

    /** Checks that expressions are equal */
    boolean isEqual(Expression expression);

    /** Replace identifiers from substitution with values */
    @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution);

    /** Links function calls to their definitions */
    void link(@NotNull FunctionLinker functionLinker) throws FunctionNotFoundException, ArgumentNumberMismatchException;
    @NotNull String toString();
}
