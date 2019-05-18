package parser;

import calculator.*;
import exceptions.ParsingException;
import org.jetbrains.annotations.NotNull;

import static calculator.BinaryOperation.possibleOperation;

public class Parser {
    private String parsingString;

    public Expression parse(@NotNull String stringToParse) throws ParsingException {
        parsingString = stringToParse;
        var result = parseExpression(0);
        if (result == null) {
            throw new ParsingException("Cannot parse string");
        }

        if (result.getNumberOfParsedSymbols() != parsingString.length()) {
            throw new ParsingException("Parsed not full string");
        }

        return result.getExpression();
    }

    private boolean parsingSucceed(ParsingState parsingState) {
        return parsingState != null && parsingState.getNumberOfParsedSymbols() == parsingString.length();
    }

    private ParsingState parseExpression(int index) {
        var binaryExpression = parseBinaryExpression(index);
        if (binaryExpression != null) {
            return binaryExpression;
        } else {
            return parseConstantExpression(index);
        }
    }

    /** Parses number
     * @param index stores index in string from which number should be parsed */
    private ParsingState parseNumber(int index) {
        if (index >= parsingString.length()) {
            return null;
        }

        if (parsingString.charAt(index) > '9' || parsingString.charAt(index) <'1') {
            return null;
        }

        int parsedValue = 0;
        int startOfNumber = index;
        while (index < parsingString.length() && Character.isDigit(parsingString.charAt(index))) {
            parsedValue = parsedValue * 10 + (parsingString.charAt(index) - '0');
            index++;
        }

        return new ParsingState(new NumberExpression(parsedValue), index - startOfNumber);
    }

    private ParsingState parseIdentifier(int index) {
        if (index >= parsingString.length()) {
            return null;
        }

        if (!isIdentifierSymbol(parsingString.charAt(index))) {
            return null;
        }

        int startOfIdentifier = index;
        while (index < parsingString.length() && isIdentifierSymbol(parsingString.charAt(index))) {
            index++;
        }

        return new ParsingState(new IdentifierExpression(parsingString.substring(startOfIdentifier, index)),
                index - startOfIdentifier);
    }

    private boolean isIdentifierSymbol(char symbol) {
        return (symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol == '_');
    }

    private ParsingState parseConstantExpression(int index) {
        if (index >= parsingString.length()) {
            return null;
        }

        if (parsingString.charAt(index) == '-') {
            var parsedNumber = parseNumber(index + 1);
            if (parsedNumber == null) {
                return null;
            } else {
                return new ParsingState(new UnaryOperationExpression(parsedNumber.getExpression()),
                        parsedNumber.getNumberOfParsedSymbols() + 1);
            }
        } else {
            return parseNumber(index);
        }
    }

    private ParsingState parseBinaryExpression(int index) {
        if (symbolDifferent(index, '(')) {
            return null;
        }

        var currentIndex = index + 1;
        var leftExpression = parseExpression(currentIndex);
        if (leftExpression == null) {
            return null;
        }

        currentIndex += leftExpression.getNumberOfParsedSymbols();
        var operation = parseOperation(currentIndex);

        if (operation == null) {
            return null;
        }

        currentIndex++;
        var rightExpression = parseExpression(currentIndex);
        if (rightExpression == null) {
            return null;
        }

        currentIndex += rightExpression.getNumberOfParsedSymbols();
        if (symbolDifferent(currentIndex, ')')) {
            return null;
        }

        return new ParsingState(new BinaryOperationExpression(operation, leftExpression.getExpression(),
                rightExpression.getExpression()), currentIndex + 1 - index);
    }

    private boolean symbolDifferent(int index, char symbol) {
        return index >= parsingString.length() || parsingString.charAt(index) != symbol;
    }

    private BinaryOperation parseOperation(int index) {
        if (possibleOperation(parsingString.charAt(index))) {
            return new BinaryOperation(parsingString.charAt(index));
        }

        return null;
    }
}
