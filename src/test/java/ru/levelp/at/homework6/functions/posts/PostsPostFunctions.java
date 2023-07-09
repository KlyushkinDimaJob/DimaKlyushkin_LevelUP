package ru.levelp.at.homework6.functions.posts;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.testng.annotations.AfterMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.ErrorModel;
import ru.levelp.at.homework6.model.response.PostModel;

public class PostsPostFunctions extends CommonGoRestFunctions {
    private PostModel post;
    private PostModel responsePost;

    @AfterMethod(groups = {"hw6", "posts"})
    public void afterMethod() {
        if (responsePost != null && responsePost.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(POSTS_ENDPOINT + "/" + responsePost.getId());
        }
    }

    public void checkPostCreated() {
        //Получаем случайного пользователя
        var user = getRandomUser();

        //Генерируем пост
        post = generatePost(user.getId());

        //Вызов запроса
        responsePost = given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(POSTS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(PostModel.class);

        assertThat(post)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(responsePost);
    }

    public void checkPostUnauthorized() {
        //Получаем случайного пользователя
        var user = getRandomUser();

        //Генерируем пост
        post = generatePost(user.getId());

        //Вызов запроса
        given()
            .body(post.toString())
            .when()
            .post(POSTS_ENDPOINT)
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPostUnprocessableEntity(String testData) {
        //Генерируем пост
        var gson = new Gson();
        post = gson.fromJson(testData, PostModel.class);
        var errorStr = JsonParser.parseString(testData).getAsJsonObject().getAsJsonArray("errorModel").toString();
        var error = gson.fromJson(errorStr, ErrorModel[].class);

        //Вызов запроса
        var responseError = given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(POSTS_ENDPOINT)
            .then()
            .statusCode(422)
            .extract()
            .as(ErrorModel[].class);

        assertThat(responseError)
            .as("Сравнение фактической ошибки с ожидаемой")
            .hasSize(error.length)
            .containsExactlyInAnyOrderElementsOf(Lists.newArrayList(error));
    }

    public void checkPostUnprocessableEntityUser() {
        //Генерируем пост
        post = generatePost(0);

        //Вызов запроса
        given()
            .body(post.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(POSTS_ENDPOINT)
            .then()
            .statusCode(422)
            .body("message", hasItems("must exist"))
            .body("field", hasItems("user"));
    }
}
