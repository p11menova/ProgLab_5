package org.example.commands;

import org.example.exceptions.NoSuchElementException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

/**
 * Команда замены экземпляра коллекции, на новый, если он больше старого.
 */
public class ReplaceIfGreaterCommand extends AbstractAddCommand {
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        super("replace_if_greater {key} {element}",
                "заменить значение по ключу, если новое значение больше старого",
                collectionManager);
    }

    @Override
    public boolean go(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int id = Integer.parseInt(arg.trim());

            if (!this.collectionManager.isIdTaken(id)) throw new NoSuchElementException();
            Console.println("-создание экземпляра Ticket, на который вы хотите заменить:");
            Ticket newElem = this.makeNewTicket(id);
            if (newElem != null) {
                if (newElem.compareTo(this.collectionManager.getById(id)) < 0) {
                    this.collectionManager.addToCollection(newElem);
                    Console.println("введенный элемент больше предыдущего. произошла замена!");
                } else Console.println("введенный элемент не больше предыдущего.) все осталось как было.");
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
