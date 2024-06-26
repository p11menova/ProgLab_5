package org.server.commands.clientCommands;

import org.example.interaction.Response;

/**
 * Абстрактный класс команды. Служит родителем для всех пользовательских команд.
 */
public abstract class Command {
    protected String CommandName;
    protected String CommandDescription;
    protected Response response;

    public Command(String name, String description){
        this.CommandName = name.split(" ",2)[0];
        this.CommandDescription = description;
    }

    /**
     * Исполняет команду
     * @return true, если команда выполнилась успешно, иначе false
     */

    //public abstract boolean go(String arg);
    public abstract Response execute(String arg);
    public String getCommandName(){
        return this.CommandName;
    }
    protected String getCommandDescription(){
        return this.CommandDescription;
    }
    @Override
    public String toString() {
        return  CommandName + " : " + CommandDescription;
    }
}

