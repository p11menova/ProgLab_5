package org.server.commands.clientCommands;

import org.example.interaction.Request;
import org.example.models.DBModels.TicketWithMetadata;
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
 * Команда удаления экземпляров коллекции, превышающих заданный.
 */
public class RemoveIfGreaterCommand extends ChangingCollectionCommand{
    public RemoveIfGreaterCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("remove_greater {element}", " удалить все элементы из коллекции, превышающие заданный", collectionManager,dbManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try{
            String arg = request.getCommandStringArg().trim();
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int i = Integer.parseInt(arg);
            if (!this.collectionManager.isIdTaken(i)) throw new NoSuchElementException();
            Hashtable<Integer, TicketWithMetadata> ht = this.collectionManager.getTicketsCollection();
            Ticket cur = ht.get(i).getTicket();
            StringBuilder resBody = new StringBuilder();
            Set<Map.Entry<Integer, TicketWithMetadata>> entries = ht.entrySet();
            entries.stream()
                    .filter(entry -> cur.compareTo(entry.getValue().getTicket()) > 0)
                    .forEach(entry->{
                        if (checkIfCanModify(new Request(String.valueOf(entry.getKey()), request.getUserData()))){
                        collectionManager.removeWithId(entry.getKey());
                        resBody.append("элемент с айди "+ entry.getKey() +" удален");};
                    });

            if (resBody.isEmpty()) return new Response(ResponseStatus.OK, "все элементы оказались не больше заданного. коллекция осталась без изменений)");
            return new Response(ResponseStatus.OK, resBody.toString());
        }
        catch (WrongAmountOfArgumentsException e) {
           return new Response(ResponseStatus.ERROR, e.getMessage());
        }
        catch (NumberFormatException e){
            return new Response(ResponseStatus.ERROR, "айди элемента должен быть целым числом.");
        } catch (NoSuchElementException e) {
            return new Response(ResponseStatus.ERROR, "элемента с указанным айди не существует. использование: " +this.getCommandName());
        }
    }
}
