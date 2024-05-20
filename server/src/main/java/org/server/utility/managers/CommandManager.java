package org.server.utility.managers;

import org.example.interaction.UserAuthStatus;
import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.DBModels.UserData;
import org.example.models.Ticket;
import org.server.App;
import org.server.commands.Command;
import org.server.commands.clientCommands.AbstractAddCommand;
import org.server.commands.clientCommands.ChangingCollectionCommand;

import org.server.commands.clientCommands.UserCommand;
import org.server.commands.serverCommands.AuthorizationCommand;
import org.server.commands.serverCommands.ServerCommand;
import org.server.exceptions.NoSuchCommandException;
import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.Server;
import org.server.utility.managers.DBInteraction.DBCommands;
import org.server.utility.managers.DBInteraction.DBManager;

import java.sql.SQLException;
import java.util.*;

import static org.server.utility.managers.RunManager.dbManager;

/**
 * Класс управления пользовательскими командами
 */
public class CommandManager {
    /**
     * Словарь используемых команд
     */
    static Map<String, Command> commands = new LinkedHashMap<>();

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
    public static Map<String, Command> getCommandsMap() {
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
     * @return true, если исполнилась успешно; false, если во время выполнения возникла ошибка
     */
    public static Response isUserExists(UserData userData) {
        try {
            System.out.println("я проверяю есть ли такой юзер");
            dbManager.getUserByLogin(userData.login);
            System.out.println("есть) такой юзер");
            return new Response(UserAuthStatus.OK);
        } catch (SQLException e) {
            App.logger.severe("пользователя " + userData.login + " не существует.");
            App.logger.info("перегружаю данные о Users");
            try {
                dbManager.selectUsers();
            } catch (SQLException ex) {
                App.logger.warning("данные о Users не были обновлены.");
            }
            return new Response(UserAuthStatus.NO_SUCH_LOGIN, "данного пользователя не существует( повторите попытку авторизации.");

        }
    }

    public static Response go(Request request) {
        try {
            String CommandName = request.getCommandName();
            String args = request.getCommandStringArg() == null ? "" : request.getCommandStringArg().trim();
            TicketWithMetadata objArg = request.getNewTicketModel();
            if (commands.get(CommandName) == null) throw new NoSuchCommandException();

            Response response;
            if ((commands.get(CommandName).getClass().getSuperclass() != AuthorizationCommand.class)) {
                UserData userData = null;
                if (objArg != null) {
                    userData = request.getNewTicketModel().userData;
                } else {
                    userData = request.getUserData();
                }
                Response isUserExists = isUserExists(userData);
                if (isUserExists.getResponseStatus() != UserAuthStatus.OK)
                    return new Response(UserAuthStatus.NO_SUCH_LOGIN, "пользователя с логином " + request.getUserData().login + " не существует(");
            }
            commandsHistory.add(CommandName);

            if (ChangingCollectionCommand.class.isAssignableFrom(((Command) commands.get(CommandName)).getClass())) {

                //Server.save();
            }
            if (commands.get(CommandName).getClass().getSuperclass() == AbstractAddCommand.class
                    && objArg != null) {
                response = ((AbstractAddCommand) commands.get(CommandName)).execute(request.getNewTicketModel());
            } else {
                if (commands.get(CommandName).getClass().getSuperclass() != AuthorizationCommand.class) {
                    response = ((UserCommand) commands.get(CommandName)).execute(request);
                } else {
                    response = ((AuthorizationCommand) commands.get(CommandName)).execute(request.getUserData());
                }
            }

            return response;
        } catch (NoSuchCommandException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }

    }
}
