package pl.jakubraban.whereismyjudgement.data.judgment;

import com.google.gson.annotations.SerializedName;
import pl.jakubraban.whereismyjudgement.Utilities;
import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.other.DissentingOpinion;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Judgment {

    @SerializedName("id")
    private int caseId;
    private Calendar judgmentDate;
    private CourtType courtType;
    @SerializedName("courtCases")
    private List<CourtCaseReference> concernedCourtCases;
    private JudgmentType judgmentType;
    private List<Judge> judges;
    private JudgmentSource source;
    private List<String> courtReporters;
    private String decision;
    private String summary;
    private String textContent;
    private List<String> legalBases;
    private List<Regulation> referencedRegulations;
    private List<String> keywords;
    private List<CourtCaseReference> referencedCourtCases;
    private Calendar receiptDate;
    private String meansOfAppeal;
    private String judgmentResult;
    private List<String> lowerCourtJudgments;
    private PersonnelType personnelType;
    @SerializedName("division.id")
    private int divisionID;
    private List<Chamber> chambers;
    private List<DissentingOpinion> dissentingOpinions;

    public String getMetric() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sygnatura: ");
        for(CourtCaseReference aCase : concernedCourtCases) {
            sb.append(aCase.getCaseNumber()).append(" ");
        }
        sb.append("\n");
        sb.append("Data: ");
        sb.append(new SimpleDateFormat("dd.MM.yyyy").format(judgmentDate.getTime()));
        sb.append("\n");
        sb.append("Typ sądu: ");
        sb.append(courtType);
        sb.append("\n");
        sb.append("Lista sędziów: ").append("\n");
        for(Judge judge : judges) {
            sb.append(" -- ").append(judge).append("\n");
        }
        return sb.toString();
    }

    public String getReasons() {
        int index = textContent.toLowerCase().indexOf("uzasadnienie");
        if(index == -1) index = textContent.toLowerCase().indexOf("u z a s a d n i e n i e");
        String extractedReasons = textContent.substring(index);
        return Utilities.dropHTMLTags(extractedReasons);
    }

    public List<CourtCaseReference> getConcernedCourtCases() {
        return concernedCourtCases;
    }

    public Calendar getJudgmentDate() {
        return judgmentDate;
    }

    public List<Judge> getJudges() {
        return judges;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public List<Regulation> getReferencedRegulations() {
        return referencedRegulations;
    }

}
