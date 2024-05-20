package org.server.commands.clientCommands;

import org.example.interaction.Request;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.managers.CollectionManager;
import org.server.utility.managers.ConnectionHandler;
import org.server.utility.managers.DBInteraction.DBCommands;
import org.server.utility.managers.DBInteraction.DBManager;

import java.sql.SQLException;


/**
 * Команда очистки коллекции.
 */
public class ClearCommand extends ChangingCollectionCommand {
    public ClearCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("clear", "очистить коллекцию", collectionManager, dbManager);

    }

    @Override
    public Response execute(Request request) {
        try {
            String arg = request.getCommandStringArg();
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            try {
                this.dbManager.clearByUser(Integer.parseInt(request.getCommandStringArg()));
                this.collectionManager.clear();
                return new Response(ResponseStatus.OK, "элементы коллекции, добавленные этим юзером очищены.");
            } catch (SQLException e) {
                return new Response(ResponseStatus.ERROR, "коллекция не очищена");
            }

        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
    }
}
