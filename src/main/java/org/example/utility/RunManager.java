package org.example.utility;

import org.example.commands.*;

import java.util.Scanner;

/**
 * Менеджер запуска приложения
 */
public class RunManager {
    /**
     * Запускает работу приложения: проверка установки переменной окружения, регистрация команд, запуск чтения пользовательского ввода
     */
    public static void go() {
        String path = System.getenv("FILE_PATH");
        if (path == null || path.isBlank()){
            Console.print_error("нет переменной окружения. установите значение FILE_PATH командой\nexport FILE_PATH=<file_path>.\nзавершение работы программы(");
            System.exit(1);
        }

        FileManager fileManager = new FileManager(path);
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.loadCollectionFromFile(fileManager);

        CommandManager.registerCommand(new ExitCommand());
        CommandManager.registerCommand(new HelpCommand());
        CommandManager.registerCommand(new InfoCommand());
        CommandManager.registerCommand(new HistoryCommand());
        CommandManager.registerCommand(new InsertCommand(collectionManager));
        CommandManager.registerCommand(new ShowCommand(collectionManager));
        CommandManager.registerCommand(new UpdateCommand(collectionManager));
        CommandManager.registerCommand(new RemoveByKeyCommand(collectionManager));
        CommandManager.registerCommand(new ClearCommand(collectionManager));
        CommandManager.registerCommand(new RemoveIfGreaterCommand(collectionManager));
        CommandManager.registerCommand(new ReplaceIfGreaterCommand(collectionManager));
        CommandManager.registerCommand(new ReplaceIfLowerCommand(collectionManager));
        CommandManager.registerCommand(new RemoveAllByPersonCommand(collectionManager));
        CommandManager.registerCommand(new GroupCountingByCoordinatesCommand(collectionManager));
        CommandManager.registerCommand(new SaveCommand(collectionManager, fileManager));
        CommandManager.registerCommand(new ExecuteScriptCommand());


        Console.in = new Scanner(System.in);
        Console.readUserInput();
    }
}