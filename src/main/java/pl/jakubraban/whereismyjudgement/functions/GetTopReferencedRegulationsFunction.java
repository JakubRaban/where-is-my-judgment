package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class GetTopReferencedRegulationsFunction extends AbstractFunction {
    GetTopReferencedRegulationsFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        if(args.length == 0) return getTopNReferencedRegulations(10);
        return getTopNReferencedRegulations(Integer.parseInt(args[0]));
    }

    @Override
    String getHelpMessage() {
        return name + " [liczba] -- wybrana liczba najczęściej przywoływanych aktów prawnych (lub 10 jeśli nie podana)";
    }

    private FunctionResult getTopNReferencedRegulations(final int N) {
        Map<String, Integer> regulationCount = new HashMap<>();
        LinkedHashMap<String, Integer> topRegulations = new LinkedHashMap<>();
        getJudgmentsStream()
                .map(Judgment::getReferencedRegulations)
                .flatMap(List::stream)
                .map(Regulation::getJournalTitle)
                .forEach(regulation -> regulationCount.merge(regulation, 1, (a,b) -> a + b));
        regulationCount.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(N)
                .forEach(entry -> topRegulations.put(entry.getKey(), entry.getValue()));
        return new FunctionResult(topRegulations);
    }
}
