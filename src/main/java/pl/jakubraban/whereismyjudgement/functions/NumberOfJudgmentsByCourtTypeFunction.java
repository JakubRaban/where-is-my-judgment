package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.CourtType;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.HashMap;
import java.util.Map;

public class NumberOfJudgmentsByCourtTypeFunction extends AbstractFunction {
    @Override
    FunctionResult invoke(String... args) {
        return numberOfJudgmentsByCourtType();
    }

    private FunctionResult numberOfJudgmentsByCourtType() {
        Map<CourtType, Integer> judgmentsByCourtType = new HashMap<>();
        getJudgmentsStream()
                .map(Judgment::getCourtType)
                .forEach(courtType -> judgmentsByCourtType.merge(courtType, 1, (a,b) -> a + b));
        return new FunctionResult(judgmentsByCourtType);
    }
}
