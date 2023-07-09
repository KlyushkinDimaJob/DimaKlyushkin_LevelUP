package ru.levelp.at.homework6.functions.posts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.PostModel;

public class PostsGetFunctions extends CommonGoRestFunctions {
    public void checkGetPostByPostIdOK() {
        //Получаем случайный пост
        var post = getRandomPost();

        //Вызов запроса
        var responsePostModel = given()
            .when()
            .get(POSTS_ENDPOINT + "/" + post.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(PostModel.class);

        Assert.assertEquals(responsePostModel, post, "Ответы не совпадают");
    }

    public void checkGetPostByPostIdNotFound() {
        //Вызов запроса
        given()
            .when()
            .get(POSTS_ENDPOINT + "/" + 0)
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
