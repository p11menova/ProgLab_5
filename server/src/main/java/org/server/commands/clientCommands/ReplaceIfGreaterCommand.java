package org.server.commands.clientCommands;

import org.example.models.Ticket;
import org.server.exceptions.NoSuchElementException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

/**
 * Команда замены экземпляра коллекции, на новый, если он больше старого.
 */
public class ReplaceIfGreaterCommand extends AbstractAddCommand {
    private int idToBeReplaced;
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        super("replace_if_greater {key} {element}",
                "заменить значение по ключу, если новое значение больше старого",
                collectionManager);
    }
    @Override
    public Response execute(Ticket newElem) {
        if (this.collectionManager.getById(this.idToBeReplaced).compareTo(newElem) < 0) {
            this.collectionManager.addToCollection(newElem);
            return new Response(ResponseStatus.OK, "введенный элемент больше предыдущего. произошла замена!");
        }
        return new Response(ResponseStatus.OK, "введенный элемент не больше предыдущего.) все осталось как было.");
    }

    @Override
    public Response execute(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();
            this.idToBeReplaced = id;
            return new Response(ResponseStatus.OBJECT, ">создание нового экземпляра Ticket, на который хотите заменить:");
        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        } catch (NumberFormatException | NoSuchElementException e) {
            return new Response(ResponseStatus.STOP_SCRIPT, "ключ элемента должен быть целым числом(");
        }
    }
}
