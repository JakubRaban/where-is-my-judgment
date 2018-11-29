package pl.jakubraban.whereismyjudgement.input;

import pl.jakubraban.whereismyjudgement.Functions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandParser {

    private static final String SET_PATH                   = "setPath";
    private static final String TOP_JUDGES                 = "topJudges";
    private static final String GET_METRICS                = "getMetrics";
    private static final String GET_REASONS                = "getReasons";
    private static final String GET_JUDGES_JUDGMENTS       = "howManyJudgments";
    private static final String JUDGMENTS_BY_MONTH         = "judgmentsByMonth";
    private static final String JUDGMENTS_BY_COURT_TYPE    = "judgmentsByCourtType";
    private static final String TOP_REFERENCED_REGULATIONS = "topReferencedRegulations";
    private static final String AVERAGE_JUDGES_NUMBER      = "averageJudgesNumber";

    private Functions functions;

    public CommandParser(Functions functions) {
        this.functions = functions;
    }

    public Object parse(String input) {
        String[] commandParts = input.split(" ");
        int numberOfArguments = commandParts.length - 1;
        String command = commandParts[0];
        String[] arguments = {};
        if(numberOfArguments > 0) arguments = Arrays.copyOfRange(commandParts, 1, numberOfArguments);
        IllegalArgumentException ex =
                new IllegalArgumentException("Too few arguments for command" + commandParts[0]);
        switch(command) {
            case SET_PATH:
                if(numberOfArguments < 1) throw ex;
                return functions.getReaderForSpecifiedPath(arguments[0]);
            case GET_METRICS:
                if(numberOfArguments < 1) throw ex;
                List<String> metrics = new LinkedList<>(Arrays.asList(arguments));
                return functions.getMetrics(metrics);
            case TOP_JUDGES:
                if(numberOfArguments < 1) functions.getTopNJudges(10);
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
                if(numberOfArguments < 1) functions.getTopNReferencedRegulations(10);
                int numberOfRegulations = Integer.parseInt(arguments[0]);
                return functions.getTopNReferencedRegulations(numberOfRegulations);
            case AVERAGE_JUDGES_NUMBER:
                return functions.getAverageNumberOfJudgesPerJudgment();
            default:
                throw new IllegalArgumentException("Bad command");
        }
    }
}
