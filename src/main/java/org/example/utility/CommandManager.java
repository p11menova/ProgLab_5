package org.example.utility;

import org.example.commands.AbstractAddCommand;
import org.example.commands.Command;
import org.example.commands.ExecuteScriptCommand;
import org.example.exceptions.NoSuchCommandException;

import java.util.*;

/**
 * Класс управления пользовательскими командами
 */
public class CommandManager {
    /**
     * Словарь используемых команд
     */
    static HashMap<String,? super Command> commands = new HashMap<>();

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
     * @param command добавляемая команда
     */
    public static void registerCommand(Command command){
        commands.put(command.getCommandName(), command);
    }

    /**
     * Возвращает словарь доступных команд
     * @return словарь доступных команд
     */
    public static HashMap<String, ? super Command> getCommands(){
        return commands;
    }

    /**
     * Добавляет название исполненной команды в историю
     * @param commandName название команды
     */
    public static void addToHistory(String commandName){
        commandsHistory.add(commandName);

    }

    /**
     * Возвращает историю последних 10 исполненных команд
     * @return история последних 10 исполненных команд
     */
    public static String getHistory(){
        StringBuilder history = new StringBuilder();
        if (!(commandsHistory.isEmpty())){
            for (int i = Math.max(0, commandsHistory.size() - HISTORY_SIZE); i < commandsHistory.size(); i++){
                if (!(commandsHistory.get(i)==null)){ history.append("\n").append(commandsHistory.get(i));}
            }
        }
        return  (history.toString().isEmpty())? "история еще пуста:( " : "последние команд пользователя (<= 10):" + history.toString();
    }

    /**
     * Исполняет команду
     * @param CommandName имя команды
     * @param args аргументы команды
     * @return true, если исполнилась успешно; false, если во время выполнения возникла ошибка
     */
    public static boolean go(String CommandName, String args) {
        try {
            if (commands.get(CommandName) == null) throw new NoSuchCommandException();
            if (commands.get(CommandName).getClass().getSuperclass() == AbstractAddCommand.class && (Console.in.equals(Console.script_in))){
                Console.notPrinting = true;
            }
            return ((Command) commands.get(CommandName)).go(args);
        } catch (NoSuchCommandException e) {
            Console.print_error(e.getMessage());
        }
        return false;
    }
}
