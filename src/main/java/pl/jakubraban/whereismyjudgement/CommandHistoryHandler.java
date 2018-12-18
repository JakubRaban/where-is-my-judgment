package pl.jakubraban.whereismyjudgement;

import java.util.LinkedList;
import java.util.List;

public class CommandHistoryHandler {

    private List<String> typedCommands = new LinkedList<>();
    private int index;

    public void add(String command) {
        typedCommands.add(command);
        index = typedCommands.size();
    }

    public String next() {
        if(typedCommands.isEmpty() || index == typedCommands.size()) return "";
        index++;
        if(index == typedCommands.size()) return "";
        else return typedCommands.get(index);
    }

    public String previous() {
        if(typedCommands.isEmpty() || index == -1) return "";
        index--;
        return index == -1 ? "" : typedCommands.get(index);
    }

}
