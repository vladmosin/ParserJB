package exceptions;

import org.jetbrains.annotations.NotNull;

public class FunctionNotFoundException extends Exception {
    @NotNull
    private String message;

    public FunctionNotFoundException(@NotNull String message) {
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }
}
