package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.WindowFrame;
import pl.jakubraban.whereismyjudgement.functions.FunctionInvoker;
import pl.jakubraban.whereismyjudgement.input.CommandParser;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class Console {

    private CommandParser commandParser = new CommandParser();
    private FunctionInvoker invoker = new FunctionInvoker();
    private OutputFilePrinter filePrinter;
    private WindowFrame frame;

    public Console(String judgmentsPath) {
        frame = new WindowFrame(this);
        greetUser();
        try {
            frame.printMessage("Ładowanie wyroków z folderu " + judgmentsPath + " . . .");
            FormattableFunctionResult fileLoadingResult =
                    new FormattableFunctionResult(invoker.invoke("setPath", judgmentsPath));
            frame.printMessage(fileLoadingResult.format());
        } catch (InvalidPathException ipe) {
            frame.printMessage("BŁĄD: Podana jako parametr ścieżka nie jest istniejącym katalogiem");
            System.exit(-1);
        } catch (IOException ioe) {
            frame.printMessage("Błąd odczytu pliku");
        } finally {
            frame.printMessage("");
        }
    }

    public Console(String judgmentsPath, String outputFolderLocation) {
        this(judgmentsPath);
        try {
            Path outputFolderPath = Path.of(outputFolderLocation);
            filePrinter = new OutputFilePrinter(outputFolderPath);
            frame.printMessage("Wynik działania programu będzie zapisywany do pliku " + filePrinter.getFileLocation() + "\n");
        } catch (InvalidPathException e) {
            frame.printMessage("Zła nazwa folderu dla plików wyjściowych" + "\n");
        }
    }

    private void greetUser() {
        frame.printMessage("\n" + "Where Is My Judgment -- program do prezentowania danych o wyrokach sądów");
        frame.printMessage("Autor: Jakub Raban, 2018");
        frame.printMessage("Aby uzyskać pomoc, wpisz help" + "\n");
    }

    public void execute(String typedCommand) {
        try {
            typedCommand = typedCommand.trim();
            FormattableFunctionResult result = new FormattableFunctionResult(commandParser.parse(typedCommand));
            String formattedResult = result.format();
            frame.printMessage(formattedResult);
            if (filePrinter != null) filePrinter.appendToFile(typedCommand, formattedResult);
        } catch (IOException ioe) {
            frame.printMessage("Błąd odczytu z pliku");
        } catch (IllegalArgumentException iae) {
            frame.printMessage("BŁĄD: Nieprawidłowy argument lub ilość argumentów");
            try {
                frame.printMessage("Prawidłowe użycie: " +
                        new FormattableFunctionResult(invoker.invoke("help", typedCommand)).format());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoSuchElementException nsee) {
            frame.printMessage("BŁĄD: Złe polecenie");
            frame.printMessage("Wpisz help aby uzyskać pomoc");
        } finally {
            frame.printMessage("");
        }
    }

    public WindowFrame getFrame() {
        return frame;
    }

}
