package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

/**
 * Команда вывода информации о коллекции.
 */
public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public boolean go(String args) {
        try {
            if (!args.isEmpty()) throw new WrongAmountOfArgumentsException();
            Console.println(CollectionManager.getInfo());
            return true;
        }
        catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName());
        }
       return false;
    }
}
