package pl.jakubraban.whereismyjudgement.functions;

import java.util.Collections;
import java.util.List;

public class FunctionResult {

    public static final FunctionResult NONE = new FunctionResult();
    private Object result;
    private List<String> erroneousInput;
    private String errorAffectedClassName;
    private String message;

    private FunctionResult() { }

    public FunctionResult(Object result) {
        this.result = result;
        this.erroneousInput = Collections.emptyList();
        this.errorAffectedClassName = "";
        this.message = "";
    }

    public FunctionResult(Object result, String message) {
        this(result);
        this.message = message;
    }

    public FunctionResult(Object result, List<String> erroneousInput, String errorAffectedClassName, String message) {
        this(result, message);
        this.erroneousInput = erroneousInput;
        this.errorAffectedClassName = errorAffectedClassName;
    }

    public Object getResult() {
        return result;
    }

    public List<String> getErroneousInput() {
        return erroneousInput;
    }

    public String getErrorAffectedClassName() {
        return errorAffectedClassName;
    }

    public String getMessage() {
        return message;
    }
}
