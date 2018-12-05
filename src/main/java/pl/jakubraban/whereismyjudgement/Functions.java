package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtType;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;
import pl.jakubraban.whereismyjudgement.input.JudgmentDirectoryReader;
import pl.jakubraban.whereismyjudgement.input.JudgmentJSONParser;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabase;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabaseProvider;

import java.io.IOException;
import java.text.ParseException;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class Functions {

    private JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();

    public void getNewJudgments(String path) throws IOException {
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
    }

    public FunctionResult getMetrics(List<String> signatures) {
        List<String> result = new LinkedList<>();
        List<String> erroneousSignatures = new LinkedList<>();
        signatures.forEach(signature -> {
            Optional<Judgment> searchResult = database.search(signature);
            if (searchResult.isPresent()) {
                result.add(searchResult.orElseThrow().getMetric());
            } else {
                erroneousSignatures.add(signature);
            }
        });
        return new FunctionResult(result, erroneousSignatures, "Judgment");
    }

    public FunctionResult getReasons(String signature) {
        Optional<Judgment> searchResult = database.search(signature);
        return searchResult.isPresent() ?
                new FunctionResult(searchResult.orElseThrow().getReasons()) :
                new FunctionResult(null, Collections.singletonList(signature), "Judgment");
    }

    public FunctionResult numberOfJudgmentsOfSpecifiedJudge(String judgeName) {
        int numberOfJudgments = (int) getJudgeStream()
                .filter(judge -> judge.getName().equals(judgeName))
                .count();
        return new FunctionResult(numberOfJudgments);
    }

    public FunctionResult getTopNJudges(final int N) {
        Map<String, Integer> judgeCount = new HashMap<>();
        LinkedHashMap<String, Integer> topJudges = new LinkedHashMap<>();
        getJudgeStream()
                .map(Judge::getName)
                .forEach(judge -> judgeCount.merge(judge, 1, (a,b) -> a + b));
        judgeCount.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(N)
                .forEach(entry -> topJudges.put(entry.getKey(), entry.getValue()));
        return new FunctionResult(topJudges);
    }

    public FunctionResult numberOfJudgmentsByMonth() {
        Map<Month, Integer> judgmentsByMonth = new LinkedHashMap<>();
        for(Month month : Month.values()) judgmentsByMonth.put(month, 0);
        getJudgmentsStream()
                .map(Judgment::getJudgmentDate)
                .map(calendar -> calendar.get(Calendar.MONTH))
                .map(Month::of)
                .forEach(month -> judgmentsByMonth.merge(month, 1, (a,b) -> a + b));
        return new FunctionResult(judgmentsByMonth);
    }

    public FunctionResult numberOfJudgmentsByCourtType() {
        Map<CourtType, Integer> judgmentsByCourtType = new HashMap<>();
        getJudgmentsStream()
                .map(Judgment::getCourtType)
                .forEach(courtType -> judgmentsByCourtType.merge(courtType, 1, (a,b) -> a + b));
        return new FunctionResult(judgmentsByCourtType);
    }

    public FunctionResult getTopNReferencedRegulations(final int N) {
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

    public FunctionResult getAverageNumberOfJudgesPerJudgment() {
        return new FunctionResult(getJudgmentsStream()
                .map(Judgment::getJudges)
                .mapToDouble(List::size)
                .average()
                .orElse(-1));
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
