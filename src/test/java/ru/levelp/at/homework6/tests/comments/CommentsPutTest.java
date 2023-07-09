package ru.levelp.at.homework6.tests.comments;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.comments.CommentsPutFunctions;

public class CommentsPutTest extends CommentsPutFunctions {
    @Test(description = "Проверка успешного изменения данных комментария",
          groups = {"hw6", "comments"})
    public void testPutOK() {
        checkPutOK();
    }

    @Test(description = "Проверка ошибки авторизации при изменении данных комментария",
          groups = {"hw6", "comments"})
    public void testPutUnauthorized() {
        checkPutUnauthorized();
    }

    @Test(description = "Проверка ошибки изменения данных не существующего комментария",
          groups = {"hw6", "comments"})
    public void testPutNotFound() {
        checkPutNotFound();
    }

    @Test(description = "Проверка ошибки изменения post_id у комментария на не существующий",
          groups = {"hw6", "comments"})
    public void testPutUnprocessableEntityPost() {
        checkPutUnprocessableEntityPost();
    }
}
