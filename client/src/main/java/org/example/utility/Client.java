package org.example.utility;

import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.example.exceptions.WrongFileRightException;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    public static int RECONNECTION_TIMEOUT = 2 * 1000;
    public int reconnectionAttempts;
    public String host;
    public int port;
    public ObjectOutputStream serverWriter;
    public ObjectInputStream serverReader;
    private SocketChannel socketChannel;
    private Console console;

    public Client(String host, int port, Console console) {
        this.host = host;
        this.port = port;
        this.console = console;
        this.reconnectionAttempts = 0;
    }

    public boolean connectToServer() throws IOException {
        try {
            if (this.reconnectionAttempts > 0) console.println("переподключение к серверу...");
            //if (this.reconnectionAttempts > 0) System.out.println("-> переподключение к серверу... попытка №"+reconnectionAttempts);
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(this.host, this.port));
            System.out.println("подключение к серверу установлено!");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            System.out.println("обмен данными разрешен");
            reconnectionAttempts = 0;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void disconnectToServer() {
        try {
            socketChannel.socket().close();
            serverReader.close();
            serverWriter.close();
        } catch (IOException e) {
            System.out.println("произошла ошибка при отключении от сервера(\nпринудительное завершение работы...");
            System.exit(1);
        }
    }

    public boolean sendRequestToServer() throws IOException, ClassNotFoundException, ConnectException {
        try {
            Request request = null;
            Response response = null;
            while (response == null || response.getResponseStatus() != ResponseStatus.EXIT) {
                request = console.getRequest();
                serverWriter.writeObject(request);
                serverWriter.flush();
                response = (Response) serverReader.readObject();
                if (response.getResponseStatus() == ResponseStatus.ERROR)
                    console.print_error(response.getResponseBody());
                else console.println(response.getResponseBody());
                if (response.getResponseStatus() == ResponseStatus.SCRIPT) {
                    try {
                        console.setScriptMode(response.getResponseBody());
                    } catch (FileNotFoundException e) {
                        console.print_error("файл с таким названием не найден..");
                    } catch (WrongFileRightException e) {
                        console.print_error("файл не имеет прав на чтение");
                    } catch (RuntimeException e) {
                        console.print_error(e.getMessage());
                        disconnectToServer();
                        System.exit(1);
                    }
                }
                if (response.getResponseStatus() == ResponseStatus.STOP_SCRIPT){
                    console.print_error(response.getResponseBody());
                    console.println("ОБРАБОТКА СКРИПТА БУДЕТ ЗАВЕРШЕНА");
                    console.denyFileMode();
                }
                if (response.getResponseStatus() == ResponseStatus.OBJECT) {
                    serverWriter.writeObject(console.makeNewTicket(Integer.parseInt(request.getCommandStringArg().trim())));
                    serverWriter.flush();
                    response = (Response) serverReader.readObject();
                    console.println(response.getResponseBody());
                }
            }
        } catch (IOException e) {
            if (!this.socketChannel.socket().getKeepAlive())
                throw new ConnectException(); // упал на моменте соединения с сервером
            throw new IOException(); // упал на моменте создания ридера райтера
        }
        return false;
    }

    public void go() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    if (socketChannel == null) connectToServer();
                    processingStatus = sendRequestToServer();
                } catch (ConnectException e) {
                    reconnectionAttempts++;
                    System.out.println("разрыв соединения с сервером(");
                    //Console.print_error("ошибка подключения к серверу");
                    while (!connectToServer()) {
                        if (reconnectionAttempts > MAX_RECONNECTION_ATTEMPTS) {
                            console.print_error("превышен лимит попыток подключения.\ncервер временно не доступен.(..\nповторите попытку позже..");
                            break;
                        }
                        console.println("повторная попытка подключения через " + RECONNECTION_TIMEOUT / 1000 + "c");
                        //System.out.println("повторная попытка подключения через " + RECONNECTION_TIMEOUT / 1000 + "c");
                        try {
                            Thread.sleep(RECONNECTION_TIMEOUT);
                        } catch (InterruptedException ex) {
                            continue;
                        }
                        reconnectionAttempts++;
                    }
                } catch (IOException e) {
                    console.print_error("ошибка при передаче данных на сервер..");

                } catch (ClassNotFoundException e) {
                    console.print_error("ошибка при десериализации( такого типа данных нет(");
                }
                // break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        disconnectToServer();

    }
}
