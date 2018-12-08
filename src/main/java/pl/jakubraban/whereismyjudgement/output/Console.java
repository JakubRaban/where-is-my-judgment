package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.functions.FunctionResult;
import pl.jakubraban.whereismyjudgement.Functions;
import pl.jakubraban.whereismyjudgement.input.CommandParser;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabase;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabaseProvider;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class Console {

    private Functions functions = new Functions();
    private CommandParser commandParser = new CommandParser(functions);
    private FunctionResultFormatter formatter = new FunctionResultFormatter();
    private JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();
    private Scanner sc = new Scanner(System.in);

    public void initialize(String judgmentsPath) {
        try {
            FunctionResult fileLoadingResult = functions.getNewJudgments(judgmentsPath);
            System.out.println("Załadowano wyroków: " + fileLoadingResult.getResult());
        } catch(InvalidPathException ipe) {
            System.out.println("Podana jako parametr ścieżka nie jest katalogiem");
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
                FunctionResult result = commandParser.parse(command);
                String formattedResult = formatter.format(result);
                System.out.println(formattedResult);
            } catch (IOException ioe) {
                System.out.println("Błąd odczytu z pliku");
            } catch (IllegalArgumentException iae) {
                if(iae.getMessage().equals("Bad command")) System.out.println("Złe polecenie");
                else System.out.println("Nieprawidłowy argument lub ilość argumentów");
            } finally {
                System.out.println();
            }
        }
    }

}
