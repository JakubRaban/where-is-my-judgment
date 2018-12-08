package pl.jakubraban.whereismyjudgement.functions;

import java.io.IOException;

public class DontWannaSeeMoreJudgmentsFunction extends AbstractFunction {
    @Override
    FunctionResult invoke(String... args) throws IOException {
        System.exit(0);
        return null;
    }
}
