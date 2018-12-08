package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.time.Month;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class NumberOfJudgmentsByMonthFunction extends AbstractFunction {
    @Override
    FunctionResult invoke(String... args) {
        return numberOfJudgmentsByMonth();
    }
    private FunctionResult numberOfJudgmentsByMonth() {
        Map<Month, Integer> judgmentsByMonth = new LinkedHashMap<>();
        for(Month month : Month.values()) judgmentsByMonth.put(month, 0);
        getJudgmentsStream()
                .map(Judgment::getJudgmentDate)
                .map(calendar -> calendar.get(Calendar.MONTH) + 1)
                .map(Month::of)
                .forEach(month -> judgmentsByMonth.merge(month, 1, (a,b) -> a + b));
        return new FunctionResult(judgmentsByMonth);
    }

}
