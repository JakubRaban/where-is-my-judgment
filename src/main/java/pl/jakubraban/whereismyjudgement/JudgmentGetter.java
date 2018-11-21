package pl.jakubraban.whereismyjudgement;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JudgmentGetter {

    private Path pathToJudgments;

    public JudgmentGetter(String path) {
        pathToJudgments = Paths.get(path);
        if(!Files.isDirectory(pathToJudgments))
            throw new InvalidPathException(pathToJudgments.toString(), "is not a directory");
    }

    public ArrayList<String> getFilesContents() throws IOException {
        List<Path> pathList = Files.walk(pathToJudgments)
                .filter(path -> path.endsWith(".json"))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        return extractFromFiles(pathList);
    }

    private ArrayList<String> extractFromFiles(List<Path> paths) throws IOException {
        var contents = new ArrayList<String>();
        for(Path path : paths) {
            BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            contents.add(reader.readLine());
        }
        return contents;
    }

}
