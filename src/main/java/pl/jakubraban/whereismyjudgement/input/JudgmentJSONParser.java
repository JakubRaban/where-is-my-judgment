package pl.jakubraban.whereismyjudgement.input;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class JudgmentJSONParser {

    public LinkedList<Judgment> parse(String json)  {
        try {
            String properJson = dropMetadata(json);
            Type judgmentListType = new TypeToken<LinkedList<Judgment>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(Calendar.class, (JsonDeserializer<Calendar>) (element, arg1, arg2) -> {
                String date = element.getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(date));
                    return cal;
                } catch (ParseException e) {
                    return null;
                }
            }).create();
            return gson.fromJson(properJson, judgmentListType);
        } catch (ParseException | JsonSyntaxException e) {
            return new LinkedList<>();
        }
    }

    private String dropMetadata(String parsedJson) throws ParseException {
        int start = parsedJson.indexOf("items") + 7;
        int end = parsedJson.indexOf("queryTemplate") - 2;
        if(start == 6 || end == -3) throw new ParseException("The file is not a proper judgment JSON", 0);
        return parsedJson.substring(start, end);
    }

}
