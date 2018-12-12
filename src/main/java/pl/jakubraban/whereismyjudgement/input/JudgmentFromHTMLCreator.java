package pl.jakubraban.whereismyjudgement.input;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JudgmentFromHTMLCreator {

    public Judgment create() {
        return null;
    }

    public Map<String, String> getParametersList(Path pathToHtml) throws IOException {
        Map<String, String> judgmentContents = new HashMap<>();
        File html = pathToHtml.toFile();
        Document document = Jsoup.parse(html, "UTF-8");
        Element table = document.select("table").get(3);
        Elements rows = table.select("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element currentRow = rows.get(i);
            Elements keys = currentRow.getElementsByClass("info-list-label");
            String key = null;
            for(int j = 0; j < keys.size(); j++) {
                Elements currentKey = keys.get(j).getElementsByClass("lista-label");
                key = currentKey.text();
            }
            Elements values = currentRow.getElementsByClass("info-list-value");
            Element currentValue = values.first();
            String value = null;
            if(currentValue != null) value = currentValue.text().trim().replaceAll("orzeczenie.*","");
            if (value != null && key != null) judgmentContents.put(key, value);
        }
        return judgmentContents;
    }

}
