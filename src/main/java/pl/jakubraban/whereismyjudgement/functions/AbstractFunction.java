package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.FunctionResult;

public abstract class AbstractFunction {

    abstract FunctionResult invoke(String ... args);

}
