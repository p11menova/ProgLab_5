package org.server.utility.managers;

import org.example.interaction.Request;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ProcessRequestHandler implements Callable<Response> {
    public Request request;

    public ProcessRequestHandler(Request request) {
        this.request = request;
    }

    @Override
    public Response call() {
        return CommandManager.go(request);
    }


}
