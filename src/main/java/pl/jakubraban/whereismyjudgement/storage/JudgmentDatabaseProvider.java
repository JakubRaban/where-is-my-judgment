package pl.jakubraban.whereismyjudgement.storage;

public class JudgmentDatabaseProvider {

    private static JudgmentDatabase database;

    private JudgmentDatabaseProvider() { }

    public static JudgmentDatabase getDatabase() {
        if(database == null) database = new JudgmentDatabase();
        return database;
    }

}
