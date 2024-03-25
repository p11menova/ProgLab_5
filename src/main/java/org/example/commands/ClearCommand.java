package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

/**
 * Команда очистки коллекции.
 */
public class ClearCommand extends Command{
    public CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean go(String arg) {
        try{
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            this.collectionManager.clear();
            Console.println("коллекция очищена.");
            return true;

        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
        return false;
    }
}
