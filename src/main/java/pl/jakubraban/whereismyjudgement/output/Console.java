package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.functions.FunctionInvoker;
import pl.jakubraban.whereismyjudgement.input.CommandParser;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private CommandParser commandParser = new CommandParser();
    private FunctionInvoker invoker = new FunctionInvoker();
    private Scanner sc = new Scanner(System.in);
    private OutputFilePrinter filePrinter;

    public Console(String judgmentsPath) {
        try {
            System.out.println("Ładowanie wyroków z folderu " + judgmentsPath + " . . .");
            FormattableFunctionResult fileLoadingResult =
                    new FormattableFunctionResult(invoker.invoke("setPath", judgmentsPath));
            System.out.println(fileLoadingResult.format());
        } catch (InvalidPathException ipe) {
            System.out.println("BŁĄD: Podana jako parametr ścieżka nie jest katalogiem");
        } catch (IOException ioe) {
            System.out.println("Błąd odczytu pliku");
        } finally {
            System.out.println();
        }
    }

    public Console(String judgmentsPath, String outputFolderLocation) {
        this(judgmentsPath);
        try {
            System.out.println(outputFolderLocation);
            Path outputFolderPath = Path.of(outputFolderLocation);
            filePrinter = new OutputFilePrinter(outputFolderPath);
        } catch (InvalidPathException e) {
            System.out.println("Zła nazwa folderu dla plików wyjściowych");
        }
    }

    public void readUserCommands() throws IOException {
        String command = "";
        while (true) {
            try {
                System.out.print("?> ");
                command = sc.nextLine();
                FormattableFunctionResult result = new FormattableFunctionResult(commandParser.parse(command));
                String formattedResult = result.format();
                System.out.println(formattedResult);
                if (filePrinter != null) filePrinter.appendToFile(command, formattedResult);
            } catch (IOException ioe) {
                System.out.println("Błąd odczytu z pliku");
            } catch (IllegalArgumentException iae) {
                System.out.println("BŁĄD: Nieprawidłowy argument lub ilość argumentów");
                System.out.println("Prawidłowe użycie: " +
                        new FormattableFunctionResult(invoker.invoke("help", command)).format());
            } catch (NoSuchElementException nsee) {
                System.out.println("BŁĄD: Złe polecenie");
                System.out.println("Wpisz help aby uzyskać pomoc");
            } finally {
                System.out.println();
            }
        }
    }

}
