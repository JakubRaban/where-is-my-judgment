package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String ... args) throws IOException {

        /*TerminalConnection connection = new TerminalConnection();
        read(connection, ReadlineBuilder.builder().enableHistory(false).build(), "[aesh@rules]$ ");
        connection.openBlocking();
        */

        JudgmentJSONParser parser = new JudgmentJSONParser();
        String path = System.getProperty("user.dir") + "\\judgments\\dummy";
        JudgmentDirectoryReader getter = new JudgmentDirectoryReader(path);
        String json = getter.getFilesContents().get(3);
        Judgment j = parser.parse(json).get(7);
        //System.out.println();
        System.out.println(j.getMetric());
        //System.out.println("r");

    }

    /*private static void read(TerminalConnection connection, Readline readline, String prompt) {
        readline.readline(connection, prompt, input -> {
            if(input != null && input.equals("exitt")) {
                connection.write("we're exiting\n");
                connection.close();
            }
            else {
                connection.write("=====> "+input+"\n");
                read(connection, readline, prompt);
            }
        });
    }*/

}
