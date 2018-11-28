package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.storage.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        return (int) database.getAllJudgments().stream()
                .map(Judgment::getJudges)
                .flatMap(List::stream)
                .filter(judge -> judge.getName().equals(judgeName))
                .count();
    }



}
