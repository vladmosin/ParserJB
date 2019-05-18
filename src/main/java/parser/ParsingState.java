package parser;

import calculator.Expression;
import org.jetbrains.annotations.NotNull;

public class ParsingState {
    @NotNull private Expression parsedExpression;
    private int numberOfParsedSymbols;

    public ParsingState(@NotNull Expression expression, int numberOfParsedSymbols) {
        parsedExpression = expression;
        this.numberOfParsedSymbols = numberOfParsedSymbols;
    }

    @NotNull public Expression getExpression() {
        return parsedExpression;
    }

    public int getNumberOfParsedSymbols() {
        return numberOfParsedSymbols;
    }
}
