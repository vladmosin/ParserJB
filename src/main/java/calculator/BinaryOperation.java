package calculator;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiFunction;

enum Operation {ADD, SUB, MUL, DIV, MOD, LESS, GREATER, EQUAL }

public class BinaryOperation {
    @NotNull private static Map<Operation, BiFunction<Integer, Integer, Integer>> functionByName = Map.of(
            Operation.ADD, (left, right) -> left + right,
            Operation.SUB, (left, right) -> left - right,
            Operation.MUL, (left, right) -> left * right,
            Operation.DIV, (left, right) -> left / right,
            Operation.MOD, (left, right) -> left % right,
            Operation.LESS, (left, right) -> (left.compareTo(right) < 0) ? 1 : 0,
            Operation.GREATER, (left, right) -> (left.compareTo(right) > 0) ? 1 : 0,
            Operation.EQUAL, (left, right) -> (left.equals(right)) ? 1 : 0

    );

    @NotNull private Operation operation;

    public BinaryOperation(@NotNull Operation operation) {
        this.operation = operation;
    }

    public int apply(int left, int right) {
        return functionByName.get(operation).apply(left, right);
    }


}
