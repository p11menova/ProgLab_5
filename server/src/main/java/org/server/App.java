package org.server;



import org.server.utility.managers.RunManager;
import org.server.utility.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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

            Class.forName("org.postgresql.Driver");
            RunManager runManager = new RunManager(args[1], args[2], args[3]);
            runManager.prepare();

            Server server = new Server(port, 15, runManager);
            server.go();
        } catch (NumberFormatException e) {
            App.logger.severe("порт должен быть целым числом");

        } catch (IllegalArgumentException e) {
            App.logger.severe(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}


