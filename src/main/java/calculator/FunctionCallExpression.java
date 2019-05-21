package calculator;

import exceptions.ArgumentNumberMismatchException;
import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import exceptions.ParameterNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Stores function call expression */
public class FunctionCallExpression implements Expression {
    /** Linked function */
    private FunctionHolder function;

    /** Function name */
    @NotNull private String name;

    /** Arguments */
    @NotNull private ArrayList<Expression> arguments;

    public FunctionCallExpression(@NotNull String name, @NotNull ArrayList<Expression> arguments) {
        this.arguments = arguments;
        this.name = name;
    }

    private FunctionCallExpression(@NotNull String name, @NotNull ArrayList<Expression> arguments,
                                   @NotNull FunctionHolder function) {
        this.arguments = arguments;
        this.name = name;
        this.function = function;
    }

    @Override
    @NotNull public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("(");
        for (int i = 0; i < arguments.size(); i++) {
            stringBuilder.append(arguments.get(i).toString());
            if (i != arguments.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public int calculate() throws CalculationException, ParameterNotFoundException {
        var argumentValues = new ArrayList<Integer>();
        try {
            for (Expression argument : arguments) {
                argumentValues.add(argument.calculate());
            }

            return function.getFunctionBody().applySubstitution(createSubstitution(argumentValues)).calculate();
        } catch (CalculationException e) {
            if (e.getLine() == -1) {
                e.changeLine(function.getLine());
            }
            throw e;
        } catch (ParameterNotFoundException e) {
            if (e.getLine() == -1) {
                e.changeLine(function.getLine());
            }
            throw e;
        }
    }

    @Override
    public boolean isEqual(Expression expression) {
        if (expression == null) {
            return false;
        }

        if (expression instanceof FunctionCallExpression) {
            var functionCallExpression = (FunctionCallExpression) expression;
            return arguments.equals(functionCallExpression.arguments) && function == functionCallExpression.function;
        }

        return false;
    }

    @Override
    public @NotNull Expression applySubstitution(@NotNull Map<String, Integer> substitution) {
        var argumentsWithSubstitution = new ArrayList<Expression>();

        for (Expression argument : arguments) {
            argumentsWithSubstitution.add(argument.applySubstitution(substitution));
        }

        return new FunctionCallExpression(name, argumentsWithSubstitution, function);
    }

    @Override
    public void link(@NotNull FunctionLinker functionLinker)
            throws FunctionNotFoundException, ArgumentNumberMismatchException {
        var function = functionLinker.getFunction(name, arguments.size());
        if (function == null) {
            if (!functionLinker.containsFunctionWithName(name)) {
                throw new FunctionNotFoundException(name, -1);
            } else {
                throw new ArgumentNumberMismatchException(name, -1);
            }
        }

        this.function = function;

        try {
            for (var argument : arguments) {
                argument.link(functionLinker);
            }
        } catch (FunctionNotFoundException e) {
            if (e.getLine() == -1) {
                e.changeLine(function.getLine());
                throw e;
            }
        } catch (ArgumentNumberMismatchException e) {
            if (e.getLine() == -1) {
                e.changeLine(function.getLine());
                throw e;
            }
        }
    }

    /** Maps function parameters with their values */
    private Map<String, Integer> createSubstitution(@NotNull ArrayList<Integer> argumentValues) {
        var substitution = new HashMap<String, Integer>();

        for (int i = 0; i < argumentValues.size(); i++) {
            substitution.put(function.getArguments().get(i), argumentValues.get(i));
        }

        return substitution;
    }
}
