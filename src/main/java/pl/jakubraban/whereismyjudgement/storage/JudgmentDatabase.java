package pl.jakubraban.whereismyjudgement.storage;

import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.*;
import java.util.stream.Collectors;

public class JudgmentDatabase {

    private Map<String, Judgment> judgments;

    JudgmentDatabase() {
        judgments = new HashMap<>();
    }

    public void add(Judgment judgment) {
        List<CourtCaseReference> numbersOfCases = judgment.getConcernedCourtCases();
        for(CourtCaseReference caseReference : numbersOfCases) {
            judgments.put(caseReference.getCaseNumber(), judgment);
        }
    }

    public void add(List<Judgment> judgments) {
        for(Judgment judgment : judgments) {
            add(judgment);
        }
    }

    public void add(String key, Judgment judgment) {
        judgments.put(key, judgment);
    }

    public Judgment search(String signature) {
        Optional<Judgment> judgment = Optional.of(judgments.get(signature));
        return judgment.orElseThrow(NoSuchElementException::new);
    }

    public Judgment remove(String signature) {
        return judgments.remove(signature);
    }

    public List<Judgment> getAllJudgments() {
        return judgments.values().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public int size() {
        return judgments.size();
    }

}
