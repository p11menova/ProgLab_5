package org.server.commands.serverCommands;

import org.server.utility.CollectionManager;
import org.server.utility.FileManager;

/**
 * Команда сохранения коллекции в файл.
 */
public class SaveCommand extends Command {
    public CollectionManager collectionManager;
    public FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        super("save");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public boolean go() {
        return this.fileManager.writeCollectionToFile(this.collectionManager);
    }
}
