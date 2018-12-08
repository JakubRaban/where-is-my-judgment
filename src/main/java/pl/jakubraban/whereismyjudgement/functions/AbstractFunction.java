package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabase;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabaseProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractFunction {

    JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();
    IllegalArgumentException tooFewArguments = new IllegalArgumentException("Bad number of arguments");

    abstract FunctionResult invoke(String ... args) throws IOException;

    Stream<Judge> getJudgeStream() {
        return getJudgmentsStream()
                .map(Judgment::getJudges)
                .flatMap(List::stream);
    }

    Stream<Judgment> getJudgmentsStream() {
        return database.getAllJudgments().stream();
    }

}
