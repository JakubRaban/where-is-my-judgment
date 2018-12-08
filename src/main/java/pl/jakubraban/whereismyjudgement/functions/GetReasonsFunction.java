package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.Collections;
import java.util.Optional;

public class GetReasonsFunction extends AbstractFunction {

    @Override
    FunctionResult invoke(String... args) {
        return getReasons(args[0]);
    }

    private FunctionResult getReasons(String signature) {
        Optional<Judgment> searchResult = database.search(signature).or(() -> database.searchReasons(signature));
        return searchResult.isPresent() ?
                new FunctionResult(searchResult.orElseThrow().getReasons()) :
                new FunctionResult(null, Collections.singletonList(signature), "Judgment");
    }
}
