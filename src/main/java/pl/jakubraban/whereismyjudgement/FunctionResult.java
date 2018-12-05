package pl.jakubraban.whereismyjudgement;

import java.util.List;
import java.util.Optional;

public class FunctionResult {

    private Optional<Object> result;
    private List<String> erroneousInput;
    private String affectedClass;

    FunctionResult(Object result) {
        this.result = Optional.ofNullable(result);
    }

    FunctionResult(Object result, List<String> erroneousInput, String affectedClass) {
        this.result = Optional.ofNullable(result);
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
