package exceptions;

import org.jetbrains.annotations.NotNull;

public class CalculationException extends Exception{
    @NotNull private String message;

    public CalculationException(@NotNull String message) {
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }
}
