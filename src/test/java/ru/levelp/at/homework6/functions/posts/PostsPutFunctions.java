package ru.levelp.at.homework6.functions.posts;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.PostModel;

public class PostsPutFunctions extends CommonGoRestFunctions {
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

    public void checkPutOK() {
        //Генерируем новые данные поста
        var postNew = generatePost(post.getUserId());

        //Вызов запроса
        PostModel responsePost = given()
            .body(postNew.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(POSTS_ENDPOINT + "/" + post.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(PostModel.class);

        assertThat(responsePost)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(postNew);

        assertThat(responsePost.getId())
            .as("Сравнение id пост")
            .isEqualTo(post.getId());
    }

    public void checkPutUnauthorized() {
        //Получаем случайный пост
        var postNew = getRandomPost();

        //Вызов запроса
        given()
            .body(post.toString())
            .when()
            .put(POSTS_ENDPOINT + "/" + postNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPutNotFound() {
        //Вызов запроса
        given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(POSTS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }

    public void checkPutUnprocessableEntityUser() {
        //Изменяем значение user_id на не существующий
        post.setUserId(0);

        //Вызов запроса
        given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(POSTS_ENDPOINT + "/" + post.getId())
            .then()
            .statusCode(422)
            .body("message", hasItems("must exist"))
            .body("field", hasItems("user"));
    }
}
