package pl.jakubraban.whereismyjudgement.input;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jakubraban.whereismyjudgement.Utilities;
import pl.jakubraban.whereismyjudgement.data.judge.Judge;
import pl.jakubraban.whereismyjudgement.data.judge.JudgeRole;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtCaseReference;
import pl.jakubraban.whereismyjudgement.data.judgment.CourtType;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.data.judgment.JudgmentType;
import pl.jakubraban.whereismyjudgement.data.other.Regulation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JudgmentFromHTMLCreator {

    private Document htmlDocument;

    private List<CourtCaseReference> signatures;
    private Calendar judgmentDate;
    private List<Judge> judges = new LinkedList<>();
    private String textContent;
    private List<Regulation> referencedRegulations = new LinkedList<>();
    private CourtType courtType;

    public JudgmentFromHTMLCreator(Path pathToHtml) throws IOException {
        File html = pathToHtml.toFile();
        htmlDocument = Jsoup.parse(html, "UTF-8");
        htmlDocument.select("br").append("\\");
    }

    public Judgment create() {
        Judgment created;
        try {
            setSignatures();
            setTextContent();
            setFields(getParameters());
            created = Judgment.class.getDeclaredConstructor().newInstance();
            setAccessible(Judgment.class.getDeclaredField("concernedCourtCases")).set(created, signatures);
            setAccessible(Judgment.class.getDeclaredField("judgmentDate")).set(created, judgmentDate);
            setAccessible(Judgment.class.getDeclaredField("judges")).set(created, judges);
            setAccessible(Judgment.class.getDeclaredField("textContent")).set(created, textContent);
            setAccessible(Judgment.class.getDeclaredField("referencedRegulations")).set(created, referencedRegulations);
            setAccessible(Judgment.class.getDeclaredField("courtType")).set(created, courtType);
            setAccessible(Judgment.class.getDeclaredField("judgmentType")).set(created, JudgmentType.SENTENCE);
        } catch (InstantiationException | NoSuchFieldException | SecurityException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
        return created;
    }

    private void setSignatures() {
        String signatureHeader = htmlDocument.getElementsByClass("war_header").first().text();
        String properSignature = signatureHeader.substring(0, signatureHeader.indexOf("-") - 1).trim();
        signatures = Collections.singletonList(new CourtCaseReference(properSignature));
    }

    private Map<String, String> getParameters() {
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

    private void setTextContent() {
        StringBuilder result = new StringBuilder();
        Element table = htmlDocument.select("table").get(3);
        Elements sentence = table.getElementsByClass("info-list-label-uzasadnienie");
        for(Element element : sentence) {
            result.append(element.text()).append(Utilities.newLine);
        }
        textContent = result.toString().replaceAll("\\\\", Utilities.newLine);
    }

    private void setFields(Map<String, String> parameters) {
        for(String parameterName : parameters.keySet()) {
            switch(parameterName) {
                case "Data orzeczenia":
                    judgmentDate = parseDate(parameters.get("Data orzeczenia"));
                    break;
                case "Sędziowie":
                    judges = parseJudges(parameters.get("Sędziowie"));
                    break;
                case "Powołane przepisy":
                    referencedRegulations = parseReferencedRegulations(parameters.get("Powołane przepisy"));
                    break;
                case "Sąd":
                    courtType = parseCourtType(parameters.get("Sąd"));
            }
        }
    }

    private CourtType parseCourtType(String court) {
        if(court.contains("Naczelny")) return CourtType.SUPREME_ADMINISTRATIVE;
        else return CourtType.REGIONAL_ADMINISTRATIVE;
    }

    private List<Regulation> parseReferencedRegulations(String courtCases) {
        String[] courtCasesStringArray = courtCases.split("\\\\");
        List<Regulation> toReturn = new LinkedList<>();
        for(String aCase : courtCasesStringArray) {
            aCase = aCase.trim();
            if(!aCase.contains("Dz.U.") && !aCase.contains("Dz. U.")) {
                if (!aCase.contains("w sprawie") && !aCase.contains("r. o") && !aCase.contains("r. -")) {
                    StringBuilder sb = new StringBuilder(aCase);
                    sb.insert(aCase.indexOf("r.") + 2, " - ");
                    aCase = sb.toString();
                }
                if(aCase.endsWith(".")) aCase = aCase.substring(0, aCase.length() - 1);
                if(aCase.contains("-tekst") || aCase.contains("- tekst")) {
                    aCase = aCase.substring(0, aCase.lastIndexOf("-")).trim();
                }
                toReturn.add(new Regulation(aCase));
            }
        }
        return toReturn;
    }

    private List<Judge> parseJudges(String judges) {
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
