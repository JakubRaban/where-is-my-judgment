package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtType;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;
import pl.jakubraban.whereismyjudgement.storage.*;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class Functions {

    private JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();

    public JudgmentDirectoryReader getReaderForSpecifiedPath(String path) {
        return new JudgmentDirectoryReader(path);
    }

    public List<String> getMetrics(List<String> signatures) {
        List<Judgment> judgments = new LinkedList<>();
        for(String signature : signatures) {
            judgments.add(database.search(signature));
        }
        return judgments.stream()
                .map(Judgment::getMetric)
                .collect(Collectors.toList());
    }

    public String getReasons(String signature) {
        return database.search(signature).getReasons();
    }

    public Integer numberOfJudgmentsOfSpecifiedJudge(String judgeName) {
        return (int) getJudgeStream()
                .filter(judge -> judge.getName().equals(judgeName))
                .count();
    }

    public Map<Judge, Integer> getTopNJudges(final int N) {
        Map<Judge, Integer> judgeCount = new HashMap<>();
        Map<Judge, Integer> topJudges = new HashMap<>();
        getJudgeStream()
                .forEach(judge -> judgeCount.merge(judge, 1, (a,b) -> a + b));
        judgeCount.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(N)
                .forEach(entry -> topJudges.put(entry.getKey(), entry.getValue()));
        return topJudges;
    }

    public Map<Month, Integer> numberOfJudgmentsByMonth() {
        Map<Month, Integer> judgmentsByMonth = new HashMap<>();
        getJudgmentsStream()
                .map(Judgment::getJudgmentDate)
                .map(calendar -> calendar.get(Calendar.MONTH))
                .map(Month::of)
                .forEach(month -> judgmentsByMonth.merge(month, 1, (a,b) -> a + b));
        return judgmentsByMonth;
    }

    public Map<CourtType, Integer> numberOfJudgmentsByCourtType() {
        Map<CourtType, Integer> judgmentsByCourtType = new HashMap<>();
        getJudgmentsStream()
                .map(Judgment::getCourtType)
                .forEach(courtType -> judgmentsByCourtType.merge(courtType, 1, (a,b) -> a + b));
        return judgmentsByCourtType;
    }

    public Map<String, Integer> getTopNReferencedRegulations(final int N) {
        Map<String, Integer> regulationCount = new HashMap<>();
        Map<String, Integer> topRegulations = new HashMap<>();
        getJudgmentsStream()
                .map(Judgment::getReferencedRegulations)
                .flatMap(List::stream)
                .map(Regulation::getJournalTitle)
                .forEach(regulation -> regulationCount.merge(regulation, 1, (a,b) -> a + b));
        regulationCount.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(N)
                .forEach(entry -> topRegulations.put(entry.getKey(), entry.getValue()));
        return topRegulations;
    }

    public Double getAverageNumberOfJudgesPerJudgment() {
        return getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1);
    }

    private Stream<Judge> getJudgeStream() {
        return getJudgmentsStream()
                .map(Judgment::getJudges)
                .flatMap(List::stream);
    }

    private Stream<Judgment> getJudgmentsStream() {
        return database.getAllJudgments().stream();
    }

}
