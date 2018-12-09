package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.List;

public class GetAverageNumberOfJudgesPerJudgmentFunction extends AbstractFunction {
    @Override
    FunctionResult invoke(String... args) {
        return new FunctionResult(getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1));

    }
}
