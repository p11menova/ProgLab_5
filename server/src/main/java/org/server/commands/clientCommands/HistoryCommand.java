package org.server.commands.clientCommands;

import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CommandManager;

/**
 * Команда вывода текущей истории команд.
 */
public class HistoryCommand extends Command{


    public HistoryCommand() {
        super("history", "получить историю о последних <=10 выполненных командах");
    }

    @Override
    public Response execute(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            return new Response(ResponseStatus.OK, CommandManager.getHistory());
        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
    }
}
