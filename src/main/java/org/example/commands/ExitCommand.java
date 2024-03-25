package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.Console;

/**
 * Команда завершения работы программы (без сохранения в файл).
 */
public class ExitCommand extends Command{
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public boolean go(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Console.println("завершение работы программы (без сохранения в файл) ");
            System.exit(0);
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
        return false;
    }


}
