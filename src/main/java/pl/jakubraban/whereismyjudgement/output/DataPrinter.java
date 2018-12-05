package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.FunctionResult;

import java.util.*;

public class DataPrinter {

    public String print(Object o) {
        if(o instanceof LinkedHashMap) return printMap((LinkedHashMap) o);
        else if(o instanceof HashMap) return printMap ((HashMap) o);
        else if(o instanceof List) {
            StringBuilder sb = new StringBuilder();
            for(Object s : (List) o) {
                sb.append(s.toString()).append("\n");
            }
            return sb.toString();
        }
        else if(o instanceof String) return o + "\n";
        else return o.toString() + "\n";
    }

    private String printMap(LinkedHashMap map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : ((LinkedHashMap<Object, Object>) map).entrySet()) {
            sb.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    private String printMap(HashMap map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : ((HashMap<Object, Object>) map).entrySet()) {
            sb.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String print(FunctionResult result) {
        Optional<Object> objectToPrint = Optional.ofNullable(result.getResult());
        List<String> erroneousInput = result.getErroneousInput();
        String affectedClass = result.getAffectedClass();
        StringBuilder printedResult = new StringBuilder();
        if(objectToPrint.isPresent()) {
            printedResult.append(print(objectToPrint.orElseThrow()));
            if(!erroneousInput.isEmpty()) {

            }
        }
        return printedResult.toString();
    }

}
