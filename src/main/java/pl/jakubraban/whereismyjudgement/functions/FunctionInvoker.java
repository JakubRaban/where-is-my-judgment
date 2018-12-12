package pl.jakubraban.whereismyjudgement.functions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FunctionInvoker {

    private Map<String, AbstractFunction> functionNames;

    private static final String SET_PATH                    = "setpath";
    private static final String TOP_JUDGES                  = "judges";
    private static final String GET_METRICS                 = "rubrum";
    private static final String GET_REASONS                 = "content";
    private static final String GET_JUDGES_JUDGMENTS        = "judge";
    private static final String JUDGMENTS_BY_MONTH          = "months";
    private static final String JUDGMENTS_BY_COURT_TYPE     = "courts";
    private static final String TOP_REFERENCED_REGULATIONS  = "regulations";
    private static final String JUDGES_NUMBER_DISTRIBUTION  = "jury";
    private static final String AVERAGE_JUDGES_PER_JUDGMENT = "avgjudges";
    private static final String EXIT                        = "exit";
    private static final String HELP                        = "help";

    public FunctionInvoker() {
        functionNames = new HashMap<>();
        functionNames.put(SET_PATH, new GetNewJudgmentsFunction(SET_PATH));
        functionNames.put(TOP_JUDGES, new GetTopJudgesFunction(TOP_JUDGES));
        functionNames.put(GET_METRICS, new GetMetricsFunction(GET_METRICS));
        functionNames.put(GET_REASONS, new GetReasonsFunction(GET_REASONS));
        functionNames.put(GET_JUDGES_JUDGMENTS, new NumberOfJudgmentsOfSpecifiedJudgeFunction(GET_JUDGES_JUDGMENTS));
        functionNames.put(JUDGMENTS_BY_MONTH, new NumberOfJudgmentsByMonthFunction(JUDGMENTS_BY_MONTH));
        functionNames.put(JUDGMENTS_BY_COURT_TYPE, new NumberOfJudgmentsByCourtTypeFunction(JUDGMENTS_BY_COURT_TYPE));
        functionNames.put(TOP_REFERENCED_REGULATIONS, new GetTopReferencedRegulationsFunction(TOP_REFERENCED_REGULATIONS));
        functionNames.put(JUDGES_NUMBER_DISTRIBUTION, new GetJudgeNumberDistributionFunction(JUDGES_NUMBER_DISTRIBUTION));
        functionNames.put(AVERAGE_JUDGES_PER_JUDGMENT, new GetAverageNumberOfJudgesPerJudgmentFunction(AVERAGE_JUDGES_PER_JUDGMENT));
        functionNames.put(EXIT, new DontWannaSeeMoreJudgmentsFunction(EXIT));
        functionNames.put(HELP, new HelpCommand(HELP));
    }

    public FunctionResult invoke(String name, String ... arguments) throws IOException {
        Optional<AbstractFunction> function = Optional.ofNullable(functionNames.get(name.toLowerCase()));
        return function.orElseThrow().invoke(arguments);
    }

    Map<String, AbstractFunction> getFunctionNames() {
        return functionNames;
    }
}
