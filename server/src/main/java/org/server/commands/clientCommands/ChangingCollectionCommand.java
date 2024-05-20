package org.server.commands.clientCommands;

import org.example.interaction.Request;
import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.DBModels.UserData;
import org.server.App;
import org.server.utility.managers.CollectionManager;
import org.server.utility.managers.DBInteraction.DBManager;

import java.sql.SQLException;

public abstract class ChangingCollectionCommand extends UserCommand{
    protected CollectionManager collectionManager;
    protected DBManager dbManager;
    public ChangingCollectionCommand(String name, String description, CollectionManager collectionManager, DBManager dbManager) {
        super(name, description);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }
    public boolean checkIfCanModify(Request request){
        try{
            int ticket_id = Integer.parseInt(request.getCommandStringArg().trim());
            String login = request.getUserData().login;
            dbManager.getUserByLogin(login);
        return (collectionManager.getById(ticket_id).userData.login).equals(login);
        } catch (NumberFormatException | SQLException e){
            return false;
        }
    }
}
