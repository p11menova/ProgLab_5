package org.server.commands.serverCommands;

public abstract class Command {
    protected String CommandName;

    public Command(String commandName) {
        this.CommandName = commandName;
    }

    public abstract boolean go();
}
