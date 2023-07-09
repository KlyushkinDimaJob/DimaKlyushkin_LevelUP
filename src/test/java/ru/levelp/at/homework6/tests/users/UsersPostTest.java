package ru.levelp.at.homework6.tests.users;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.users.UsersPostFunctions;
import ru.levelp.at.homework6.providers.UsersDataProvider;

public class UsersPostTest extends UsersPostFunctions {
    @Test(description = "Проверка успешного создания пользователя",
          groups = {"hw6", "users"})
    public void testPostUserCreated() {
        checkPostUserCreated();
    }

    @Test(description = "Проверка ошибки авторизации при создания пользователя",
          groups = {"hw6", "users"})
    public void testPostUnauthorized() {
        checkPostUnauthorized();
    }

    @Test(dataProvider = "usersPostWithoutFieldDataProvider", dataProviderClass = UsersDataProvider.class,
          description = "Проверка ошибки при создании пользователя без заполнения обязательных полей",
          groups = {"hw6", "users"})
    public void testPostUnprocessableEntity(String testData) {
        checkPostUnprocessableEntity(testData);
    }

    @Test(description = "Проверка ошибки создания пользователя с занятым email",
          groups = {"hw6", "users"})
    public void testPostUnprocessableEntityEmail() {
        checkPostUnprocessableEntityEmail();
    }
}
