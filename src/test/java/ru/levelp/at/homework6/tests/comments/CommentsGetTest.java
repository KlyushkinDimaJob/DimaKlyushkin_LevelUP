package ru.levelp.at.homework6.tests.comments;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.comments.CommentsGetFunctions;

public class CommentsGetTest extends CommentsGetFunctions {
    @Test(description = "Проверка успешного получения комментария",
          groups = {"hw6", "comments"})
    public void testGetOk() {
        checkGetByCommentIdOK();
    }

    @Test(description = "Проверка ошибки получения не существующего комментария",
          groups = {"hw6", "comments"})
    public void testGetNotFound() {
        checkGetByCommentIdNotFound();
    }
}
