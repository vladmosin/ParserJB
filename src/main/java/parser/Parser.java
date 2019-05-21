package parser;

import calculator.*;
import exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static calculator.BinaryOperation.possibleOperation;

public class Parser {
    /** Uses as function return value */
    private static class Parameters {
        @NotNull ArrayList<String> arguments;
        int numberOfParsedSymbols;

        private Parameters(@NotNull ArrayList<String> arguments, int numberOfParsedSymbols) {
            this.arguments = arguments;
            this.numberOfParsedSymbols = numberOfParsedSymbols;
        }
    }

    /** Uses as function return value */
    private static class Arguments {
        @NotNull ArrayList<Expression> arguments;
        int numberOfParsedSymbols;

        private Arguments(@NotNull ArrayList<Expression> arguments, int numberOfParsedSymbols) {
            this.arguments = arguments;
            this.numberOfParsedSymbols = numberOfParsedSymbols;
        }
    }

    /** String which is parsing now*/
    private String parsingString;

    /** Function linker */
    private FunctionLinker functionLinker;

    /** Current line */
    private int currentLineNumber = 1;

    /** Parses and links expression */
    public Expression parse(@NotNull String[] lines) throws ParsingException,
            FunctionRedefinitionException, IllegalFunctionDeclarationException,
            FunctionNotFoundException, ArgumentNumberMismatchException {
        currentLineNumber = 1;
        var functions = new ArrayList<FunctionHolder>();
        for (int i = 0; i < lines.length - 1; i++) {
            parsingString = lines[i];

            var function = parseFunctionDefinition();
            if (function == null) {
                throw new ParsingException("Cannot parse function definition");
            }

            currentLineNumber++;
            functions.add(function);
        }

        functionLinker = new FunctionLinker(functions);
        parsingString = lines[lines.length - 1];

        var expression = parseLastString();
        for (int i = 0; i < lines.length - 1; i++) {
            try {
                functions.get(i).getFunctionBody().link(functionLinker);
            } catch (FunctionNotFoundException e) {
                e.changeLine(i + 1);
                throw e;
            } catch (ArgumentNumberMismatchException e) {
                e.changeLine(i + 1);
                throw e;
            }
        }
        expression.link(functionLinker);

        return expression;
    }

    public Expression parse(@NotNull String stringToParse) throws ParsingException,
            FunctionRedefinitionException, IllegalFunctionDeclarationException, FunctionNotFoundException, ArgumentNumberMismatchException {
        return parse(stringToParse.split("\n"));
    }

    private Expression parseLastString() throws ParsingException {
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
        var functionCallExpression = parseFunctionCall(index);
        var binaryExpression = parseBinaryExpression(index);
        var constantExpression = parseConstantExpression(index);
        var ifExpression = parseIfExpression(index);
        var identifier = parseIdentifier(index);

        if (functionCallExpression != null) {
            return functionCallExpression;
        } else if (binaryExpression != null) {
            return binaryExpression;
        } else if (ifExpression != null) {
            return ifExpression;
        } else if (constantExpression != null) {
            return constantExpression;
        } else {
            return identifier;
        }
    }

    /** Parses number
     * @param index stores index in string from which number should be parsed */
    private ParsingState parseNumber(int index) {
        if (index >= parsingString.length()) {
            return null;
        }

        if (parsingString.charAt(index) == '0') {
            return new ParsingState(new NumberExpression(0), 1);
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

    private FunctionHolder parseFunctionDefinition() throws IllegalFunctionDeclarationException {
        int currentIndex = 0;
        var identifier = parseIdentifier(currentIndex);

        currentIndex += increaseIndex(identifier);
        if (!isSubstring(currentIndex, "(")) {
            return null;
        }

        currentIndex += 1;
        var arguments = parseParameters(currentIndex);
        if (arguments == null) {
            return null;
        }
        currentIndex += arguments.numberOfParsedSymbols;
        if (!isSubstring(currentIndex, "={")) {
            return null;
        }

        currentIndex += 2;
        var expression = parseExpression(currentIndex);
        currentIndex += increaseIndex(expression);
        if (!isSubstring(currentIndex, "}") || parsingString.length() != currentIndex + 1) {
            return null;
        }

        if (expression == null || identifier == null) {
            return null;
        }

        return new FunctionHolder(((IdentifierExpression)identifier.getExpression()).getName(),
                arguments.arguments, expression.getExpression(), currentLineNumber);
    }

    private Parameters parseParameters(int index) {
        int currentIndex = index;
        var arguments = new ArrayList<String>();
        while (currentIndex < parsingString.length()) {
            if (parsingString.charAt(currentIndex) == ')') {
                return new Parameters(arguments, currentIndex - index + 1);
            }
            var identifier = parseIdentifier(currentIndex);
            currentIndex = successfullyParseArgument(currentIndex, identifier);
            if (currentIndex == -1 || identifier == null) {
                return null;
            }
            arguments.add(((IdentifierExpression)identifier.getExpression()).getName());
        }
        return null;
    }

    private ParsingState parseFunctionCall(int index) {
        int currentIndex = index;
        var identifier = parseIdentifier(index);

        currentIndex += increaseIndex(identifier);
        if (!isSubstring(currentIndex, "(")) {
            return null;
        }

        currentIndex++;
        var arguments = parseArguments(currentIndex);

        if (arguments == null || identifier == null) {
            return null;
        }

        currentIndex += arguments.numberOfParsedSymbols;
        var name = ((IdentifierExpression)identifier.getExpression()).getName();
        return new ParsingState(new FunctionCallExpression(name, arguments.arguments),
                currentIndex - index);
    }

    private Arguments parseArguments(int index) {
        int currentIndex = index;
        var arguments = new ArrayList<Expression>();
        while (currentIndex < parsingString.length()) {
            if (parsingString.charAt(currentIndex) == ')') {
                return new Arguments(arguments, currentIndex - index + 1);
            }
            var expression = parseExpression(currentIndex);
            currentIndex = successfullyParseArgument(currentIndex, expression);
            if (currentIndex == -1) {
                return null;
            }

            arguments.add(expression.getExpression());
        }
        return null;
    }

    /** Returns new index in string or -1 if mistake appears */
    private int successfullyParseArgument(int index, ParsingState expression) {
        if (expression == null) {
            return -1;
        }

        int currentIndex = index;

        currentIndex += expression.getNumberOfParsedSymbols();
        if (currentIndex >= parsingString.length()) {
            return -1;
        }

        char nextChar = parsingString.charAt(currentIndex);
        if (nextChar == ',') {
            currentIndex++;
        } else if (nextChar != ')') {
            return -1;
        }

        return currentIndex;
    }
}
