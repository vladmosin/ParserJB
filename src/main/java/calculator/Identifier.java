package calculator;

import exceptions.CalculationException;
import org.jetbrains.annotations.NotNull;

public class Identifier implements Expression {
    @NotNull private String name;

    public Identifier(@NotNull String name) {
        this.name = name;
    }

    @NotNull public String getName() {
        return name;
    }

    @Override
    public int calculate() throws CalculationException {
        throw new CalculationException("Identifier cannot be calculated");
    }
}
