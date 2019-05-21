package calculator;

import exceptions.*;
import org.jetbrains.annotations.NotNull;
import parser.Parser;

public class Calculator {
    private static Parser parser = new Parser();

    public static int calculate(@NotNull String[] lines) throws IllegalFunctionDeclarationException, FunctionNotFoundException, CalculationException, FunctionRedefinitionException, ArgumentNumberMismatchException, ParameterNotFoundException, ParsingException {
        try {
            var expression = parser.parse(lines);

            return expression.calculate();
        } catch (ParameterNotFoundException e) {
            System.out.println("PARAMETERS HAS SAME NAMES " + e.getMessage() + ":" + e.getLine());
            throw e;
        } catch (ArgumentNumberMismatchException e) {
           if (e.getLine() == -1) {
               e.changeLine(lines.length);
           }
            System.out.println("ARGUMENT NUMBER MISMATCH " + e.getMessage() + ":" + e.getLine());
            throw e;
        } catch (FunctionRedefinitionException e) {
            System.out.println("FUNCTION REDEFINITION " + e.getMessage() +
                               " with" + e.getNumberOfArguments() + " arguments");
            throw e;
        } catch (ParsingException e) {
            System.out.println("SYNTAX ERROR");
            throw e;
        } catch (CalculationException e) {
            if (e.getLine() == -1) {
                e.changeLine(lines.length);
            }
            System.out.println("RUNTIME ERROR " + e.getMessage() + ":" + e.getLine());
            throw e;
        } catch (FunctionNotFoundException e) {
            if (e.getLine() == -1) {
                e.changeLine(lines.length);
            }
            System.out.println("FUNCTION NOT FOUND " + e.getMessage() + ":" + e.getLine());
            throw e;
        } catch (IllegalFunctionDeclarationException e) {
            System.out.println("ARGUMENTS WITH SAME NAME FOUND " + e.getMessage() + ":" + e.getLine());
            throw e;
        }
    }

    public static int calculate(@NotNull String lines) throws FunctionNotFoundException, CalculationException, ParameterNotFoundException, FunctionRedefinitionException, ParsingException, IllegalFunctionDeclarationException, ArgumentNumberMismatchException {
        return calculate(lines.split("\n"));
    }
}
