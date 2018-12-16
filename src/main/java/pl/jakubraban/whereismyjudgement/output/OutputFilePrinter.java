package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OutputFilePrinter {

    private Path fileLocation;

    public OutputFilePrinter(Path folderPath) {
        try {
            if(!Files.exists(folderPath)) Files.createDirectory(folderPath);
            Calendar currentDate = Calendar.getInstance();
            String filename = "where-is-my-judgment-output-" + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(currentDate.getTime()) + ".txt";
            fileLocation = Path.of(folderPath.toString(), filename);
        } catch (IOException e) {
            System.out.println("Błąd przy otwieraniu tworzeniu pliku z wynikami programu");
        }
    }

    public void appendToFile(String input, String text) {
        StringBuilder sb = new StringBuilder("?> ");
        sb.append(input).append(Utilities.newLine).append(text).append(Utilities.newLine);
        try {
            if(!Files.exists(fileLocation)) Files.createFile(fileLocation);
            Files.write(fileLocation, sb.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku");
        }
    }

    public Path getFileLocation() {
        return fileLocation;
    }
}
