package pl.jakubraban.whereismyjudgement.input;

import pl.jakubraban.whereismyjudgement.Functions;

public class CommandParser {

    private static final String SET_PATH                   = "setPath";
    private static final String TOP_JUDGES                 = "topJudges";
    private static final String GET_METRICS                = "getMetrics";
    private static final String GET_REASONS                = "getReasons";
    private static final String GET_JUDGES_JUDGMENTS       = "getJudge'sJudgments";
    private static final String JUDGMENTS_BY_MONTH         = "judgmentsByMonth";
    private static final String JUDGMENTS_BY_COURT_TYPE    = "judgmentsByCourtType";
    private static final String TOP_REFERENCED_REGULATIONS = "topReferencedRegulations";
    private static final String AVERAGE_JUDGES_NUMBER      = "averageJudgesNumber";

    public void parse(String command, Functions functions) {
        switch(command) {

        }
    }
}
