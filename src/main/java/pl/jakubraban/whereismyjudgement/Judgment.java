package pl.jakubraban.whereismyjudgement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Judgment {

    // TODO change String to Calendar in judgmentDate, receiptDate

    @SerializedName("id")
    private int caseId;
    private String judgmentDate;
    private CourtType courtType;
    private List<CourtCase> courtCases;
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
    private String receiptDate;
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
        for(CourtCase aCase : courtCases) {
            sb.append(aCase.getCaseNumber()).append(" ");
        }
        sb.append("\n");
        sb.append("Data: ");
        sb.append(judgmentDate);
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

}
