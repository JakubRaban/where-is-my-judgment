package pl.jakubraban.whereismyjudgement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.Iterator;
import java.util.List;

public class Utilities {

    public static String newLine = System.getProperty("line.separator");

    public static String getDelimitedList(List list, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            Object current = iterator.next();
            if(current != null) {
                sb.append(current.toString());
                if(iterator.hasNext()) sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String dropHTMLTags(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        String s = document.html().replaceAll("\\\\n", Utilities.newLine);
        String tagClean = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        return tagClean.replaceAll(Utilities.newLine + Utilities.newLine, Utilities.newLine);
    }
}
