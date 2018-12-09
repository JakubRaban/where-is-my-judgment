package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.functions.FunctionInvoker;
import pl.jakubraban.whereismyjudgement.input.CommandParser;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private CommandParser commandParser = new CommandParser();
    private FunctionInvoker invoker = new FunctionInvoker();
    private Scanner sc = new Scanner(System.in);

    public void initialize(String judgmentsPath) {
        try {
            FormattableFunctionResult fileLoadingResult =
                    new FormattableFunctionResult(invoker.invoke("setPath", judgmentsPath));
            System.out.println(fileLoadingResult.format());
        } catch(InvalidPathException ipe) {
            System.out.println("BŁĄD: Podana jako parametr ścieżka nie jest katalogiem");
        } catch(IOException ioe) {
            System.out.println("Błąd odczytu pliku");
        } finally {
            System.out.println();
        }
        readUserCommands();
    }

    private void readUserCommands() {
        while(true) {
            try {
                System.out.print("?> ");
                String command = sc.nextLine();
                FormattableFunctionResult result = new FormattableFunctionResult(commandParser.parse(command));
                String formattedResult = result.format();
                System.out.println(formattedResult);
            } catch (IOException ioe) {
                System.out.println("Błąd odczytu z pliku");
            } catch (IllegalArgumentException iae) {
                System.out.println("BŁĄD: Nieprawidłowy argument lub ilość argumentów");
            } catch(NoSuchElementException nsee) {
                System.out.println("BŁĄD: Złe polecenie");
            } finally {
                System.out.println();
            }
        }
    }

}
