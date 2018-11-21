package pl.jakubraban.whereismyjudgement;

import java.io.IOException;

public class Main {

    public static void main(String ... args) throws IOException {

        JudgmentParser parser = new JudgmentParser();
        String path = System.getProperty("user.dir") + "\\judgments\\dummy";
        JudgmentDirectoryReader getter = new JudgmentDirectoryReader(path);
        String json = getter.getFilesContents().get(2);
        System.out.println(parser.parse(json).get(2).getMetric());

    }

}
