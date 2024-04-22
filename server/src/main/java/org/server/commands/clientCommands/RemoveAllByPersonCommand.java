package org.server.commands.clientCommands;

import org.example.models.Person;
import org.example.models.Ticket;
import org.server.exceptions.NoSuchElementException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Команда удаления элементов коллекции, чье поле person эквивалентно заданному
 */
public class RemoveAllByPersonCommand extends ChangingCollectionCommand{
    public RemoveAllByPersonCommand(CollectionManager collectionManager) {
        super("remove_all_by_person {key}", " удалить из коллекции все элементы, значение поля person которого эквивалентно заданному", collectionManager);

    }

    @Override
    public Response execute(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();

            Hashtable<Integer, Ticket> ht = this.collectionManager.getTicketsCollection();
            Person cur_person = this.collectionManager.getById(id).get_person();

            StringBuilder resBody = new StringBuilder();
            Set<Map.Entry<Integer, Ticket>> entries = ht.entrySet();
            entries.stream().filter(entry -> cur_person.equals(entry.getValue().get_person()) && id != entry.getKey())
                    .forEach(entry -> {
                        this.collectionManager.removeWithId(entry.getKey());
                        resBody.append("элемент с айди="+ entry.getKey() + " был удалён.\n");
                    });
            if (resBody.isEmpty()) return new Response(ResponseStatus.OK,"элементов с эквивалентным полем Person не нашлось.. все осталось как есть!");

            return new Response(ResponseStatus.OK, resBody.toString());
        } catch (WrongAmountOfArgumentsException | NoSuchElementException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.ERROR, "поле айди должно быть целым числом.(");
        }
    }
}
