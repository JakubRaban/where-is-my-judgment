package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GetMetricsFunction extends AbstractFunction {

    @Override
    FunctionResult invoke(String... args) throws IOException {
        return getMetrics(Arrays.asList(args));
    }

    private FunctionResult getMetrics(List<String> signatures) {
        List<String> result = new LinkedList<>();
        List<String> erroneousSignatures = new LinkedList<>();
        signatures.forEach(signature -> {
            Optional<Judgment> searchResult = database.search(signature).or(() -> database.searchReasons(signature));
            if (searchResult.isPresent()) {
                result.add(searchResult.orElseThrow().getMetric());
            } else {
                erroneousSignatures.add(signature);
            }
        });
        return new FunctionResult(result, erroneousSignatures, "Judgment");
    }
}
