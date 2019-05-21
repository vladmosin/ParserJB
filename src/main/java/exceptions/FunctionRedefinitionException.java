package exceptions;

import org.jetbrains.annotations.NotNull;

public class FunctionRedefinitionException extends Exception {
    @NotNull private String message;
    private int numberOfArguments;

    public FunctionRedefinitionException(@NotNull String message, int numberOfArguments) {
        this.message = message;
        this.numberOfArguments = numberOfArguments;
    }

    @NotNull public String getMessage() {
        return message;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }
}
