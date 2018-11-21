package pl.jakubraban.whereismyjudgement;

import com.google.gson.annotations.SerializedName;

public class Regulation {

    private String journalTitle;
    private int journalYear;
    @SerializedName("journalNo")
    private int journalNumber;
    private int journalEntry;
    private String text;

}
