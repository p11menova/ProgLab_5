package org.server.commands.clientCommands;

import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

/**
 * Команда вывода информации о коллекции.
 */
public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public Response execute(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            return new Response(ResponseStatus.OK, CollectionManager.getInfo());
        }
        catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage() + " использование: " + this.getCommandName());
        }
    }

//    @Override
//    public Response execute(Request request) {
//        return null;
//    }
}
