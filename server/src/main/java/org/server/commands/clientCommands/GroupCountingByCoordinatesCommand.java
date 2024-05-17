package org.server.commands.clientCommands;

import org.example.models.Coordinates;
import org.example.models.Ticket;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда группировки элементов коллекции по значению поля Coordinates
 */
public class GroupCountingByCoordinatesCommand extends Command{
    public CollectionManager collectionManager;
    public GroupCountingByCoordinatesCommand(CollectionManager collectionManager) {
        super("group_counting_by_coordinates", "сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Hashtable<Integer, Ticket> ht = this.collectionManager.getTicketsCollection();
            if (ht.isEmpty()) {
                return new Response(ResponseStatus.OK, "коллекция еще пуста.");
            }
            StringBuilder resBody = new StringBuilder();
            Map<Coordinates, List<Ticket>> hm = ht.values().stream().collect(Collectors.groupingBy(Ticket::get_coodinates));
            hm.forEach((key,value) -> resBody.append(key.toString() + ": количество элементов с такими коорд.:" + value.size()+"\n"));
            return new Response(ResponseStatus.OK, resBody.toString().trim());
        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
