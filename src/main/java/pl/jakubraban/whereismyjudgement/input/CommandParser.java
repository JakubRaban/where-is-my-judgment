package pl.jakubraban.whereismyjudgement.input;

import pl.jakubraban.whereismyjudgement.FunctionResult;
import pl.jakubraban.whereismyjudgement.Functions;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {

    private static final String SET_PATH                   = "setpath";
    private static final String TOP_JUDGES                 = "topjudges";
    private static final String GET_METRICS                = "getmetrics";
    private static final String GET_REASONS                = "getreasons";
    private static final String GET_JUDGES_JUDGMENTS       = "howmanyjudgments";
    private static final String JUDGMENTS_BY_MONTH         = "judgmentsbymonth";
    private static final String JUDGMENTS_BY_COURT_TYPE    = "judgmentsbycourttype";
    private static final String TOP_REFERENCED_REGULATIONS = "topreferencedregulations";
    private static final String AVERAGE_JUDGES_NUMBER      = "averagejudgesnumber";
    private static final String EXIT                       = "exit";

    private Functions functions;

    public CommandParser(Functions functions) {
        this.functions = functions;
    }

    public FunctionResult parse(String input) throws IOException {
        String[] commandParts = splitInputBySpacesOutsideQuotes(input);
        int numberOfArguments = commandParts.length - 1;
        String command = commandParts[0];
        String[] arguments = new String[numberOfArguments];
        for(int i = 0; i < numberOfArguments; i++) {
            arguments[i] = commandParts[i + 1];
        }
        IllegalArgumentException ex =
                new IllegalArgumentException("Too few arguments for command " + commandParts[0]);
        switch(command.toLowerCase()) {
            case SET_PATH:
                if(numberOfArguments < 1) throw ex;
                return functions.getNewJudgments(arguments[0]);
            case GET_METRICS:
                if(numberOfArguments < 1) throw ex;
                List<String> metrics = new LinkedList<>(Arrays.asList(arguments));
                return functions.getMetrics(metrics);
            case TOP_JUDGES:
                if(numberOfArguments < 1) return functions.getTopNJudges(10);
                int numberOfJudges = Integer.parseInt(arguments[0]);
                return functions.getTopNJudges(numberOfJudges);
            case GET_REASONS:
                if(numberOfArguments < 1) throw ex;
                return functions.getReasons(arguments[0]);
            case GET_JUDGES_JUDGMENTS:
                if(numberOfArguments < 1) throw ex;
                return functions.numberOfJudgmentsOfSpecifiedJudge(arguments[0]);
            case JUDGMENTS_BY_MONTH:
                return functions.numberOfJudgmentsByMonth();
            case JUDGMENTS_BY_COURT_TYPE:
                return functions.numberOfJudgmentsByCourtType();
            case TOP_REFERENCED_REGULATIONS:
                if(numberOfArguments < 1) return functions.getTopNReferencedRegulations(10);
                int numberOfRegulations = Integer.parseInt(arguments[0]);
                return functions.getTopNReferencedRegulations(numberOfRegulations);
            case AVERAGE_JUDGES_NUMBER:
                return functions.getAverageNumberOfJudgesPerJudgment();
            case EXIT:
                functions.exit();
            default:
                throw new IllegalArgumentException("Bad command");
        }
    }

    private String[] splitInputBySpacesOutsideQuotes(String input) {
        String splitBySpacesOutsideQuotesRegex = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] commandParts = input.split(splitBySpacesOutsideQuotesRegex);
        for(int i = 0; i < commandParts.length; i++)
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
