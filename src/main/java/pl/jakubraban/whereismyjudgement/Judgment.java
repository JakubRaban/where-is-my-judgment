package pl.jakubraban.whereismyjudgement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Judgment {

    private int caseID;
    private Calendar judgmentDate;
    private CourtType courtType;
    private List<String> courtCases;
    private JudgmentType judgmentType;
    private List<Judge> judges;
    private JudgmentSource source;
    private List<CourtReporter> courtReporters;
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
    private int divisionID;
    // Chambers?
    private List<DissentingOpinion> dissentingOpinions;

    public String getMetric() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sygnatura: ");
        for(String aCase : courtCases) {
            sb.append(aCase).append(" ");
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

}
