package org.server.utility;

import org.server.App;

import org.server.utility.managers.ConnectionHandler;
import org.server.utility.managers.DBInteraction.Users;
import org.server.utility.managers.Outputer;
import org.server.utility.managers.RunManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Character.toChars;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Server {
    public final int port;
    public Outputer outputer;
    public RunManager runManager;
    public ExecutorService fixedThreadPool;
    private ServerSocket serverSocket;

    public Server(int port, int maxClients, RunManager runManager) {

        this.port = port;
        this.outputer = new Outputer();
        this.runManager = runManager;
        this.fixedThreadPool = Executors.newFixedThreadPool(15);
    }

    public void openServerSocket() throws IOException {
        try {
            App.logger.info("прослушивает порт:" + this.port);
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(this.port));
            App.logger.info("ServerSocket opened");
            outputer.println("ServerSocket открыт");
        } catch (IOException e) {
            throw new IOException("произошла ошибка при подключении по данному порту(");
        }
    }


    public Socket connectToClient() throws IOException {
        try {
            outputer.println("Подключение к клиенту по данному сокету");
            Socket clientSocket = serverSocket.accept();
            outputer.println("Сервер успешно соединен с клиентом");
            App.logger.info("Сервер успешно соединен с клиентом порт=" + port);
            return clientSocket;
        } catch (IOException e) {
            throw new IOException("Ошибка соединения с клиентом( по данному сокету");
        }
    }

    public void go() {
        try{
            new Thread(()->{
                Scanner scanner = new Scanner(System.in);
                while(scanner.hasNextLine()){
                    if (scanner.nextLine().trim().equals("users")){
                        System.out.println("users, registered on server:{\n" + Users.String()+"}");
                    };
                }
            }).start();
            openServerSocket();
            while (true) {
                Socket clientSocket = connectToClient();
                try {
                    this.fixedThreadPool.submit(new ConnectionHandler(clientSocket)); //многопоточная обработка
                    // proccessingStatus = processClientRequest(clientSocket);
                } catch (Exception ex) {
                   break;
                }
            }

//        } catch (IOException e) {
//            App.logger.severe(e.getMessage());
//            outputer.print_error(e.getMessage());
//        }
//        stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static void save() {
//        SaveCommand saveCommand = new SaveCommand("save", RunManager.collectionManager, RunManager.fileManager);
//        if (saveCommand.go()) App.logger.info("коллекция успешно сохранена в файл");
//        else App.logger.severe("ошибка при сохранении коллекции в файл(");
//    }
}
