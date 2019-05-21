package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BinaryOperationExpression implements Expression {
    @NotNull private final BinaryOperation operation;
    @NotNull private final Expression left;
    @NotNull private final Expression right;

    @Override
    public int calculate() throws CalculationException {
        int leftValue = left.calculate();
        int rightValue = right.calculate();

        return operation.apply(leftValue, rightValue);
    }

    @Override
    public boolean isEqual(Expression expression) {
        if (expression == null) {
            return false;
        }

        if (expression instanceof BinaryOperationExpression) {
            var binaryExpression = (BinaryOperationExpression) expression;
            return left.isEqual(binaryExpression.left) && right.isEqual(binaryExpression.right) &&
                    operation.isEqual(binaryExpression.operation);
        }

        return false;
    }

    @Override
    @NotNull public Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        return new BinaryOperationExpression(operation, left.applySubstitution(substitution),
                right.applySubstitution(substitution));
    }

    @Override
    public void link(@NotNull FunctionExecutor functionExecutor) {
        left.link(functionExecutor);
        right.link(functionExecutor);
    }

    public BinaryOperationExpression(@NotNull BinaryOperation operation,
                                     @NotNull Expression left, @NotNull Expression right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }
}
