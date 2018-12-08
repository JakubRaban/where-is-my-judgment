package pl.jakubraban.whereismyjudgement.input;

import pl.jakubraban.whereismyjudgement.functions.FunctionInvoker;
import pl.jakubraban.whereismyjudgement.functions.FunctionResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {

    private FunctionInvoker invoker = new FunctionInvoker();

    public FunctionResult parse(String input) throws IOException {
        if (input.isEmpty()) return new FunctionResult(null);
        String[] commandParts = splitInputBySpacesOutsideQuotes(input);
        int numberOfArguments = commandParts.length - 1;
        String command = commandParts[0];
        String[] arguments = new String[numberOfArguments];
        System.arraycopy(commandParts, 1, arguments, 0, numberOfArguments);
        return invoker.invoke(command, arguments);
    }

    private String[] splitInputBySpacesOutsideQuotes(String input) {
        String splitBySpacesOutsideQuotesRegex = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] commandParts = input.split(splitBySpacesOutsideQuotesRegex);
        for (int i = 0; i < commandParts.length; i++)
            commandParts[i] = commandParts[i].replaceAll("\"", "");
        return commandParts;
    }

    public String getCommand(String input) {
        return splitInputBySpacesOutsideQuotes(input)[0];
    }

    public List<String> getArguments(String input) {
        return Arrays.stream(splitInputBySpacesOutsideQuotes(input)).skip(1).collect(Collectors.toList());
    }

}
