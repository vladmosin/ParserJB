package calculator;

import exceptions.FunctionRedefinitionException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionExecutor {
    /** Maps function name and number of arguments to function holder */
    @NotNull private Map<String, Map<Integer, FunctionHolder>> functionByNameAndArgs = new HashMap<>();

    public FunctionExecutor(@NotNull List<FunctionHolder> functions) throws FunctionRedefinitionException {
        for (var function : functions) {
            var functionName = function.getName();
            var numberOfArgs = function.getArguments().size();

            if (functionByNameAndArgs.containsKey(functionName)) {
                var functionsWithSameName = functionByNameAndArgs.get(functionName);

                if (functionsWithSameName.containsKey(numberOfArgs)) {
                    throw new FunctionRedefinitionException("Function " + function.getName() + " was redefined");
                } else {
                    functionsWithSameName.put(numberOfArgs, function);
                }
            } else {
                functionByNameAndArgs.put(functionName, Map.of(numberOfArgs, function));
            }
        }
    }

    public FunctionHolder getFunction(@NotNull String name, int numberOfArgs) {
        if (functionByNameAndArgs.containsKey(name)) {
            var functionsWithGivenName = functionByNameAndArgs.get(name);
            return functionsWithGivenName.get(numberOfArgs);
        } else {
            return null;
        }
    }

    public boolean containsFunctionWithName(@NotNull String name) {
        return functionByNameAndArgs.containsKey(name);
    }
}
