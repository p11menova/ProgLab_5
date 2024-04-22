package org.example.commands;

import org.example.utility.CommandManager;
import org.example.utility.Console;

import java.util.Map;

/**
 * Команда получения справки по доступным командам.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    @Override
    public boolean go(String arg) {
        Map<String, ? super Command> hm = CommandManager.getCommands();
        for (String key: hm.keySet()){
            Console.println(hm.get(key));
        }
        return true;
    }
}
