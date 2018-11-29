package pl.jakubraban.whereismyjudgement.data.judge;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
        Iterator<JudgeRole> iterator = specialRoles.iterator();
        while(iterator.hasNext()) {
            JudgeRole currentRole = iterator.next();
            if(currentRole != null) {
                sb.append(currentRole);
                if(iterator.hasNext()) sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Judge)) return false;
        Judge judge = (Judge) o;
        return Objects.equals(name, judge.name) &&
                Objects.equals(function, judge.function) &&
                Objects.equals(specialRoles, judge.specialRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, function, specialRoles);
    }
}
