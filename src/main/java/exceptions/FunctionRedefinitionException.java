package exceptions;

import org.jetbrains.annotations.NotNull;

public class FunctionRedefinitionException extends Exception {
    @NotNull String message;

    public FunctionRedefinitionException(@NotNull String message) {
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }
}
