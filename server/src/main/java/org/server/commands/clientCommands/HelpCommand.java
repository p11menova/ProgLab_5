package org.server.commands.clientCommands;

import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CommandManager;

import java.util.Map;

/**
 * Команда получения справки по доступным командам.
 */
public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    @Override
    public Response execute(String arg) {
        StringBuilder resBody = new StringBuilder();
        if (!arg.isEmpty())
            return new Response(ResponseStatus.ERROR, "неверное количество аргументов команды(");
        Map<String, ? super Command> hm = CommandManager.getCommandsMap();
        hm.keySet().forEach(key -> resBody.append(hm.get(key)+"\n"));
        return new Response(ResponseStatus.OK, resBody.toString().trim());
    }
}
