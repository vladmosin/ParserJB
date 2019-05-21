package calculator;

import exceptions.CalculationException;
import exceptions.FunctionNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionCallExpression implements Expression {
    private FunctionHolder function;
    @NotNull private String name;
    @NotNull private ArrayList<Expression> arguments;

    public FunctionCallExpression(@NotNull String name, @NotNull ArrayList<Expression> arguments) {
        this.arguments = arguments;
        this.name = name;
    }

    @Override
    public int calculate() throws CalculationException {
        var argumentValues = new ArrayList<Integer>();
        for (int i = 0; i < arguments.size(); i++) {
            argumentValues.add(arguments.get(i).calculate());
        }

        return function.getFunctionBody().applySubstitution(createSubstitution(argumentValues)).calculate();
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
        var newSubstitution = new HashMap<String, Integer>();

        for (var mapEntry : substitution.entrySet()) {
            if (!containsArgument(mapEntry.getKey())) {
                newSubstitution.put(mapEntry.getKey(), mapEntry.getValue());
            }
        }

        return function.getFunctionBody().applySubstitution(newSubstitution);
    }

    @Override
    public void link(@NotNull FunctionExecutor functionExecutor) throws FunctionNotFoundException {
        var function = functionExecutor.getFunction(name, arguments.size());
        if (function == null) {
            throw new FunctionNotFoundException("Function was not found");
        }

        this.function = function;
        for (var argument : arguments) {
            argument.link(functionExecutor);
        }
    }

    private Map<String, Integer> createSubstitution (@NotNull ArrayList<Integer> argumentValues) {
        var substitution = new HashMap<String, Integer>();

        for (int i = 0; i < argumentValues.size(); i++) {
            substitution.put(function.getArguments().get(i), argumentValues.get(i));
        }

        return substitution;
    }

    private boolean containsArgument(@NotNull String argumentName) {
        for (var argument : function.getArguments()) {
            if (argumentName.equals(argument)) {
                return true;
            }
        }

        return false;
    }
}
