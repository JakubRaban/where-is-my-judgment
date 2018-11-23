package pl.jakubraban.whereismyjudgement.data.judgment;

import com.google.gson.annotations.SerializedName;

public class JudgmentSource {

    @SerializedName("code")
    private CourtType sourceCourtType;
    private String judgementUrl;
    private int judgementId;
    @SerializedName("publisher")
    private String publisherName;
    @SerializedName("reviser")
    private String reviserName;
    private String publicationDate;

}
