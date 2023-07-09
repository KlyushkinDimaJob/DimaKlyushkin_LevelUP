package ru.levelp.at.homework6.tests.users;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.users.UsersPutFunctions;

public class UsersPutTest extends UsersPutFunctions {
    @Test(description = "Проверка успешного изменения данных пользователя",
          groups = {"hw6", "users"})
    public void testPutOK() {
        checkPutOK();
    }

    @Test(description = "Проверка ошибки авторизации при изменения данных пользователя",
          groups = {"hw6", "users"})
    public void testPutUnauthorized() {
        checkPutUnauthorized();
    }

    @Test(description = "Проверка ошибки изменения данных у не существующего пользователя",
          groups = {"hw6", "users"})
    public void testPutNotFound() {
        checkPutNotFound();
    }

    @Test(description = "Проверка ошибки изменения email у пользователя на уже занятый",
          groups = {"hw6", "users"})
    public void testPutUnprocessableEntityEmail() {
        checkPutUnprocessableEntityEmail();
    }
}
