package parser;

import calculator.Expression;
import org.jetbrains.annotations.NotNull;

public class ParsingResult {
    private Expression parsedExpression;
    @NotNull private String leftString;

    public ParsingResult(Expression expression, @NotNull String string) {
        parsedExpression = expression;
        leftString = string;
    }

    public Expression getExpression() {
        return parsedExpression;
    }

    @NotNull public String getString() {
        return leftString;
    }
}
