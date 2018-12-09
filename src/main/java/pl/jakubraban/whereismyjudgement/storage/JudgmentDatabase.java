package pl.jakubraban.whereismyjudgement.storage;

import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.*;
import java.util.stream.Collectors;

public class JudgmentDatabase {

    private Map<String, Judgment> judgments;
    private Map<String, Judgment> reasons;

    JudgmentDatabase() {
        judgments = new HashMap<>();
        reasons = new HashMap<>();
    }

    private void add(Judgment judgment) {
        List<CourtCaseReference> numbersOfCases = judgment.getConcernedCourtCases();
        for(CourtCaseReference caseReference : numbersOfCases) {
            if(judgment.isReasons())
                reasons.put(caseReference.getCaseNumber(), judgment);
            else if(judgment.getJudges().size() != 0)
                judgments.put(caseReference.getCaseNumber(), judgment);
        }
    }

    public void add(List<Judgment> judgments) {
        for(Judgment judgment : judgments) {
            add(judgment);
        }
        mergeReasons();
    }

    private void mergeReasons() {
        Set<String> reasonsSignatures = new HashSet<>(reasons.keySet());
        for(String signature : reasonsSignatures) {
            Optional<Judgment> properJudgment = search(signature);
            if(properJudgment.isPresent()) {
                properJudgment.orElseThrow().mergeWithReasons(searchReasons(signature).orElseThrow());
                reasons.remove(signature);
                judgments.put(signature, properJudgment.orElseThrow());
            }
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
