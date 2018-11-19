package pl.jakubraban.whereismyjudgement;

import java.util.Calendar;
import java.util.List;

public class Judgement {

    private int caseID;
    private Calendar judgementDate;
    private CourtType courtType;
    private List<String> courtCases;
    private JudgementType judgementType;
    private List<Judge> judges;
    private JudgementSource source;
    private List<Person> courtReporters;
    private String decision;
    private String summary;
    private String textContent;
    private List<String> legalBases;
    private List<Regulation> referencedRegulations;
    private List<String> keywords;
    private List<Judgement> referencedCourtCases;
    private Calendar receiptDate;
    private CourtType meansOfAppeal;
    private String judgementResult;
    private List<Judge> dissentingOpinions;

}
