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

    @NotNull private static Map<Character, Operation> operationByName = Map.of(
            '+', Operation.ADD,
            '-', Operation.SUB,
            '*', Operation.MUL,
            '/', Operation.DIV,
            '%', Operation.MOD,
            '=', Operation.EQUAL,
            '<', Operation.LESS,
            '>', Operation.GREATER
    );

    @NotNull private Operation operation;

    public static boolean possibleOperation(char operation) {
        return operationByName.containsKey(operation);
    }

    public BinaryOperation(@NotNull Operation operation) {
        this.operation = operation;
    }

    public BinaryOperation(char operation) {
        if (possibleOperation(operation)) {
            this.operation = operationByName.get(operation);
        } else {
            throw new IllegalArgumentException("Inappropriate operation");
        }
    }

    public int apply(int left, int right) {
        return functionByName.get(operation).apply(left, right);
    }

    public boolean isEqual(BinaryOperation binaryOperation) {
        if (binaryOperation == null) {
            return false;
        }

        return binaryOperation.operation == operation;
    }
}
