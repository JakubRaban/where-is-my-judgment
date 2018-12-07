package pl.jakubraban.whereismyjudgement;

import pl.jakubraban.whereismyjudgement.output.Console;

public class Main {

    public static void main(String ... args) {

        try {
            System.out.println("Ładowanie wyroków . . .");
            new Console().initialize(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("BŁĄD: Nie podano ścieżki dostępu do plików z wyrokami");
        }
    }

}
