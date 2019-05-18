package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class IfExpression implements Expression {
    @NotNull private final Expression caseExpression;
    @NotNull private final Expression ifTrueExpression;
    @NotNull private final Expression ifFalseExpression;


    @Override
    public int calculate() throws CalculationException {
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

    public IfExpression(@NotNull Expression caseExpression, @NotNull Expression ifTrueExpression,
                        @NotNull Expression ifFalseExpression) {
        this.caseExpression = caseExpression;
        this.ifFalseExpression = ifFalseExpression;
        this.ifTrueExpression = ifTrueExpression;
    }

}
