package pl.jakubraban.whereismyjudgement.data.judge;

import pl.jakubraban.whereismyjudgement.Utilities;

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
        sb.append(Utilities.getListWithCommas(specialRoles));
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
