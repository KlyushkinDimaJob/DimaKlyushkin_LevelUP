package ru.levelp.at.homework6.tests.posts;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.posts.PostsGetFunctions;

public class PostsGetTest extends PostsGetFunctions {
    @Test(description = "Проверка успешного получения поста",
          groups = {"hw6", "posts"})
    public void testGetOk() {
        checkGetPostByPostIdOK();
    }

    @Test(description = "Проверка ошибки получения не существующего поста",
          groups = {"hw6", "posts"})
    public void testGetNotFound() {
        checkGetPostByPostIdNotFound();
    }
}
