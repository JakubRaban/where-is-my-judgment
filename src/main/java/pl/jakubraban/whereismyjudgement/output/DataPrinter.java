package pl.jakubraban.whereismyjudgement.output;

import java.util.*;

public class DataPrinter {

    public void print(Object o) {
        if(o instanceof Map) printMap((Map) o);
        if(o instanceof List) {
            for(Object s : (List) o) {
                System.out.println(s);
                System.out.println();
            }
        }
        if(o instanceof String) System.out.println((String) o);
    }

    private void printMap(Map map) {
        for(Map.Entry entry : ((Map<Object, Object>) map).entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

}
