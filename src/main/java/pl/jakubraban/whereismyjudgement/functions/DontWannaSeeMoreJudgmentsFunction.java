package pl.jakubraban.whereismyjudgement.functions;

import java.io.IOException;

public class DontWannaSeeMoreJudgmentsFunction extends AbstractFunction {
    DontWannaSeeMoreJudgmentsFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) throws IOException {
        System.exit(0);
        return null;
    }

    @Override
    String getHelpMessage() {
        return name + " -- wyjście z programu";
    }
}
