package org.server.utility.managers;

import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.Ticket;
import org.server.App;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConnectionHandler implements Runnable {
    public Socket clientSocket;
    public ExecutorService fixedThreadPool;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.fixedThreadPool = Executors.newFixedThreadPool(15);
    }

    public void stop() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "зашел в коннекшион хэндлер");
        try (ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream())) {
            Request request = null;
            Response response = null;

            while (true)
                try {
                    if (request != null && response.getResponseStatus() == ResponseStatus.OBJECT) {
                        TicketWithMetadata newElem = (TicketWithMetadata) clientReader.readObject();
                        String previousCommandName = request.getCommandName();
                        request = new Request(previousCommandName, newElem);
                    } else {
                        request = (Request) clientReader.readObject();
                    }

                    response = fixedThreadPool.submit(new ProcessRequestHandler(request)).get();
                    Response finalResponse = response;
                    System.out.println(response.getResponseStatus());
                    new Thread(() -> {
                        try {
                            clientWriter.writeObject(finalResponse);
                            clientWriter.flush();
                        } catch (IOException e) {
                            App.logger.severe("ответ не был отправлен: " + e.getMessage());
                        }
                    }).start();
                    // if (response.getResponseStatus() == ResponseStatus.EXIT) stop();
                } catch (IOException e) {
                    App.logger.info("клиент по сокету" + clientSocket.getLocalPort() + "отключился");
                    break;
                }


        } catch (IOException | ClassNotFoundException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}