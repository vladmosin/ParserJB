package calculator;

import org.jetbrains.annotations.NotNull;

public class BinaryOperationExpression implements Expression {
    @NotNull private final BinaryOperation operation;
    @NotNull private final Expression left;
    @NotNull private final Expression right;

    @Override
    public int calculate() {
        int leftValue = left.calculate();
        int rightValue = right.calculate();

        return operation.apply(leftValue, rightValue);
    }

    public BinaryOperationExpression(@NotNull BinaryOperation operation,
                                     @NotNull Expression left, @NotNull Expression right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }
}
