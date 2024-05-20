package org.server.commands.serverCommands;

import org.example.interaction.Response;
import org.example.interaction.UserAuthStatus;
import org.example.models.DBModels.UserData;
import org.server.App;
import org.server.utility.managers.DBInteraction.DBCommands;
import org.server.utility.managers.DBInteraction.DBManager;
import org.server.utility.managers.DBInteraction.Users;

public class LoginUserCommand extends AuthorizationCommand {
    public LoginUserCommand(DBManager dbManager) {
        super("login", dbManager);
    }

    @Override
    public Response execute(UserData userData) {
        if (!Users.isUserExists(userData.login)) return new Response(UserAuthStatus.NO_SUCH_LOGIN, "пользователя с логином "+userData.login+" еще не существует(");
        boolean res = DBManager.loginUser(userData, userData.getHashedPassword());
        System.out.println("привчи из логин команды"+res);
        if (res) {
            App.logger.info("пользователь: " + userData.login + "успешно авторизовался");
            return new Response(UserAuthStatus.OK, "пользователь: " + userData.login + "успешно авторизовался");
        }
        return new Response(UserAuthStatus.WRONG_PASSWORD, "пароли не совпадают(\nповторите попытку авторизации");
    }
}
