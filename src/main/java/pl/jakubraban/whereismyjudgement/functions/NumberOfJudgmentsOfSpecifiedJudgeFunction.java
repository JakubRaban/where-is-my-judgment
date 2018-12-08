package pl.jakubraban.whereismyjudgement.functions;

public class NumberOfJudgmentsOfSpecifiedJudgeFunction extends AbstractFunction {

    @Override
    FunctionResult invoke(String... args) {
        if(args.length < 1) throw argumentException;
        else if(args.length > 1) return numberOfJudgmentsOfSpecifiedJudge(args[0], args[1]);
        else return numberOfJudgmentsOfSpecifiedJudge(args[0]);
    }

    private FunctionResult numberOfJudgmentsOfSpecifiedJudge(String fullJudgeName) {
        int numberOfJudgments = (int) getJudgeStream()
                .filter(judge -> judge.getName().toLowerCase().equals(fullJudgeName.toLowerCase()))
                .count();
        return new FunctionResult(numberOfJudgments);
    }

    private FunctionResult numberOfJudgmentsOfSpecifiedJudge(String firstname, String lastname) {
        return numberOfJudgmentsOfSpecifiedJudge(firstname + " " + lastname);
    }

}
