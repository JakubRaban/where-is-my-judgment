package pl.jakubraban.whereismyjudgement.functions;

import pl.jakubraban.whereismyjudgement.Utilities;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpCommand extends AbstractFunction {

    HelpCommand(String name) {
        super(name);
    }

    @Override
    FunctionResult invoke(String... args) {
        Map<String, AbstractFunction> functions = new FunctionInvoker().getFunctionNames();
        Stream<AbstractFunction> functionStream = functions.values().stream();
        if(args.length > 0) {
            try {
                return new FunctionResult(null, functionStream
                        .filter(function -> function.getName().toLowerCase().equals(args[0]))
                        .map(AbstractFunction::getHelpMessage)
                        .collect(Collectors.toList()).get(0));
            } catch (Exception e) {
                return new FunctionResult(null, Collections.singletonList(args[0]), "Function", "");
            }
        }
        else return new FunctionResult(null, Utilities.getDelimitedList(functionStream
                .map(AbstractFunction::getHelpMessage)
                .collect(Collectors.toList()), Utilities.newLine));
    }

    @Override
    String getHelpMessage() {
        return name + " -- pomoc";
    }

}
