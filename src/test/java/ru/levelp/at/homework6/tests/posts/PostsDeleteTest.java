package ru.levelp.at.homework6.tests.posts;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.posts.PostsDeleteFunctions;

public class PostsDeleteTest extends PostsDeleteFunctions {
    @Test(description = "Проверка успешного удаления поста",
          groups = {"hw6", "posts"})
    public void testDeleteNoContent() {
        checkDeleteNoContent();
    }

    @Test(description = "Проверка ошибки авторизации при удалении поста",
          groups = {"hw6", "posts"})
    public void testDeleteUnauthorized() {
        checkDeleteUnauthorized();
    }

    @Test(description = "Проверка ошибки удаления не существующего поста",
          groups = {"hw6", "posts"})
    public void testDeleteNotFound() {
        checkDeleteNotFound();
    }
}
