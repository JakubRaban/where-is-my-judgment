package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.List;

public class GetAverageNumberOfJudgesPerJudgmentFunction extends AbstractFunction {
    GetAverageNumberOfJudgesPerJudgmentFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        return new FunctionResult(getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1));

    }

    @Override
    String getHelpMessage() {
        return name + " -- średnia liczba sędziów przypadających na jeden wyrok";
    }
}
