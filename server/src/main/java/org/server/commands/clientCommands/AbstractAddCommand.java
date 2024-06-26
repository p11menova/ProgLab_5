package org.server.commands.clientCommands;

import org.example.interaction.Response;
import org.example.models.Ticket;
import org.server.utility.CollectionManager;
import org.server.utility.ModelsValidators.NewTicketValidator;

/**
 * Абстрактный класс команды добавления экземпляра.
 * Служит родителем, если в команде требуется запрос и/или валидация нового экземпляра класса Ticket.
 */
public abstract class AbstractAddCommand extends ChangingCollectionCommand {
    NewTicketValidator ticketValidator;
    public AbstractAddCommand(String CommandName, String CommandDescription, CollectionManager collectionManager) {
        super(CommandName, CommandDescription, collectionManager);
        this.ticketValidator = new NewTicketValidator();
    }
    public abstract Response execute(Ticket newTicket);
    public boolean validate(Ticket newElem){
        return this.ticketValidator.validateTicket(collectionManager, newElem);
    }

}
