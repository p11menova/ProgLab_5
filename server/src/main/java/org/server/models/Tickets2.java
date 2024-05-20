package org.server.models;

import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.DBModels.UserData;
import org.example.models.Ticket;
import org.server.App;
import org.server.utility.ModelsValidators.NewTicketValidator;
import org.server.utility.managers.CollectionManager;
import org.server.utility.managers.DBInteraction.Users;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tickets2 {
    public static ArrayList<TicketWithMetadata> tickets = new ArrayList<>();

    public void addToList(TicketWithMetadata ticket){
        tickets.add(ticket);
    }
    public boolean addToCollectionIfOkay(CollectionManager collectionManager) {
    AtomicBoolean are_added = new AtomicBoolean(false);
    for (TicketWithMetadata ticketWithMetadata : tickets) {
        System.out.println(ticketWithMetadata.toString());
        if (new NewTicketValidator().validateTicket(collectionManager, ticketWithMetadata.ticket)) {
            UserData userData = Users.getById(ticketWithMetadata.userData.get_id());
            ticketWithMetadata.setUserData(userData);
            collectionManager.addToCollection(ticketWithMetadata);

            App.logger.info("в коллекцию был добавлен элемент с id="+ticketWithMetadata.getTicket().get_id());
            are_added.set(true);
        }
    }
    return are_added.get();
    }
}
