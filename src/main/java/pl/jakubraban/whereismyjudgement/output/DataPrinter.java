package pl.jakubraban.whereismyjudgement.output;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataPrinter {

    public void print(Object o) throws IOException {
        if(o instanceof LinkedHashMap) printMap((LinkedHashMap) o);
        else if(o instanceof List) {
            for(Object s : (List) o) {
                System.out.println(s.toString());
                System.out.println();
            }
        }
        else if(o instanceof String) System.out.println((String) o);
        else System.out.println(o);
        System.out.println();
    }

    private void printMap(LinkedHashMap map) throws IOException {
        for(Map.Entry entry : ((LinkedHashMap<Object, Object>) map).entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

}
