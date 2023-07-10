package ru.levelp.at.homework6.tests.posts;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.posts.PostsPostFunctions;
import ru.levelp.at.homework6.providers.PostsDataProvider;

public class PostsPostTest extends PostsPostFunctions {

    @Test(description = "Проверка успешного создания поста",
          groups = {"hw6", "posts"})
    public void testPostCreated() {
        checkPostCreated();
    }

    @Test(description = "Проверка ошибки авторизации при создании поста",
          groups = {"hw6", "posts"})
    public void testPostUnauthorized() {
        checkPostUnauthorized();
    }

    @Test(dataProvider = "postsPostWithoutFieldDataProvider", dataProviderClass = PostsDataProvider.class,
          description = "Проверка ошибки при создании поста без заполнения обязательных полей",
          groups = {"hw6", "posts"})
    public void testPostUnprocessableEntity(String testData) {
        checkPostUnprocessableEntity(testData);
    }

    @Test(description = "Проверка ошибки создании поста при указании id не существующего пользователя",
          groups = {"hw6", "posts"})
    public void testPostUnprocessableEntityUser() {
        checkPostUnprocessableEntityUser();
    }
}
