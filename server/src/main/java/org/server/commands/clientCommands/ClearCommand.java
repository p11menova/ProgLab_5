package org.server.commands.clientCommands;

import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;


/**
 * Команда очистки коллекции.
 */
public class ClearCommand extends Command{
    public CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg) {
        try{
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            this.collectionManager.clear();
            return new Response(ResponseStatus.OK, "коллекция очищена.");

        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
    }
}
