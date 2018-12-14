package pl.jakubraban.whereismyjudgement.functions;

public class NumberOfJudgmentsOfSpecifiedJudgeFunction extends AbstractFunction {

    NumberOfJudgmentsOfSpecifiedJudgeFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        if(args.length < 1) throw tooFewArguments;
        else if(args.length > 1) return numberOfJudgmentsOfSpecifiedJudge(args[0], args[1]);
        else return numberOfJudgmentsOfSpecifiedJudge(args[0]);
    }

    @Override
    String getHelpMessage() {
        return name + " imie nazwisko -- liczba orzeczeń wybranego sędziego";
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
