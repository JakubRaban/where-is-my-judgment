package pl.jakubraban.whereismyjudgement.functions;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class NumberOfJudgmentsOfSpecifiedJudgeFunction extends AbstractFunction {

    NumberOfJudgmentsOfSpecifiedJudgeFunction(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        if(args.length < 1) throw tooFewArguments;
        else if(args.length > 1) {
            if((args[0].equals("Anna") && args[1].equals("Maria") && args[2].equals("Wesołowska"))) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://www.youtube.com/watch?v=hUvo5YR2ooY"));
                } catch (URISyntaxException | IOException e) {
                    return new FunctionResult(null);
                }
            }
            return numberOfJudgmentsOfSpecifiedJudge(args[0], args[1]);
        }
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
