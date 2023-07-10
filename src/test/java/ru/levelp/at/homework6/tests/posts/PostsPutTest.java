package ru.levelp.at.homework6.tests.posts;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.posts.PostsPutFunctions;

public class PostsPutTest extends PostsPutFunctions {
    @Test(description = "Проверка успешного изменения данных поста",
          groups = {"hw6", "posts"})
    public void testPutOK() {
        checkPutOK();
    }

    @Test(description = "Проверка ошибки авторизации при изменении данных поста",
          groups = {"hw6", "posts"})
    public void testPutUnauthorized() {
        checkPutUnauthorized();
    }

    @Test(description = "Проверка ошибки изменения данных не существующего поста",
          groups = {"hw6", "posts"})
    public void testPutNotFound() {
        checkPutNotFound();
    }

    @Test(description = "Проверка ошибки изменения user_id у поста на не существующий",
          groups = {"hw6", "posts"})
    public void testPutUnprocessableEntityUser() {
        checkPutUnprocessableEntityUser();
    }
}
