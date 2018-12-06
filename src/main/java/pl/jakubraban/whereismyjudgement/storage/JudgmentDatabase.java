package pl.jakubraban.whereismyjudgement.storage;

import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.judgment.JudgmentType;

import java.util.*;
import java.util.stream.Collectors;

public class JudgmentDatabase {

    private Map<String, Judgment> judgments;
    private Map<String, Judgment> reasons;

    JudgmentDatabase() {
        judgments = new HashMap<>();
        reasons = new HashMap<>();
    }

    public void add(Judgment judgment) {
        List<CourtCaseReference> numbersOfCases = judgment.getConcernedCourtCases();
        for(CourtCaseReference caseReference : numbersOfCases) {
            if(judgment.getJudgmentType().equals(JudgmentType.REASONS)) {
                reasons.put(caseReference.getCaseNumber(), judgment);
                System.out.println(caseReference.getCaseNumber());
            }
            else
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

    public Optional<Judgment> search(String signature) {
        return Optional.ofNullable(judgments.get(signature));
    }

    public Optional<Judgment> searchReasons(String signature) {
        return Optional.ofNullable(reasons.get(signature));
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

    public Map<String, Judgment> getMap() {
        return judgments;
    }

}
