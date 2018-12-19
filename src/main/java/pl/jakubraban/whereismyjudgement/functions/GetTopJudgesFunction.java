package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class GetTopJudgesFunction extends AbstractFunction {
    GetTopJudgesFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        if(args.length == 0) return getTopNJudges(10);
        else {
            try {
                return getTopNJudges(Math.abs(Integer.parseInt(args[0])));
            } catch (NumberFormatException e) {
                return getTopNJudges(10);
            }
        }
    }

    @Override
    String getHelpMessage() {
        return name + " [liczba] -- wybrana liczba sędziów o największej liczbie orzeczeń (lub 10 jeśli bez parametru)";
    }

    private FunctionResult getTopNJudges(final int N) {
        Map<String, Integer> judgeCount = new HashMap<>();
        LinkedHashMap<String, Integer> topJudges = new LinkedHashMap<>();
        getJudgeStream()
                .map(Judge::getName)
                .forEach(judge -> judgeCount.merge(judge, 1, (a, b) -> a + b));
        judgeCount.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(N)
                .forEach(entry -> topJudges.put(entry.getKey(), entry.getValue()));
        return new FunctionResult(topJudges);
    }
}
