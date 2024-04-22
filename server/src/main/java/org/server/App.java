package org.server;



import org.example.utility.*;
import org.server.commands.clientCommands.AbstractAddCommand;
import org.server.commands.clientCommands.ChangingCollectionCommand;
import org.server.commands.clientCommands.InsertCommand;
import org.server.utility.RunManager;
import org.server.utility.Server;

import java.util.logging.Logger;

/**
 * Главный класс запуска программы.
 */
public class App {
    public static final Logger logger = Logger.getLogger(
            Server.class.getName());
    /**
     * Начинает выполнение менеджера запуска программы.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            if (port <= 0 || port >= 65536) throw new IllegalArgumentException("неверное значение порта");
            RunManager runManager = new RunManager();
            runManager.prepare();
            Server server = new Server(port, runManager);
            server.go();
        } catch (NumberFormatException e) {
            App.logger.severe("порт должен быть целым числом");
        } catch (IllegalArgumentException e){
            App.logger.severe(e.getMessage());
        }

    }
}


