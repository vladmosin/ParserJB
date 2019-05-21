package calculator;

import exceptions.IllegalFunctionDeclarationException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FunctionHolder {
    @NotNull private String name;
    @NotNull private ArrayList<String> argumentList;
    @NotNull private Expression functionBody;
    private int line;

    public FunctionHolder(@NotNull String name, @NotNull ArrayList<String> argumentList,
                          @NotNull Expression functionBody, int line) throws IllegalFunctionDeclarationException {
        this.name = name;
        this.argumentList = argumentList;
        this.functionBody = functionBody;
        this.line = line;

        if (!checkArgumentList()) {
            throw new IllegalFunctionDeclarationException("Function " + name + " has arguments with same name");
        }
    }

    @NotNull public String getName() {
        return name;
    }

    @NotNull public List<String> getArguments() {
        return argumentList;
    }

    @NotNull public Expression getFunctionBody() {
        return functionBody;
    }

    /** Returns true if there is no arguments with same name */
    private boolean checkArgumentList() {
        return (new HashSet<String>(argumentList)).size() == argumentList.size();
    }

    public int getLine() {
        return line;
    }
}
