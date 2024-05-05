package org.server.utility;

import org.server.commands.clientCommands.*;
import org.server.commands.serverCommands.SaveCommand;

/**
 * Менеджер запуска приложения
 */
public class RunManager {
    /**
     * Запускает работу приложения: проверка установки переменной окружения, регистрация команд, запуск чтения пользовательского ввода
     */
    static CollectionManager collectionManager;
    static FileManager fileManager;

    public void prepare() {

        String path = System.getenv("FILE_PATH");
//        if (path == null || path.isBlank()){
//            Console.print_error("нет переменной окружения. установите значение FILE_PATH командой\nexport FILE_PATH=<file_path>.\nзавершение работы программы(");
//            System.exit(1);
//        }
        fileManager = new FileManager(path);
        collectionManager = new CollectionManager();
        collectionManager.loadCollectionFromFile(fileManager);

        CommandManager.registerCommand(new HelpCommand());
        CommandManager.registerCommand(new InfoCommand());
        CommandManager.registerCommand(new ShowCommand(collectionManager));
        CommandManager.registerCommand(new InsertCommand(collectionManager));
        CommandManager.registerCommand(new UpdateCommand(collectionManager));
        CommandManager.registerCommand(new RemoveByKeyCommand(collectionManager));
        CommandManager.registerCommand(new ClearCommand(collectionManager));
        // CommandManager.registerCommand(new SaveCommand(collectionManager, fileManager));
        CommandManager.registerCommand(new ExecuteScriptCommand());
        CommandManager.registerCommand(new HistoryCommand());
        CommandManager.registerCommand(new ExitCommand());
        CommandManager.registerCommand(new RemoveIfGreaterCommand(collectionManager));
        CommandManager.registerCommand(new ReplaceIfGreaterCommand(collectionManager));
        CommandManager.registerCommand(new ReplaceIfLowerCommand(collectionManager));
        CommandManager.registerCommand(new RemoveAllByPersonCommand(collectionManager));
        CommandManager.registerCommand(new GroupCountingByCoordinatesCommand(collectionManager));


        //    Console.in = new Scanner(System.in);
        //  Console.readUserInput();
    }
}