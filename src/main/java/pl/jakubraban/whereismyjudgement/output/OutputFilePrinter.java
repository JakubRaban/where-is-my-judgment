package pl.jakubraban.whereismyjudgement.output;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class OutputFilePrinter {

    Path folderLocation;

    public OutputFilePrinter(String folderLocation) {
        Path folderPath = Path.of(folderLocation);
        if(!Files.isDirectory(folderPath)) throw new InvalidPathException(folderPath.toString(), "is not a directory");
        this.folderLocation = folderPath;

    }



}
