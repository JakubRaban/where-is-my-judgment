package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import static java.util.Comparator.comparing;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetJudgeNumberDistributionFunction extends AbstractFunction {
    GetJudgeNumberDistributionFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        return getAverageNumberOfJudgesPerJudgment();
    }

    @Override
    String getHelpMessage() {
        return name + " -- dane na temat tego ile wyroków zapadło w danej liczebności składu sędziowskiegp";
    }

    private FunctionResult getAverageNumberOfJudgesPerJudgment() {
        Map<String, Integer> numberOfJudgesCount = new HashMap<>();
        LinkedHashMap<String, Integer> sortedCount = new LinkedHashMap<>();
        getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToInt(List::size)
                .forEach(number -> numberOfJudgesCount.merge(number + "-osobowy", 1, (a,b) -> a + b));
        numberOfJudgesCount.entrySet().stream()
                .sorted(comparing(entry -> Integer.parseInt(entry.getKey().substring(0, entry.getKey().indexOf('-')))))
                .forEach(entry -> sortedCount.put(entry.getKey(), entry.getValue()));
        return new FunctionResult(sortedCount);
    }
}


