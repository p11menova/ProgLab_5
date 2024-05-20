package org.server.utility.managers.DBInteraction;

import org.example.models.DBModels.UserData;

import java.util.HashMap;
import java.util.Objects;

public class Users {
    public static HashMap<String, UserData> users = new HashMap<>();

    public synchronized static boolean isUserExists(String login) {
        return users.get(login) != null;
    }
    public synchronized static boolean checkPassword(UserData userData, String hashedPassword){
        UserData previousUser = users.get(userData.login);
        System.out.println(previousUser.getHashedPassword()+previousUser.getSalt()+" "+ hashedPassword+previousUser.getSalt());
        return Objects.equals(previousUser.getHashedPassword()+previousUser.getSalt(), hashedPassword+previousUser.getSalt());

    }
    public synchronized static boolean register(UserData user){
        if (!users.containsKey(user.login)) {
            users.put(user.login, user);
            return true;
           }
        return false;

    }
    public synchronized static UserData getById(int id){
        for (UserData userData:users.values()){
            if (userData.id == id) return userData;
        }
        return null;
    }

    public static String String() {
        StringBuilder sb = new StringBuilder();
        for (UserData user:users.values()){
            sb.append("id:"+user.get_id() +" login:"+user.login+";\n");
        }
        return sb.toString();
    }
}

