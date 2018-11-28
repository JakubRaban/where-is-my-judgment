package pl.jakubraban.whereismyjudgement.data.judge;

import java.util.List;

public class Judge {

    private String name;
    private String function;
    private List<JudgeRole> specialRoles;

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(function != null) sb.append(function).append(" ");
        sb.append(name);
        if(specialRoles.size() > 0) sb.append(" - ");
        var iterator = specialRoles.iterator();
        while(iterator.hasNext()) {
            JudgeRole currentRole = iterator.next();
            if(currentRole != null) {
                sb.append(currentRole);
                if(iterator.hasNext()) sb.append(", ");
            }
        }
        return sb.toString();
    }

}
