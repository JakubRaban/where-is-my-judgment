package pl.jakubraban.whereismyjudgement.functions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FunctionInvoker {

    private Map<String, AbstractFunction> functionNames;

    private static final String SET_PATH                    = "setpath";
    private static final String TOP_JUDGES                  = "topjudges";
    private static final String GET_METRICS                 = "getmetrics";
    private static final String GET_REASONS                 = "getreasons";
    private static final String GET_JUDGES_JUDGMENTS        = "howmanyjudgments";
    private static final String JUDGMENTS_BY_MONTH          = "judgmentsbymonth";
    private static final String JUDGMENTS_BY_COURT_TYPE     = "judgmentsbycourttype";
    private static final String TOP_REFERENCED_REGULATIONS  = "topreferencedregulations";
    private static final String JUDGES_NUMBER_DISTRIBUTION  = "judgenumberdistribution";
    private static final String AVERAGE_JUDGES_PER_JUDGMENT = "averagejudgesperjudgment";
    private static final String EXIT                        = "exit";

    public FunctionInvoker() {
        functionNames = new HashMap<>();
        functionNames.put(SET_PATH, new GetNewJudgmentsFunction());
        functionNames.put(TOP_JUDGES, new GetTopJudgesFunction());
        functionNames.put(GET_METRICS, new GetMetricsFunction());
        functionNames.put(GET_REASONS, new GetReasonsFunction());
        functionNames.put(GET_JUDGES_JUDGMENTS, new NumberOfJudgmentsOfSpecifiedJudgeFunction());
        functionNames.put(JUDGMENTS_BY_MONTH, new NumberOfJudgmentsByMonthFunction());
        functionNames.put(JUDGMENTS_BY_COURT_TYPE, new NumberOfJudgmentsByCourtTypeFunction());
        functionNames.put(TOP_REFERENCED_REGULATIONS, new GetTopReferencedRegulationsFunction());
        functionNames.put(JUDGES_NUMBER_DISTRIBUTION, new GetJudgeNumberDistributionFunction());
        functionNames.put(AVERAGE_JUDGES_PER_JUDGMENT, new GetAverageNumberOfJudgesPerJudgmentFunction());
        functionNames.put(EXIT, new DontWannaSeeMoreJudgmentsFunction());
    }

    public FunctionResult invoke(String name, String ... arguments) throws IOException {
        Optional<AbstractFunction> function = Optional.ofNullable(functionNames.get(name.toLowerCase()));
        return function.orElseThrow().invoke(arguments);
    }
}
