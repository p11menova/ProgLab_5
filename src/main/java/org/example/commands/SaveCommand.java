package org.example.commands;

import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CollectionManager;
import org.example.utility.Console;
import org.example.utility.FileManager;

/**
 * Команда сохранения коллекции в файл.
 */
public class SaveCommand extends Command {
    public CollectionManager collectionManager;
    public FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public boolean go(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            return this.fileManager.writeCollectionToFile(this.collectionManager);

        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage());
        }
        return false;
    }
}
