package org.server.utility.managers.DBInteraction;

import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.DBModels.UserData;

import java.time.format.DateTimeFormatter;

public class DBCommands {
    public static String createTableUsers() {
        return "CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY," +
                "login TEXT UNIQUE NOT NULL," +
                "hashed_password TEXT NOT NULL," +
                "salt TEXT NOT NULL);";
    }
    public static String createTicketTypeEnum(){
        return "DROP TYPE TicketType; CREATE TYPE TicketType AS ENUM('VIP', 'CHEAP', 'USUAL', 'BUDGETARY');";
    }

    public static String createTableTickets() {
        return "CREATE TABLE IF NOT EXISTS Tickets (" +
                "id INTEGER UNIQUE PRIMARY KEY," +
                "user_id INTEGER NOT NULL REFERENCES Users(id) ," +
                "name TEXT NOT NULL," +
                "coordinates POINT NOT NULL," +
                "creation_date TIMESTAMP," +
                "price BIGINT," +
                "is_refundable BOOLEAN," +
                "type TicketType NOT NULL," +
                "person_height FLOAT," +
                "person_birthday TIMESTAMP);";
    }

    public static String insertIntoTickets(TicketWithMetadata ticketWithMetadata, String user_id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println(ticketWithMetadata.ticket.get_creationDate().toLocalDateTime().format(formatter));
        return String.format("INSERT INTO Tickets(id, user_id, name, coordinates, creation_date, price, is_refundable, type, person_height, person_birthday) " +
                "VALUES (%s, '%s','%s','%s',E'%s', %s, %s, '%s', %s,E'%s');",
                ticketWithMetadata.ticket.get_id(),
                user_id,
                ticketWithMetadata.ticket.get_name(),
                ticketWithMetadata.ticket.get_coodinates(),
                ticketWithMetadata.ticket.get_creationDate().toLocalDateTime().format(formatter),
                ticketWithMetadata.ticket.get_price(),
                ticketWithMetadata.ticket.get_refundable(),
                ticketWithMetadata.ticket.get_ticketType(),
                ticketWithMetadata.ticket.get_person().height,
                ticketWithMetadata.ticket.get_person().birthday.format(formatter));

    }

    public static String getUserLoginByID(String user_id) {
        return String.format("SELECT login from Users WHERE id='%s';", user_id);

    }
    public static String getUserIDByLogin(String login){
        return String.format("SELECT id from Users WHERE login='%s'", login);
    }
    public static String getTickets(){
        return "SELECT * FROM Tickets";
    }
    public static String getUsers(){
        return "SELECT * FROM Users";
    }
    public static String updateTicket(TicketWithMetadata ticketWithMetadata, String user_id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println(ticketWithMetadata.ticket.get_creationDate().toLocalDateTime().format(formatter));
        return String.format("UPDATE Tickets " +
                        "SET name='%s',coordinates='%s',creation_date=E'%s', price=%s, is_refundable=%s, type='%s', person_height=%s, person_birthday=E'%s'" +
                        "WHERE id=%s;",
                ticketWithMetadata.ticket.get_name(),
                ticketWithMetadata.ticket.get_coodinates(),
                ticketWithMetadata.ticket.get_creationDate().toLocalDateTime().format(formatter),
                ticketWithMetadata.ticket.get_price(),
                ticketWithMetadata.ticket.get_refundable(),
                ticketWithMetadata.ticket.get_ticketType(),
                ticketWithMetadata.ticket.get_person().height,
                ticketWithMetadata.ticket.get_person().birthday.format(formatter),
                ticketWithMetadata.ticket.get_id());

    }



    public static String insertIntoUsers(UserData userData) {
        return String.format("INSERT INTO Users(login, hashed_password, salt) " +
                        "VALUES('%s', E'%s', E'%s');",
                userData.login, userData.hashedPassword, userData.getSalt());
    }
    public static String deleteFromTicketsById(String id){
        return String.format("DELETE FROM Tickets WHERE id=%s", id);
    }
    public static String clearByUser(int id){
        return String.format("DELETE FROM Tickets WHERE user_id=%s", id);
    }
}