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
        var constantExpression = parseConstantExpression(index);
        var ifExpression = parseIfExpression(index);

        if (binaryExpression != null) {
            return binaryExpression;
        } else if (ifExpression != null) {
            return ifExpression;
        } else {
            return constantExpression;
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

        currentIndex += increaseIndex(leftExpression);
        var operation = parseOperation(currentIndex);


        currentIndex++;
        var rightExpression = parseExpression(currentIndex);

        currentIndex += increaseIndex(rightExpression);
        if (symbolDifferent(currentIndex, ')') || rightExpression == null ||
                leftExpression == null || operation == null) {
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

    /** If parsingState == null, then it's not important the increasing */
    private int increaseIndex(ParsingState parsingState) {
        if (parsingState == null) {
            return 0;
        } else {
            return parsingState.getNumberOfParsedSymbols();
        }
    }

    private ParsingState parseIfExpression(int index) {
        if (symbolDifferent(index, '[')) {
            return null;
        }

        var currentIndex = index + 1;
        var caseExpression = parseExpression(currentIndex);
        currentIndex += increaseIndex(caseExpression);

        if (!isSubstring(currentIndex, "]?(")) {
            return null;
        }

        currentIndex += 3;
        var ifTrueExpression = parseExpression(currentIndex);
        currentIndex += increaseIndex(ifTrueExpression);

        if (!isSubstring(currentIndex, "):(")) {
            return null;
        }

        currentIndex += 3;
        var ifFalseExpression = parseExpression(currentIndex);
        currentIndex += increaseIndex(ifFalseExpression);

        if (symbolDifferent(currentIndex, ')')) {
            return null;
        }

        if (caseExpression == null || ifFalseExpression == null || ifTrueExpression == null) {
            return null;
        }

        return new ParsingState(new IfExpression(caseExpression.getExpression(), ifTrueExpression.getExpression(),
                ifFalseExpression.getExpression()), currentIndex + 1 - index);
    }

    private boolean isSubstring(int index, @NotNull String comparingString) {
        for (int i = 0; i < comparingString.length(); i++) {
            if (symbolDifferent(index + i, comparingString.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
