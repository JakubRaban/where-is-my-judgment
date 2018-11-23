package pl.jakubraban.whereismyjudgement.storage;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JudgmentDatabase {

    private Map<String, Judgment> judgments;

    JudgmentDatabase() {
        judgments = new HashMap<>();
    }

    public void add(Judgment judgment) {

    }

    public void add(String key, Judgment judgment) {
        judgments.put(key, judgment);
    }

    public void search(String signature) {

    }

    public int size() {
        return judgments.size();
    }

}
