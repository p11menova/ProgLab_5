package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CollectionManager;
import org.example.utility.CommandManager;
import org.example.utility.Console;

/**
 * Команда вывода информации о коллекции в стандартный поток вывода.
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean go(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Console.println(this.collectionManager.toString());
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
        return false;
    }
}