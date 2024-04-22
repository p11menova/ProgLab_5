package org.example.interaction;

import org.example.models.Ticket;


import java.io.Serializable;

public class Request implements Serializable {
    private String CommandName;
    private String CommandStringArg;
    private byte[] tb;
    private Ticket NewTicketModel; // при <? extends AbstractAddCommand>
    public Request(String cn,  Ticket newTick){
        this.CommandName = cn;
        this.NewTicketModel = newTick;
    }
    public Request(String cn, String ca){
        this.CommandName = cn;
        this.CommandStringArg = ca;
        this.NewTicketModel = null;
    }
    public Request(String cn, byte[] ca){
        this.CommandName = cn;
        this.tb = ca;
    }
    public Request(){
        new Request("", "");
    }

    public String getCommandName() {
        return CommandName;
    }
    public String getCommandStringArg(){
        return CommandStringArg;
    }
    public Ticket getNewTicketModel() {
        return NewTicketModel;
    }

}
