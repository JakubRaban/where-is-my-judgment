package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.Utilities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCommand extends AbstractFunction {

    HelpCommand(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        Map<String, AbstractFunction> functions = new FunctionInvoker().getFunctionNames();
        List<String> help = functions.values().stream()
                .map(AbstractFunction::getHelpMessage)
                .collect(Collectors.toList());
        return new FunctionResult(null, Utilities.getDelimitedList(help, '\n'));
    }

    @Override
    String getHelpMessage() {
        return name + " -- pomoc";
    }

}
