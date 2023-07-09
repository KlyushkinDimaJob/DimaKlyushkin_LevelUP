package ru.levelp.at.homework6.tests.comments;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.comments.CommentsPostFunctions;
import ru.levelp.at.homework6.providers.CommentsDataProvider;

public class CommentsPostTest extends CommentsPostFunctions {

    @Test(description = "Проверка успешного создания комментария",
          groups = {"hw6", "comments"})
    public void testPostCreated() {
        checkPostCreated();
    }

    @Test(description = "Проверка ошибки авторизации при создании комментария",
          groups = {"hw6", "comments"})
    public void testPostUnauthorized() {
        checkPostUnauthorized();
    }

    @Test(dataProvider = "commentsPostWithoutFieldDataProvider", dataProviderClass = CommentsDataProvider.class,
          description = "Проверка ошибки при создании комментария без заполнения обязательных полей",
          groups = {"hw6", "comments"})
    public void testPostUnprocessableEntity(String testData) {
        checkPostUnprocessableEntity(testData);
    }

    @Test(description = "Проверка ошибки создании комментария при указании id не существующего поста",
          groups = {"hw6", "comments"})
    public void testPostUnprocessableEntityPost() {
        checkPostUnprocessableEntityPost();
    }
}
