package org.example.interaction;

import org.example.models.Ticket;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseStatus responseStatus;
    private String responseBody;
    public Response(ResponseStatus responseStatus, String responseBody){
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }
    public Response(ResponseStatus responseStatus){
        this.responseStatus = responseStatus;
        this.responseBody = null;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
