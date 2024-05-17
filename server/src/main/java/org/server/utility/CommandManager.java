package org.server.utility;

import org.example.models.Ticket;
import org.server.commands.clientCommands.AbstractAddCommand;
import org.server.commands.clientCommands.ChangingCollectionCommand;
import org.server.commands.clientCommands.Command;
import org.server.commands.clientCommands.InsertCommand;
import org.server.commands.serverCommands.SaveCommand;
import org.server.exceptions.NoSuchCommandException;
import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;

import java.util.*;

/**
 * Класс управления пользовательскими командами
 */
public class CommandManager {
    /**
     * Словарь используемых команд
     */
    static Map<String, ? super Command> commands = new LinkedHashMap<>();

    /**
     * История последних пользовательских команд
     */
    public static final List<String> commandsHistory = new ArrayList<>();
    /**
     * Количество хранимых последних команд в истории
     */
    static int HISTORY_SIZE = 10;
    /**
     * История названий файлов исполняемых скриптов
     */
    public static final Stack<String> scriptNamesHistory = new Stack<>();

    /**
     * Регистрирует команду
     *
     * @param command добавляемая команда
     */
    public static void registerCommand(Command command) {
        commands.put(command.getCommandName(), command);
    }

    /**
     * Возвращает словарь доступных команд
     *
     * @return словарь доступных команд
     */
    public static Map<String, ? super Command> getCommandsMap() {
        return commands;
    }
//    public static ArrayList<? super Command> getCommandsAsArray(){
//        return (ArrayList<? super Command>) commands.keys();
//    }

    /**
     * Добавляет название исполненной команды в историю
     *
     * @param commandName название команды
     */
    public static void addToHistory(String commandName) {
        commandsHistory.add(commandName);

    }

    /**
     * Возвращает историю последних 10 исполненных команд
     *
     * @return история последних 10 исполненных команд
     */
    public static String getHistory() {
        StringBuilder history = new StringBuilder();
        if (!(commandsHistory.isEmpty())) {
            for (int i = Math.max(0, commandsHistory.size() - HISTORY_SIZE); i < commandsHistory.size(); i++) {
                if (!(commandsHistory.get(i) == null)) {
                    history.append("\n").append(commandsHistory.get(i));
                }
            }
        }
        return (history.toString().isEmpty()) ? "история еще пуста:( " : "последние команд пользователя (<= 10):" + history.toString();
    }

    /**
     * Исполняет команду
     *
     * @param request запрос клиента
     * @return true, если исполнилась успешно; false, если во время выполнения возникла ошибка
     */
    public static Response go(Request request) {
        try {
            String CommandName = request.getCommandName();
            String args = request.getCommandStringArg() == null ? "" : request.getCommandStringArg().trim();
            Ticket objArg = request.getNewTicketModel();
            if (commands.get(CommandName) == null) throw new NoSuchCommandException();

            Response response;
            commandsHistory.add(CommandName);

            if (commands.get(CommandName).getClass().getSuperclass() == AbstractAddCommand.class
                    && objArg != null) {
                response = ((AbstractAddCommand) commands.get(CommandName)).execute(objArg);
            } else {
                response = ((Command) commands.get(CommandName)).execute(args);
            }
            if (ChangingCollectionCommand.class.isAssignableFrom(((Command) commands.get(CommandName)).getClass()))
                Server.save();
            return response;
        } catch (NoSuchCommandException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

    }
}
