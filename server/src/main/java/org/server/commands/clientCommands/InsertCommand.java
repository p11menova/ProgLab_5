package org.server.commands.clientCommands;

import org.example.models.Ticket;
import org.server.exceptions.CollectionIdIsTakenException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

/**
 * Команда добавления нового элемента коллекции с заданным ключом.
 */
public class InsertCommand extends AbstractAddCommand {
    private Ticket new_ticket;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert {key} {element}", "добавить новый элемент с заданным ключом", collectionManager);
        this.new_ticket = new Ticket();
    }
    @Override
    public Response execute(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());
            if (this.collectionManager.isIdTaken(id)) throw new CollectionIdIsTakenException();
            return new Response(ResponseStatus.OBJECT, ">создание нового экземпляра Ticket:");
        } catch (WrongAmountOfArgumentsException | CollectionIdIsTakenException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
    @Override
    public Response execute(Ticket newElem) {
        this.collectionManager.addToCollection(newElem);
        return new Response(ResponseStatus.OK, "тоопчик! экземпляр класса Ticket успешно создан и добавлен в коллекцию!");
    }
}

