package pl.jakubraban.whereismyjudgement.data.judgment;

import com.google.gson.annotations.SerializedName;
import pl.jakubraban.whereismyjudgement.Utilities;
import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.other.DissentingOpinion;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabase;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabaseProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
        if(this.isReasons())
            sb.append("(Dla tego numeru sprawy załadowane pliki zawierają tylko uzasadnienie wyroku - dane mogą być niepełne)")
            .append("\n");
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
        if (this.getJudges().size() > 0) {
            sb.append("\n");
            sb.append("Lista sędziów: ").append("\n");
            for(Judge judge : judges) {
                sb.append(" -- ").append(judge).append("\n");
            }
        }
        return sb.toString();
    }

    public String getReasons() {
        int index = textContent.toLowerCase().indexOf("uzasadnienie");
        if(index == -1) index = textContent.toLowerCase().indexOf("u z a s a d n i e n i e");
        if(index == -1 && !this.isReasons()) {
            JudgmentDatabase database = JudgmentDatabaseProvider.getDatabase();
            for(CourtCaseReference reference : this.getConcernedCourtCases()) {
                Optional<Judgment> found = database.searchReasons(reference.getCaseNumber());
                if(found.isPresent()) return found.orElseThrow().getReasons();
            }
        }
        if(index == -1) return "(Brak uzasadnienia w wyroku) \n" + textContent;
        String extractedReasons = textContent.substring(index);
        return Utilities.dropHTMLTags(extractedReasons);
    }

    public JudgmentType getJudgmentType() {
        return judgmentType;
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

    public boolean isReasons() {
        return this.getJudgmentType().equals(JudgmentType.REASONS);
    }

    public List<Regulation> getReferencedRegulations() {
        return referencedRegulations;
    }

    public void mergeWithReasons(Judgment reasons) {
        if(!reasons.isReasons()) throw new IllegalArgumentException();
        this.textContent = this.textContent.concat("\n" + reasons.textContent);
    }
}
