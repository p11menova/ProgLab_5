package org.server.commands.serverCommands;

import org.example.interaction.Response;
import org.example.interaction.UserAuthStatus;
import org.example.models.DBModels.UserData;
import org.server.App;
import org.server.utility.managers.DBInteraction.DBManager;
import org.server.utility.managers.DBInteraction.Users;

import java.sql.SQLException;

public class RegisterUserCommand extends AuthorizationCommand {

    public RegisterUserCommand(DBManager dbManager) {
        super("register", dbManager);
    }

    @Override
    public Response execute(UserData userData) {
        if (!Users.register(userData)) return new Response(UserAuthStatus.ALREADY_EXISTS, "пользователь с логином: " + userData.login +" уже существует(" );
        try {
            this.dbManager.insertIntoUsers(userData);
        } catch (SQLException e) {
            return new Response(UserAuthStatus.ERROR, "не удалось добавить юзера в БД");
        }
        App.logger.info("пользователь: " + userData.login +" успешно зарегистрирован!");
        return new Response(UserAuthStatus.OK, "пользователь: " + userData.login +" успешно зарегистрирован!");
    }
}
