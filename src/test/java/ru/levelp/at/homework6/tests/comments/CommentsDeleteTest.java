package ru.levelp.at.homework6.tests.comments;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.comments.CommentsDeleteFunctions;

public class CommentsDeleteTest extends CommentsDeleteFunctions {
    @Test(description = "Проверка успешного удаления комментария",
          groups = {"hw6", "comments"})
    public void testDeleteNoContent() {
        checkDeleteNoContent();
    }

    @Test(description = "Проверка ошибки авторизации при удалении комментария",
          groups = {"hw6", "comments"})
    public void testDeleteUnauthorized() {
        checkDeleteUnauthorized();
    }

    @Test(description = "Проверка ошибки удалении не существующего комментария",
          groups = {"hw6", "comments"})
    public void testDeleteNotFound() {
        checkDeleteNotFound();
    }
}
