package org.example.models.DBModels;

import org.example.models.Ticket;

import java.io.Serializable;

public class TicketWithMetadata implements Serializable {
    public Ticket ticket;
    public UserData userData;
    public TicketWithMetadata(Ticket ticket){
        this.ticket = ticket;
    }
    public void setUserData(UserData userData){
        this.userData = userData;
    }
    public Ticket getTicket(){
        return this.ticket;
    }
    public void setUserId(int id){
        userData.id = id;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("author: " + userData.login+"\n");
        String ticket = getTicket().toString();
        return res.append(ticket).toString();
    }
}
