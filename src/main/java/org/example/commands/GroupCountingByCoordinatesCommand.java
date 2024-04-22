package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Coordinates;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

import java.util.HashMap;
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
    public boolean go(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Hashtable<Integer, Ticket> ht = this.collectionManager.getTicketsCollection();
            if (ht.isEmpty()) {
                Console.println("коллекция еще пуста.");
                return true;
            }
            Map<Coordinates, List<Ticket>> hm = ht.values().stream().collect(Collectors.groupingBy(Ticket::get_coodinates));
            hm.forEach((key,value) -> Console.println(key.toString() + ": количество элементов с такими коорд.:" + value.size()));
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage());
        }
        return false;
    }
}
