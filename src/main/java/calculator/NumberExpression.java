package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class NumberExpression implements Expression {
    private int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int calculate() throws CalculationException {
        return value;
    }
}
