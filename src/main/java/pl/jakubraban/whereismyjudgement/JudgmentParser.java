package pl.jakubraban.whereismyjudgement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class JudgmentParser {

    public LinkedList<Judgment> parse(String json) {
        String properJson = dropMetadata(json);
        Type judgmentListType = new TypeToken<LinkedList<Judgment>>(){}.getType();
        LinkedList<Judgment> judgments = new Gson().fromJson(properJson, judgmentListType);
        return judgments;
    }

    private String dropMetadata(String parsedJson) {
        int start = parsedJson.indexOf("items") + 7;
        int end = parsedJson.indexOf("queryTemplate") - 2;
        return parsedJson.substring(start, end);
    }

}
