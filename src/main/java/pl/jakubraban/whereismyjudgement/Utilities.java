package pl.jakubraban.whereismyjudgement;

import java.util.Iterator;
import java.util.List;

public class Utilities {

    public static String getListWithCommas(List list) {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            Object current = iterator.next();
            if(current != null) {
                sb.append(current.toString());
                if(iterator.hasNext()) sb.append(", ");
            }
        }
        return sb.toString();
    }

}
