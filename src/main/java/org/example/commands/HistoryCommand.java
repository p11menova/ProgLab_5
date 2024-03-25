package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CommandManager;
import org.example.utility.Console;
/**
 * Команда вывода текущей истории команд.
 */
public class HistoryCommand extends Command{


    public HistoryCommand() {
        super("history", "получить историю о последних <=10 выполненных командах");
    }

    @Override
    public boolean go(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Console.println(CommandManager.getHistory());
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
        return false;
    }
}
