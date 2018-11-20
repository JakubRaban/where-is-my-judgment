package pl.jakubraban.whereismyjudgement;

public enum PersonnelType {

    ONE_PERSON, THREE_PERSON, FIVE_PERSON, SEVEN_PERSON,
    ALL_COURT, ALL_CHAMBER, JOINED_CHAMBERS,
    NONE;

    public String toString() {
        switch (this) {
            case ONE_PERSON:
                return "Skład jednoosobowy";
            case ALL_COURT:
                return "Cały sąd";
            case ALL_CHAMBER:
                return "Cała izba";
            case FIVE_PERSON:
                return "Skład pięcioosobowy";
            case SEVEN_PERSON:
                return "Skład siedmioosobowy";
            case THREE_PERSON:
                return "Skład trzyosobowy";
            case JOINED_CHAMBERS:
                return "Połączone izby";
            default:
                return "Brak";
        }
    }

}
