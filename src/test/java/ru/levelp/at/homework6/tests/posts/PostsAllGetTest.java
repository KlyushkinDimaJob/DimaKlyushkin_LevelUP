package ru.levelp.at.homework6.tests.posts;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersPost;
import ru.levelp.at.homework6.functions.posts.PostsAllGetFunctions;
import ru.levelp.at.homework6.providers.PostsDataProvider;

public class PostsAllGetTest extends PostsAllGetFunctions {
    @Test(dataProvider = "postsGetAllParamDataProvider", dataProviderClass = PostsDataProvider.class,
          description = "Проверка успешного получения постов с различными параметрами",
          groups = {"hw6", "posts"})
    public void testGetParam(ParametersPost[] parameters) {
        checkGetAllPosts(parameters);
    }
}
