package calculator;

import exceptions.CalculationException;

public interface Expression {
    int calculate() throws CalculationException;
}
