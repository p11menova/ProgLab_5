package org.server.commands.clientCommands;

import org.example.interaction.Request;
import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.Person;
import org.example.models.Ticket;
import org.server.exceptions.NoSuchElementException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.managers.CollectionManager;
import org.server.utility.managers.DBInteraction.DBManager;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Команда удаления элементов коллекции, чье поле person эквивалентно заданному
 */
public class RemoveAllByPersonCommand extends ChangingCollectionCommand{
    public RemoveAllByPersonCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("remove_all_by_person {key}", " удалить из коллекции все элементы, значение поля person которого эквивалентно заданному", collectionManager, dbManager);

    }

    @Override
    public Response execute(Request request) {
        try {
            String arg = request.getCommandStringArg();
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Integer id = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();

            Hashtable<Integer, TicketWithMetadata> ht = this.collectionManager.getTicketsCollection();
            Person cur_person = this.collectionManager.getById(id).getTicket().get_person();

            StringBuilder resBody = new StringBuilder();
            Set<Map.Entry<Integer, TicketWithMetadata>> entries = ht.entrySet();
            entries.stream().filter(entry -> cur_person.equals(entry.getValue().getTicket().get_person()) && id != entry.getKey())
                    .forEach(entry -> {
                        if (checkIfCanModify(new Request(String.valueOf(entry.getKey()), request.getUserData()))){
                        this.collectionManager.removeWithId(entry.getKey());
                        resBody.append("элемент с айди="+ entry.getKey() + " был удалён.\n");
                        }
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
