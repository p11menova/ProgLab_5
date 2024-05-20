package org.server.commands.serverCommands;

import org.example.interaction.Response;
import org.example.models.DBModels.UserData;
import org.server.commands.Command;
import org.server.utility.managers.DBInteraction.DBCommands;
import org.server.utility.managers.DBInteraction.DBManager;

import java.util.concurrent.Callable;

public abstract class AuthorizationCommand implements Command {
    protected String commandName;
    protected DBManager dbManager;
    public AuthorizationCommand(String commandName, DBManager dbManager) {
        this.commandName = commandName;
        this.dbManager = dbManager;

    }
    public String getCommandName(){
        return this.commandName;
    }
    public abstract Response execute(UserData userData);
}
