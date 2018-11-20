package pl.jakubraban.whereismyjudgement;

import java.util.List;

public class Judge {

    private String name;
    private String function;
    private List<JudgeRole> specialRoles;

    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" - ").append(function);
        for(JudgeRole judgeRole : specialRoles) {
            sb.append(judgeRole.toString()); // TODO przecinki i spacje
        }
        return sb.toString();
    }

}
