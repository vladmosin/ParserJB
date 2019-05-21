package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BinaryOperationExpression implements Expression {
    @NotNull private final BinaryOperation operation;
    @NotNull private final Expression left;
    @NotNull private final Expression right;

    @Override
    public int calculate() throws CalculationException, ParameterNotFoundException {
        int leftValue = left.calculate();
        int rightValue = right.calculate();

        try {
            return operation.apply(leftValue, rightValue);
        } catch (RuntimeException e) {
            throw  new CalculationException(toString(), -1);
        }
    }

    @Override
    @NotNull public String toString() {
        return "(" + left.toString() + operation.toString() + right.toString() + ")";
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
    public void link(@NotNull FunctionExecutor functionExecutor)
            throws FunctionNotFoundException, ArgumentNumberMismatchException {
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
