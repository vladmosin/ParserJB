package calculator;

import exceptions.FunctionRedefinitionException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Needs for function linking */
public class FunctionLinker {
    /** Maps function name and number of arguments to function holder */
    @NotNull private Map<String, Map<Integer, FunctionHolder>> functionByNameAndArgs = new HashMap<>();

    public FunctionLinker(@NotNull List<FunctionHolder> functions) throws FunctionRedefinitionException {
        for (var function : functions) {
            var functionName = function.getName();
            var numberOfArgs = function.getArguments().size();

            if (functionByNameAndArgs.containsKey(functionName)) {
                var functionsWithSameName = functionByNameAndArgs.get(functionName);

                if (functionsWithSameName.containsKey(numberOfArgs)) {
                    throw new FunctionRedefinitionException(function.getName(), function.getArguments().size());
                } else {
                    functionsWithSameName.put(numberOfArgs, function);
                }
            } else {
                var functionsWithSuchName = new HashMap<Integer, FunctionHolder>();
                functionsWithSuchName.put(numberOfArgs, function);
                functionByNameAndArgs.put(functionName, functionsWithSuchName);
            }
        }
    }


    /** Returns function by number of arguments and name */
    public FunctionHolder getFunction(@NotNull String name, int numberOfArgs) {
        if (functionByNameAndArgs.containsKey(name)) {
            var functionsWithGivenName = functionByNameAndArgs.get(name);
            return functionsWithGivenName.get(numberOfArgs);
        } else {
            return null;
        }
    }

    /** Checks that exists function with given name */
    public boolean containsFunctionWithName(@NotNull String name) {
        return functionByNameAndArgs.containsKey(name);
    }
}
