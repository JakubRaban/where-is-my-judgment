package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.storage.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Commands {

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

    public int numberOfJudgmentsOfSpecifiedJudge(String judgeName) {
        return (int) getJudgeStream()
                .filter(judge -> judge.getName().equals(judgeName))
                .count();
    }

    public List<Judge> getTopNJudges(final int N) {
        Map<Judge, Integer> judgeCount = new HashMap<>();
        getJudgeStream()
                .forEach(judge -> judgeCount.merge(judge, 1, (a,b) -> a + b));
        return judgeCount.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .limit(N)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Stream<Judge> getJudgeStream() {
        return database.getAllJudgments().stream()
                .map(Judgment::getJudges)
                .flatMap(List::stream);
    }

}
