package exceptions;

import org.jetbrains.annotations.NotNull;

public class IllegalFunctionDeclarationException extends Exception {
    @NotNull
    private String message;

    public IllegalFunctionDeclarationException(@NotNull String message) {
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }
}
