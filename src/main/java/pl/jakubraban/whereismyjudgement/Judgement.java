package pl.jakubraban.whereismyjudgement;

import java.util.Calendar;
import java.util.List;

public class Judgement {

    private int caseID;
    private Calendar judgementDate;
    private CourtType courtType;
    private List<Judgement> courtCases;
    private JudgementType judgementType;
    private List<Judge> judges;
    private JudgementSource source;
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
    private String judgementResult;
    private List<String> lowerCourtJudgements;
    private PersonnelType personnelType;
    private int divisionID;
    // Chambers?
    private List<DissentingOpinion> dissentingOpinions;

}
