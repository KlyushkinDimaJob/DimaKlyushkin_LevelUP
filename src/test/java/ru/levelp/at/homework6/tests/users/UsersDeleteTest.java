package ru.levelp.at.homework6.tests.users;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.users.UsersDeleteFunctions;

public class UsersDeleteTest extends UsersDeleteFunctions {
    @Test(description = "Проверка успешного удаления пользователя",
          groups = {"hw6", "users"})
    public void testDeleteNoContent() {
        checkDeleteNoContent();
    }

    @Test(description = "Проверка ошибки авторизации при удалении пользователя",
          groups = {"hw6", "users"})
    public void testDeleteUnauthorized() {
        checkDeleteUnauthorized();
    }

    @Test(description = "Проверка ошибки удаления не существующего пользователя",
          groups = {"hw6", "users"})
    public void testDeleteNotFound() {
        checkDeleteNotFound();
    }
}
