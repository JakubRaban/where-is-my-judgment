package pl.jakubraban.whereismyjudgement;

import java.util.Collections;
import java.util.List;

public class FunctionResult {

    private Object result;
    private List<String> erroneousInput;
    private String affectedClass;

    FunctionResult(Object result) {
        this.result = result;
        this.erroneousInput = Collections.emptyList();
        this.affectedClass = "";
    }

    FunctionResult(Object result, List<String> erroneousInput, String affectedClass) {
        this.result = result;
        this.erroneousInput = erroneousInput;
        this.affectedClass = affectedClass;
    }

    public Object getResult() {
        return result;
    }

    public List<String> getErroneousInput() {
        return erroneousInput;
    }

    public String getAffectedClass() {
        return affectedClass;
    }

}
