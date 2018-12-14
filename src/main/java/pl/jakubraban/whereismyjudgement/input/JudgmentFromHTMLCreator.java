package pl.jakubraban.whereismyjudgement.input;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judge.JudgeRole;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JudgmentFromHTMLCreator {

    private Document htmlDocument;

    private List<CourtCaseReference> signatures;
    private Calendar judgmentDate;
    private List<Judge> judges;
    private String textContent;
    private List<Regulation> referencedCourtCases;

    public JudgmentFromHTMLCreator(Path pathToHtml) throws IOException {
        File html = pathToHtml.toFile();
        htmlDocument = Jsoup.parse(html, "UTF-8");
        htmlDocument.select("br").append("\\");
    }

    public Judgment create() {
        return null;
    }

    public void setSignatures() {
        String signatureHeader = htmlDocument.getElementsByClass("war_header").first().text();
        String properSignature = signatureHeader.substring(0, signatureHeader.indexOf("-") - 1);
        signatures = Collections.singletonList(new CourtCaseReference(properSignature));
    }

    public Map<String, String> getParameters() {
        Map<String, String> judgmentContents = new HashMap<>();
        Element table = htmlDocument.select("table").get(3);
        Elements rows = table.select("tr");
        for (Element currentRow : rows) {
            Elements keys = currentRow.getElementsByClass("info-list-label");
            String key = null;
            for (Element key1 : keys) {
                Elements currentKey = key1.getElementsByClass("lista-label");
                key = currentKey.text();
            }
            Elements values = currentRow.getElementsByClass("info-list-value");
            Element currentValue = values.first();
            String value = null;
            if (currentValue != null) value = currentValue.text().trim().replaceAll("orzeczenie.*", "");
            if (value != null && key != null) judgmentContents.put(key, value);
        }
        return judgmentContents;
    }

    public void getAndSetTextContent(Path pathToHtml) throws IOException {
        StringBuilder result = new StringBuilder();
        File html = pathToHtml.toFile();
        Document document = Jsoup.parse(html, "UTF-8");
        Element table = document.select("table").get(3);
        Elements sentence = table.getElementsByClass("info-list-label-uzasadnienie");
        for(Element element : sentence) {
            result.append(element.text()).append("\n");
        }
        textContent = result.toString().replaceAll("\\\\", "\n");
    }

    public void setFields(Map<String, String> parameters) {
        for(String parameterName : parameters.keySet()) {
            switch(parameterName) {
                case "Data orzeczenia":
                    judgmentDate = parseDate(parameters.get("Data orzeczenia"));
                    break;
                case "Sędziowie":
                    judges = parseJudges(parameters.get("Sędziowie"));
                    break;
                case "Powołane przepisy":
                    referencedCourtCases = parseReferencedRegulations(parameters.get("Powołane przepisy"));
                    break;
            }
        }
    }

    public List<Regulation> parseReferencedRegulations(String courtCases) {
        String[] courtCasesStringArray = courtCases.split("\\\\");
        List<Regulation> toReturn = new LinkedList<>();
        for(String aCase : courtCasesStringArray) {
            aCase = aCase.trim();
            if(!aCase.contains("Dz.U.") && !aCase.contains("Dz. U.")) {
                if (!aCase.contains("w sprawie")) {
                    StringBuilder sb = new StringBuilder(aCase);
                    sb.insert(aCase.indexOf("r.") + 2, " - ");
                    aCase = sb.toString();
                }
                aCase = aCase.substring(0, aCase.length() - 1);
                toReturn.add(new Regulation(aCase));
            }
        }
        return toReturn;
    }

    public List<Judge> parseJudges(String judges) {
        List<Judge> toReturn = new LinkedList<>();
        String[] judgesArray = judges.split("\\\\");
        for(String oneJudge : judgesArray) {
            String name;
            List<JudgeRole> specialRoles = new LinkedList<>();
            oneJudge = oneJudge.trim().replaceAll(" - ", "-");
            if (oneJudge.contains("/")) {
                int firstSlash = oneJudge.indexOf("/");
                int lastSlash = oneJudge.lastIndexOf("/");
                String specialRolesString = oneJudge.substring(firstSlash + 1, lastSlash).toLowerCase();
                if(specialRolesString.contains("sprawozdawca")) specialRoles.add(JudgeRole.REPORTING_JUDGE);
                if(specialRolesString.contains("przewodniczący")) specialRoles.add(JudgeRole.PRESIDING_JUDGE);
                if(specialRolesString.contains("autor uzasadnienia")) specialRoles.add(JudgeRole.REASONS_FOR_JUDGMENT_AUTHOR);
                name = oneJudge.substring(0, firstSlash - 1);
                toReturn.add(new Judge(name, specialRoles.toArray( new JudgeRole[0] ) ));
            }
            else toReturn.add(new Judge(oneJudge));
        }
        return toReturn;
    }

    private Calendar parseDate(String judgmentDate) {
        Calendar toReturn = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            toReturn.setTime(sdf.parse(judgmentDate));
        } catch (ParseException e) {
            return null;
        }
        return toReturn;
    }

    private Field setAccessible(Field field) {
        field.setAccessible(true);
        return field;
    }

}
