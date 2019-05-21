package exceptions;

import org.jetbrains.annotations.NotNull;

public class IllegalFunctionDeclarationException extends Exception {
    @NotNull private String message;
    private int line;

    public IllegalFunctionDeclarationException(@NotNull String message, int line) {
        this.line = line;
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }
}
