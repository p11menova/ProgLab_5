package org.server.utility;

import org.example.models.Ticket;
import org.example.interaction.ResponseStatus;
import org.server.App;
import org.example.interaction.Request;
import org.example.interaction.Response;
import org.server.commands.serverCommands.SaveCommand;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Character.toChars;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Server {
    public final int port;
    private ServerSocket serverSocket;
    public Outputer outputer;
    public RunManager runManager;

    public Server(int port, RunManager runManager) {
        this.port = port;
        this.outputer = new Outputer();
        this.runManager = runManager;
    }

    public void openServerSocket() throws IOException {
        try {
            App.logger.info("прослушивает порт:" + this.port);
            serverSocket = new ServerSocket(this.port);
            App.logger.info("ServerSocket opened");
            outputer.println("ServerSocket открыт");
        } catch (IOException e) {
            throw new IOException("произошла ошибка при подключении по данному порту(");
        }
    }

    public static String filterInputString(String data) {
        char[] inCharArray = data.toCharArray();
        int SPACE_CODE = (int) ' ';
        int CURLY_BRACE = (int) '}';
        int RUS_A_CODE = (int) 'А';
        int RUS_YA_CODE = (int) 'я';
        StringBuilder res = new StringBuilder();
        data.chars().filter(x -> (SPACE_CODE <= x && x <= CURLY_BRACE || RUS_A_CODE <= x && x <= RUS_YA_CODE))
                .forEach(x -> res.append(toChars(x)[0]));
        return data;
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

    public boolean processClientRequest(Socket clientSocket) throws IOException, ClassNotFoundException {
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            Request request = null;
            Response response = null;
            while (response == null || response.getResponseStatus() != ResponseStatus.EXIT) {
                if (response != null && response.getResponseStatus() == ResponseStatus.OBJECT) {
                    Ticket newElem = (Ticket) clientReader.readObject();
                    response = CommandManager.go(new Request(request.getCommandName(), newElem));
                }
                else {
                    request = (Request) clientReader.readObject();
                    response = CommandManager.go(request);
                }
                clientWriter.writeObject(response);
                clientWriter.flush();
            }
            return true;
        } catch (IOException e) {
            App.logger.severe("ошибка при создании потоков ввода-вывода");
        } catch (ClassNotFoundException e) {
            App.logger.severe("ошибка при десериализации. некорректный тип данных(");
        }
        return false;

    }

    public void stop() {
        try {
            if (serverSocket == null) throw new RuntimeException();
            serverSocket.close();
        } catch (IOException e) {
            outputer.print_error("ошибка при отключении сервера");
            App.logger.severe("ошибка при отключении сервера");
        } catch (RuntimeException e) {
            outputer.print_error("там и так нечего закрывать(");
            App.logger.severe("там и так нечего закрывать(");
        }
    }

    public void go() {
        try {
            openServerSocket();
            boolean proccessingStatus = true;
            while (proccessingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    proccessingStatus = processClientRequest(clientSocket);
                } catch (IOException | ClassNotFoundException ex) {
                    proccessingStatus = false;
                }
            }
        } catch (IOException e) {
            App.logger.severe(e.getMessage());
            outputer.print_error(e.getMessage());
        }
        stop();
    }
    public static void save(){
        SaveCommand saveCommand = new SaveCommand(RunManager.collectionManager, RunManager.fileManager);
        if (saveCommand.go()) App.logger.info("коллекция успешно сохранена в файл");
        else App.logger.severe("ошибка при сохранении коллекции в файл(");
    }
}
