package pl.jakubraban.whereismyjudgement.storage;

import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.*;

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

    public void add(String key, Judgment judgment) {
        judgments.put(key, judgment);
    }

    public Judgment search(String signature) {
        Judgment foundJudgment = judgments.get(signature);
        if(foundJudgment == null) throw new NoSuchElementException();
        return foundJudgment;
    }

    public Judgment remove(String signature) {
        return judgments.remove(signature);
    }

    public List<Judgment> getAllJudgments() {
        return new LinkedList<>(judgments.values());
    }

    public int size() {
        return judgments.size();
    }

}
