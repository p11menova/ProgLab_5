package org.server.commands.clientCommands;

import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.commands.Command;
import org.server.commands.serverCommands.AuthorizationCommand;
import org.server.commands.serverCommands.ServerCommand;
import org.server.utility.managers.CommandManager;

import java.util.Map;

/**
 * Команда получения справки по доступным командам.
 */
public class HelpCommand extends UserCommand {
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    @Override
    public Response execute(Request request) {
        String arg = request.getCommandStringArg();
        StringBuilder resBody = new StringBuilder();
        if (!arg.isEmpty())
            return new Response(ResponseStatus.ERROR, "неверное количество аргументов команды(");
        Map<String, Command> hm = CommandManager.getCommandsMap();
        hm.values()
                .stream()
                .filter(c->(UserCommand.class.isAssignableFrom(c.getClass())))
                .forEach(v -> resBody.append(v).append("\n"));
        return new Response(ResponseStatus.OK, resBody.toString().trim());
    }
}
