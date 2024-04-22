package org.server.commands.clientCommands;

import org.server.utility.CollectionManager;

public abstract class ChangingCollectionCommand extends Command{
    protected CollectionManager collectionManager;
    public ChangingCollectionCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }
}
