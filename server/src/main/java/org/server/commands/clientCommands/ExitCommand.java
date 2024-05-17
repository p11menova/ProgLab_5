package org.server.commands.clientCommands;

import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;

/**
 * Команда завершения работы программы (без сохранения в файл).
 */
public class ExitCommand extends Command{
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public Response execute(String arg) {
        try {
            if (!arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            return new Response(ResponseStatus.EXIT, "ЗАВЕРШЕНИЕ РАБОТЫ КЛИЕНТА)");
        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage() + " использование: " + this.getCommandName() + " ");
        }
    }
}
