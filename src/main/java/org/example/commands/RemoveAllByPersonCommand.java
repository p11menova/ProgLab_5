package org.example.commands;

import org.example.exceptions.NoSuchElementException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Person;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Команда удаления элементов коллекции, чье поле person эквивалентно заданному
 */
public class RemoveAllByPersonCommand extends Command{
    public CollectionManager collectionManager;
    public RemoveAllByPersonCommand(CollectionManager collectionManager) {
        super("remove_all_by_person {key}", " удалить из коллекции все элементы, значение поля person которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean go(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();

            Hashtable<Integer, Ticket> ht = this.collectionManager.getTicketsCollection();
            Person cur_person = this.collectionManager.getById(id).get_person();

            AtomicBoolean was_deleted = new AtomicBoolean(false);

            for (Map.Entry<Integer, Ticket> entry: ht.entrySet()) {
                if (cur_person.equals(entry.getValue().get_person()) && id != entry.getKey()) {
                    this.collectionManager.removeWithId(entry.getKey());
                    was_deleted.set(true);
                    Console.println("элемент с айди="+ entry.getKey() + " был удалён.");
                }
            }
            if (!was_deleted.get()) Console.println("элементов с эквивалентным полес Person не нашлось.. все осталось как есть!");
            return true;
        } catch (WrongAmountOfArgumentsException | NoSuchElementException e) {
            Console.print_error(e.getMessage());
        } catch (NumberFormatException e) {
            Console.print_error("поле айди должно быть целым числом.(");
        }
        return false;
    }
}
