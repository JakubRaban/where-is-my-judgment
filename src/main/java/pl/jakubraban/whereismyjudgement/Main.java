package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.output.Console;

import java.io.IOException;

public class Main {

    public static void main(String ... args) {

        Console console;

        try {
            if (args.length > 1) console = new Console(args[0], args[1]);
            else console = new Console(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("BŁĄD: Nie podano ścieżki dostępu do plików z wyrokami");
        }
    }

}
