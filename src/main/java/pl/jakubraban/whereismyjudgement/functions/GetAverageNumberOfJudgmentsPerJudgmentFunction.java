package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.List;

public class GetAverageNumberOfJudgmentsPerJudgmentFunction extends AbstractFunction {
    @Override
    FunctionResult invoke(String... args) {
        return getAverageNumberOfJudgesPerJudgment();
    }

    private FunctionResult getAverageNumberOfJudgesPerJudgment() {
        return new FunctionResult(getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1));
    }
}


