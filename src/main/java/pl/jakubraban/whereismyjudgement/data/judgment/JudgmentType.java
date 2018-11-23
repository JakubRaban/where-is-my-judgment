package pl.jakubraban.whereismyjudgement.data.judgment;

public enum JudgmentType {

    DECISION, RESOLUTION, SENTENCE, REGULATION, REASONS,
    NONE;

    public String toString() {
        switch(this) {
            case DECISION:
                return "Postanowienie";
            case REASONS:
                return "Uzasadnienie";
            case REGULATION:
                return "Zarządzenie";
            case RESOLUTION:
                return "Uchwała";
            case SENTENCE:
                return "Wyrok";
            default:
                return "Brak";
        }
    }

}
