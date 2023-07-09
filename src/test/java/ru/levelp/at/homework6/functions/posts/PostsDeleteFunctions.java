package ru.levelp.at.homework6.functions.posts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.PostModel;

public class PostsDeleteFunctions extends CommonGoRestFunctions {
    private PostModel post;

    @BeforeMethod(groups = {"hw6", "posts"})
    public void beforeMethod() {
        //Получаем случайного пользователя
        var user = getRandomUser();

        //Генерируем пост
        post = generatePost(user.getId());

        //Вызов запроса
        post = given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(POSTS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(PostModel.class);
    }

    @AfterMethod(groups = {"hw6", "posts"})
    public void afterMethod() {
        if (post != null && post.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(POSTS_ENDPOINT + "/" + post.getId());
        }
    }

    public void checkDeleteNoContent() {
        //Вызов запроса
        given()
            .queryParam("access-token", TOKEN)
            .when()
            .delete(POSTS_ENDPOINT + "/" + post.getId())
            .then()
            .statusCode(204);
    }

    public void checkDeleteUnauthorized() {
        //Получаем случайный пост
        var postNew = getRandomPost();

        //Вызов запроса
        given()
            .when()
            .delete(POSTS_ENDPOINT + "/" + postNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkDeleteNotFound() {
        //Вызов запроса
        given()
            .when()
            .delete(POSTS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
