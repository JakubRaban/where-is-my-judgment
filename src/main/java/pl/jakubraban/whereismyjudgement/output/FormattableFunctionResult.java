package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.functions.FunctionResult;
import pl.jakubraban.whereismyjudgement.Utilities;

import java.util.*;

public class FormattableFunctionResult extends FunctionResult {

    FormattableFunctionResult(FunctionResult result) {
        super(result.getResult(), result.getErroneousInput(), result.getErrorAffectedClassName(), result.getMessage());
    }

    String format() {
        Optional<Object> objectToPrint = Optional.ofNullable(getResult());
        StringBuilder printedResult = new StringBuilder();
        if (objectToPrint.isPresent()) printedResult.append(format(objectToPrint.orElseThrow()));
        if (!getMessage().isEmpty()) printedResult.append(getMessage());
        if (!getErroneousInput().isEmpty()) appendErrorMessage(printedResult);
        return printedResult.toString();
    }

    private String getFormattedMap(HashMap map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : ((HashMap<Object, Object>) map).entrySet()) {
            sb.append(entry.getKey()).append(" -- ").append(entry.getValue()).append(Utilities.newLine);
        }
        return sb.toString();
    }

    private String format(Object o) {
        if(o instanceof HashMap) return getFormattedMap((HashMap) o);
        else if(o instanceof List) {
            StringBuilder sb = new StringBuilder();
            for(Object s : (List) o) {
                sb.append(Utilities.newLine);
                sb.append(s.toString());
            }
            return sb.toString();
        }
        else if(o instanceof String) return (String) o;
        else return o.toString();
    }

    private void appendErrorMessage(StringBuilder printedResult) {
        printedResult.append("BŁĄD: Żadna instancja ").append(getErrorAffectedClassName())
                .append(" nie wynika z użycia nazw(y) ")
                .append(Utilities.getDelimitedList(getErroneousInput(), ", "))
                .append(Utilities.newLine);
    }

}
