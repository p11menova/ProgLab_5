package org.example.commands;

import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;
import org.example.utility.ModelsAskers.NewTicketAsker;

/**
 * Абстрактный класс команды добавления экземпляра.
 * Служит родителем, если в команде требуется запрос и/или валидация нового экземпляра класса Ticket.
 */
public abstract class AbstractAddCommand extends Command {
    protected CollectionManager collectionManager;

    public AbstractAddCommand(String CommandName, String CommandDescription, CollectionManager collectionManager) {
        super(CommandName, CommandDescription);
        this.collectionManager = collectionManager;
    }

    /**
     * Создание нового экземпляра Ticket
     * @param id id нового элемента
     * @return новый экземпляр Ticket
     */

    public Ticket makeNewTicket(int id) {
        NewTicketAsker newTicketAsker = new NewTicketAsker();
        if (!Console.notPrinting){return newTicketAsker.validateTicketFromInteractiveMode(id);}
        return newTicketAsker.validateTicketFromScript(id);
    }
}
