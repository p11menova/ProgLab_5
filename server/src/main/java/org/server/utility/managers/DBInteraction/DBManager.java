package org.server.utility.managers.DBInteraction;

import org.example.models.Coordinates;
import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.DBModels.UserData;
import org.example.models.Person;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.server.models.Tickets2;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static org.server.utility.managers.DBInteraction.DBCommands.getUserLoginByID;

public class DBManager {
    private Connection connection;
    private final String host;
    private final String login;
    private final String password;
    public Statement st;
    public Tickets2 selectedTickets;
    public DBManager(String host, String login, String password){
        this.host = host;
        this.login = login;
        this.password = password;
        this.selectedTickets = new Tickets2();
    }
    public HashMap<String, String> users = new HashMap<>();
    public void connectToDB() throws SQLException{

        Properties auth = new Properties();
        String DB_URL = "jdbc:postgresql://localhost:5432/studs";
        auth.put("user", "ektrntrpim");
        auth.put("password", "yui890");

        this.connection = DriverManager.getConnection(DB_URL, auth);
        System.out.println("successfully connected to DB");

        this.st = connection.createStatement();


        createTables();


    }
    public void createTables() throws SQLException {


        st.execute(DBCommands.createTableUsers());
        System.out.println("successfully created table Users");
        st.execute(DBCommands.createTicketTypeEnum());
        System.out.println("made tickettype enum");
        st.execute(DBCommands.createTableTickets());
        System.out.println("successfully created table Tickets");



      //  insertIntoUsers(new UserData("kate", "kate"));
        Ticket ticket = new Ticket();
        ticket.set_id(0);
        ticket.set_name("name");
        Coordinates coords = new Coordinates();
        coords.set_coordX(1.0);
        coords.set_coordY(1);
        ticket.set_coordinates(coords);
        ticket.set_refundable(true);
        ticket.set_type(TicketType.VIP);
        Person p = new Person();
        p.setHeight(1.0);

        p.setBirthday(LocalDateTime.now());
        ticket.set_person(p);
        ticket.set_creationData(ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        TicketWithMetadata ticketWithMetadata = new TicketWithMetadata(ticket);
        ticketWithMetadata.setUserData(new UserData("kate", "kate"));
      //  insertIntoTickets(ticketWithMetadata);

        updateTicket(ticketWithMetadata);

        selectUsers();
        selectTickets();

    }
    public void insertIntoUsers(UserData userData) throws SQLException {
        st.execute(DBCommands.insertIntoUsers(userData));

    }
    public void insertIntoTickets(TicketWithMetadata ticket) throws SQLException{
        System.out.println("я добавляю в бд");
        ResultSet rs = st.executeQuery(DBCommands.getUserIDByLogin(ticket.userData.login));
        if (rs.next()){
            String user_id = rs.getString("id");
            System.out.println("его айд " +user_id);
            st.execute(DBCommands.insertIntoTickets(ticket, user_id));

        }

    }
    public static boolean loginUser(UserData userData, String password) {
        boolean res = Users.checkPassword(userData, password);
        System.out.println("dbmanager res" + res);
        return res;
    }
    public void selectUsers() throws SQLException{
        ResultSet rs = st.executeQuery(DBCommands.getUsers());
        Users.users.clear();
        while (rs.next()){
            UserData userData = new UserData(Integer.parseInt(rs.getString("id")),
                    rs.getString("login"),
                    rs.getString("hashed_password"),
                    rs.getString("salt"));
            System.out.println(userData);
            Users.register(userData);
        }
    }
    public void getUserByLogin(String login) throws SQLException{
        ResultSet rs = st.executeQuery(DBCommands.getUserIDByLogin(login));
        if (!rs.next()){
            throw new SQLException();
        }

    }
    public Tickets2 selectTickets() throws SQLException {
        ResultSet rs = st.executeQuery(DBCommands.getTickets());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next()){

            Ticket ticket = new Ticket();
            ticket.set_id(Integer.parseInt(rs.getString("id").trim()));
            ticket.set_name(rs.getString("name"));
            Coordinates coords = new Coordinates();
            coords.set_coords(rs.getString("coordinates"));
            ticket.set_coordinates(coords);
            String price = rs.getString("price");
            System.out.println(price);
            ticket.set_price(price == null ? null : Long.parseLong(price));
            LocalDateTime creation_date = LocalDateTime.parse(rs.getString("creation_date"), formatter);
            ZonedDateTime zdt = creation_date.atZone(ZoneId.of( "UTC"));
            ticket.set_creationData(zdt);
            ticket.set_refundable(Boolean.parseBoolean(rs.getString("is_refundable")));
            ticket.set_type(TicketType.valueOf(rs.getString("type")));
            Person p = new Person();
            p.setHeight(Double.parseDouble(rs.getString("person_height")));
            p.setBirthday(LocalDateTime.parse(rs.getString("person_birthday"), formatter));
            ticket.set_person(p);

            TicketWithMetadata ticketWithMetadata = new TicketWithMetadata(ticket);
            ticketWithMetadata.setUserData(new UserData());
            ticketWithMetadata.setUserId(Integer.parseInt(rs.getString("user_id")));
            selectedTickets.addToList(ticketWithMetadata);

        }
        return selectedTickets;
    }
    public void updateTicket(TicketWithMetadata ticket1) throws SQLException {
        st.execute(DBCommands.updateTicket(ticket1, String.valueOf(ticket1.userData.id)));
    }
    public void deleteById(int id) throws SQLException{
        st.execute(DBCommands.deleteFromTicketsById(String.valueOf(id)));
    }
    public void clearByUser(int id) throws SQLException{
        st.execute(DBCommands.clearByUser(id));
    }
}
