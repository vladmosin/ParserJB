package calculator;

import org.jetbrains.annotations.NotNull;

public class Number {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    @NotNull public int getValue() {
        return value;
    }
}
