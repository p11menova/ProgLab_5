package org.example.commands;

import org.example.exceptions.NoSuchElementException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

/**
 * Команда обновления элемента коллекции по заданному ключу.
 */
public class UpdateCommand extends AbstractAddCommand {
    public UpdateCommand(CollectionManager collectionManager) {
        super("update {key} {element}", " обновить значение элемента коллекции, айди которого равен заданному", collectionManager);
    }

    @Override
    public boolean go(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();

            int id = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();

            Console.println("обновление элемента с айди=" + id);
            Ticket newElem = this.makeNewTicket(id);
            if (!(newElem == null)) {
                this.collectionManager.addToCollection(newElem);
                Console.println("тоопчик! элемент с айди=" + id + " успешно обновлен!");
            }
            return true;
        } catch (WrongAmountOfArgumentsException | NoSuchElementException e) {
            Console.print_error(e.getMessage());
        } catch (NumberFormatException e) {
            Console.print_error("ключ элемента должен быть целым числом(");
        }
        return false;
    }
}
