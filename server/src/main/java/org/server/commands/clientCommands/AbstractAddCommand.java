package org.server.commands.clientCommands;

import org.example.interaction.Response;
import org.example.models.Ticket;
import org.server.utility.CollectionManager;

/**
 * Абстрактный класс команды добавления экземпляра.
 * Служит родителем, если в команде требуется запрос и/или валидация нового экземпляра класса Ticket.
 */
public abstract class AbstractAddCommand extends ChangingCollectionCommand {
    public AbstractAddCommand(String CommandName, String CommandDescription, CollectionManager collectionManager) {
        super(CommandName, CommandDescription, collectionManager);
    }
    public abstract Response execute(Ticket newTicket);

}
