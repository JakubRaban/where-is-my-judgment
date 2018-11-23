package pl.jakubraban.whereismyjudgement;

import java.io.IOException;

public class Main {

    public static void main(String ... args) throws IOException {

        JudgmentJSONParser parser = new JudgmentJSONParser();
        String path = System.getProperty("user.dir") + "\\judgments\\dummy";
        JudgmentDirectoryReader getter = new JudgmentDirectoryReader(path);
        String json = getter.getFilesContents().get(0);
        Judgment j = parser.parse(json).get(94);
        System.out.println("r");

    }

}
