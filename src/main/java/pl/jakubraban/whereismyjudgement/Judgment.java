package pl.jakubraban.whereismyjudgement;

import java.util.Calendar;
import java.util.List;

public class Judgment {

    private int caseID;
    private Calendar judgmentDate;
    private CourtType courtType;
    private List<Judgment> courtCases;
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

}
