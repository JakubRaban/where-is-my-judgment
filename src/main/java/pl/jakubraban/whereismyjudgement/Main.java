package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String ... args) throws IOException {

        JudgmentJSONParser parser = new JudgmentJSONParser();
        String path = System.getProperty("user.dir") + "\\judgments\\dummy";
        JudgmentDirectoryReader getter = new JudgmentDirectoryReader(path);
        String json = getter.getFilesContents().get(3);
        Judgment j = parser.parse(json).get(16);
        System.out.println();
        System.out.println(j.getMetric());
        System.out.println("r");

    }

}
