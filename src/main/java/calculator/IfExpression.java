package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class IfExpression implements Expression {
    @NotNull private final Expression caseExpression;
    @NotNull private final Expression ifTrueExpression;
    @NotNull private final Expression ifFalseExpression;


    @Override
    public int calculate() throws CalculationException, ParameterNotFoundException {
        if (caseExpression.calculate() != 0) {
            return ifTrueExpression.calculate();
        } else {
            return ifFalseExpression.calculate();
        }
    }

    @Override
    public boolean isEqual(Expression expression) {
        if (expression == null) {
            return false;
        }

        if (expression instanceof IfExpression) {
            var IfExpression = (IfExpression) expression;
            return caseExpression.isEqual(IfExpression.caseExpression) &&
                   ifFalseExpression.isEqual(IfExpression.ifFalseExpression) &&
                   ifTrueExpression.isEqual(IfExpression.ifTrueExpression);
        }

        return false;
    }

    @Override
    @NotNull public String toString() {

        return "[" +
                caseExpression.toString() +
                "]?(" +
                ifTrueExpression.toString() +
                "):(" +
                ifFalseExpression +
                ")";
    }

    @Override
    public @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        return new IfExpression(caseExpression.applySubstitution(substitution),
                ifTrueExpression.applySubstitution(substitution), ifFalseExpression.applySubstitution(substitution));
    }

    @Override
    public void link(@NotNull FunctionExecutor functionExecutor)
            throws FunctionNotFoundException, ArgumentNumberMismatchException {
        caseExpression.link(functionExecutor);
        ifTrueExpression.link(functionExecutor);
        ifFalseExpression.link(functionExecutor);
    }

    public IfExpression(@NotNull Expression caseExpression, @NotNull Expression ifTrueExpression,
                        @NotNull Expression ifFalseExpression) {
        this.caseExpression = caseExpression;
        this.ifFalseExpression = ifFalseExpression;
        this.ifTrueExpression = ifTrueExpression;
    }

}
