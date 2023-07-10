package ru.levelp.at.homework6.tests.users;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersUser;
import ru.levelp.at.homework6.functions.users.UsersAllGetFunctions;
import ru.levelp.at.homework6.providers.UsersDataProvider;

public class UsersAllGetTest extends UsersAllGetFunctions {
    @Test(dataProvider = "usersGetAllParamDataProvider", dataProviderClass = UsersDataProvider.class,
          description = "Проверка успешного получения пользователей с различными параметрами",
          groups = {"hw6", "users"})
    public void testGetParam(ParametersUser[] parameters) {
        checkGetAllUser(parameters);
    }
}
