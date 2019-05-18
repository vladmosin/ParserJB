package exceptions;

import org.jetbrains.annotations.NotNull;

public class ParsingException extends Exception {
    @NotNull
    private String message;

    public ParsingException(@NotNull String message) {
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }
}
