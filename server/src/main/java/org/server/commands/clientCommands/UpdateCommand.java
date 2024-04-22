package org.server.commands.clientCommands;

import org.example.models.Ticket;
import org.server.exceptions.NoSuchElementException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;


/**
 * Команда обновления элемента коллекции по заданному ключу.
 */
public class UpdateCommand extends AbstractAddCommand {
    private int idToBeUpdated;
    public UpdateCommand(CollectionManager collectionManager) {
        super("update {key} {element}", " обновить значение элемента коллекции, айди которого равен заданному", collectionManager);
    }
    @Override
    public Response execute(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());
            if (this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();
            this.idToBeUpdated = id;
            return new Response(ResponseStatus.OBJECT, ">создание нового экземпляра Ticket:");
        } catch (WrongAmountOfArgumentsException | NoSuchElementException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
    @Override
    public Response execute(Ticket newElem) {
        this.collectionManager.addToCollection(newElem);
        return new Response(ResponseStatus.OK, "тоопчик! элемент с айди=" + this.idToBeUpdated + " успешно обновлен!");
    }
}
