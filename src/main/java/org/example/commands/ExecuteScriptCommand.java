package org.example.commands;

import org.example.utility.CommandManager;
import org.example.utility.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Команда исполнения скрипта
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");

    }

    @Override
    public boolean go(String arg) {
        try {
            File file = new File(arg.trim());
            if (!(file.canRead())){
                Console.print_error("отказано в правах доступа( чтение из файла невозможно.");
                return false;
            }
            Scanner in = new Scanner(file);

            Console.readScriptInput(in, file.getName());
            CommandManager.scriptNamesHistory.pop();
            return true;
        } catch (FileNotFoundException e) {
            Console.print_error("файл не найден..");
        }
        return false;
    }
}
