package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtType;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;
import pl.jakubraban.whereismyjudgement.input.JudgmentDirectoryReader;
import pl.jakubraban.whereismyjudgement.input.JudgmentJSONParser;
import pl.jakubraban.whereismyjudgement.storage.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class Functions {

    private JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();

    public int getNewJudgments(String path) throws IOException {
        JudgmentDirectoryReader reader = new JudgmentDirectoryReader(path);
        JudgmentJSONParser parser = new JudgmentJSONParser();
        List<String> allJsons = reader.getFilesContents();
        int newJudgmentsCounter = 0;
        for(String json : allJsons) {
            try {
                List<Judgment> judgments = parser.parse(json);
                newJudgmentsCounter += judgments.size();
                database.add(judgments);
            } catch (ParseException e) {
                System.out.println("Zły plik orzeczenia - nie dodano");
            }
        }
        return newJudgmentsCounter;
    }

    public List<String> getMetrics(List<String> signatures) {
        return signatures.stream()
                .map(signature -> database.search(signature))
                .distinct()
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

    public LinkedHashMap<Judge, Integer> getTopNJudges(final int N) {
        Map<Judge, Integer> judgeCount = new HashMap<>();
        LinkedHashMap<Judge, Integer> topJudges = new LinkedHashMap<>();
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

    public LinkedHashMap<String, Integer> getTopNReferencedRegulations(final int N) {
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
        return topRegulations;
    }

    public double getAverageNumberOfJudgesPerJudgment() {
        return getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1);
    }

    public void exit() {
        System.exit(0);
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
