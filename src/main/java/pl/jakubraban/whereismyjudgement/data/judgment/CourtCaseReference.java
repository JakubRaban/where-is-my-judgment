package pl.jakubraban.whereismyjudgement.data.judgment;

import java.util.List;

public class CourtCaseReference {

    private String caseNumber;
    private List<String> judgementIds;
    private boolean generated;

    public String getCaseNumber() {
        return caseNumber;
    }

}
