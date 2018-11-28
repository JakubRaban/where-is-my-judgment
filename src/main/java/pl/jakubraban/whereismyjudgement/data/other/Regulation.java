package pl.jakubraban.whereismyjudgement.data.other;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Regulation {

    private String journalTitle;
    private int journalYear;
    @SerializedName("journalNo")
    private int journalNumber;
    @SerializedName("journalEntry")
    private int journalEntryNumber;
    private String text;

    public int hashCode() {
        return Objects.hash(journalTitle, journalYear, journalEntryNumber, journalNumber);
    }

}