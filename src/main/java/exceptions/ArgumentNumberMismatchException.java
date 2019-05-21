package exceptions;

import org.jetbrains.annotations.NotNull;

public class ArgumentNumberMismatchException extends Exception {
    @NotNull
    private String message;
    private int line;

    public ArgumentNumberMismatchException(@NotNull String message,int line) {
        this.line = line;
        this.message = message;
    }

    @NotNull public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }

    public void changeLine(int line) {
        this.line = line;
    }
}
