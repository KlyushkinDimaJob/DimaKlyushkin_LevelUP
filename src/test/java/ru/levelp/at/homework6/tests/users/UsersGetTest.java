package ru.levelp.at.homework6.tests.users;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.users.UsersGetFunctions;

public class UsersGetTest extends UsersGetFunctions {
    @Test(description = "Проверка успешного получения пользователя по Id",
          groups = {"hw6", "users"})
    public void testGetOk() {
        checkGetUserByUserIdOK();
    }

    @Test(description = "Проверка ошибки получения не существующего пользователя",
          groups = {"hw6", "users"})
    public void testGetNotFound() {
        checkGetUserByUserIdNotFound();
    }
}
