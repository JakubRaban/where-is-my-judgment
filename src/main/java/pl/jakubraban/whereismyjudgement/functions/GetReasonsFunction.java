package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.Collections;
import java.util.Optional;

public class GetReasonsFunction extends AbstractFunction {

    GetReasonsFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        if(args.length < 1) throw tooFewArguments;
        return getReasons(args[0]);
    }

    @Override
    String getHelpMessage() {
        return name + " \"sygnatura\" -- pokaż uzasadnienie wyroku o danej sygnaturze";
    }

    private FunctionResult getReasons(String signature) {
        Optional<Judgment> searchResult = database.search(signature).or(() -> database.searchReasons(signature));
        return searchResult.isPresent() ?
                new FunctionResult(searchResult.orElseThrow().getReasons()) :
                new FunctionResult(null, Collections.singletonList(signature), "Judgment", "");
    }
}
