package pl.jakubraban.whereismyjudgement;

import java.util.List;

public class Judge {

    private String name;
    private String function;
    private List<JudgeRole> specialRoles;

    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" - ").append(function);
        if(specialRoles.size() > 0) sb.append(", ");
        var iterator = specialRoles.iterator();
        while(iterator.hasNext()) {
            sb.append(iterator.next());
            if(iterator.hasNext()) sb.append(", ");
        }
        return sb.toString();
    }

}
