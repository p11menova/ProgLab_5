package org.example.commands;

import org.example.exceptions.NoSuchElementException;
import org.example.exceptions.CollectionIdIsTakenException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;
import org.example.utility.ModelsAskers.NewTicketAsker;

/**
 * Команда добавления нового элемента коллекции с заданным ключом.
 */
public class InsertCommand extends AbstractAddCommand {
    private Ticket new_ticket;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert {key} {element}", "добавить новый элемент с заданным ключом", collectionManager);
        this.new_ticket = new Ticket();
    }

    @Override
    public boolean go(String arg) {
        try {
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();

            int id = Integer.parseInt(arg.trim());

            if (this.collectionManager.isIdTaken(id)) throw new CollectionIdIsTakenException();

            Console.println("-создание нового экземпляра типа Ticket:");

            Ticket newElem = this.makeNewTicket(id);

            if (newElem != null) {
                this.collectionManager.addToCollection(newElem);
                Console.println("тоопчик! экземпляр класса Ticket успешно создан и добавлен в коллекцию!");
            }

            return true;
        } catch (WrongAmountOfArgumentsException | CollectionIdIsTakenException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName() + " ");
        } catch (NumberFormatException e) {
            Console.print_error("ключ элемента должен быть целым числом(");
        }

        return false;
    }
}
