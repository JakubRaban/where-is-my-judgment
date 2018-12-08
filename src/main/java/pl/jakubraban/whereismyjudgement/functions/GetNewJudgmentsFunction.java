package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.input.JudgmentDirectoryReader;
import pl.jakubraban.whereismyjudgement.input.JudgmentJSONParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class GetNewJudgmentsFunction extends AbstractFunction {

    @Override
    FunctionResult invoke(String... args) throws IOException {
        if(args.length < 1) throw tooFewArguments;
        return getNewJudgments(args[0]);
    }

    private FunctionResult getNewJudgments(String path) throws IOException {
        JudgmentDirectoryReader reader = new JudgmentDirectoryReader(path);
        JudgmentJSONParser parser = new JudgmentJSONParser();
        List<String> allJsons = reader.getFilesContents();
        int newJudgmentsCounter = 0;
        for(String json : allJsons) {
            try {
                List<Judgment> judgments = parser.parse(json);
                database.add(judgments);
                newJudgmentsCounter += judgments.size();
            } catch (ParseException e) {
                System.out.println("Zły plik orzeczenia - nie dodano");
            }
        }
        return new FunctionResult(null, "Załadowano wyroków: " + newJudgmentsCounter);
    }

}
