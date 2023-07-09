package ru.levelp.at.homework6.functions.comments;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.testng.annotations.AfterMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.CommentModel;
import ru.levelp.at.homework6.model.response.ErrorModel;

public class CommentsPostFunctions extends CommonGoRestFunctions {
    private CommentModel comment;
    private CommentModel responseComment;

    @AfterMethod(groups = {"hw6", "comments"})
    public void afterMethod() {
        if (responseComment != null && responseComment.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(COMMENTS_ENDPOINT + "/" + responseComment.getId());
        }
    }

    public void checkPostCreated() {
        //Получаем случайный пост
        var post = getRandomPost();

        //Генерируем комментарий
        comment = generateComment(post.getId());

        //Вызов запроса
        responseComment = given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(COMMENTS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(CommentModel.class);

        assertThat(comment)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(responseComment);
    }

    public void checkPostUnauthorized() {
        //Получаем случайный пост
        var post = getRandomPost();

        //Генерируем комментарий
        comment = generateComment(post.getId());

        //Вызов запроса
        given()
            .body(comment.toString())
            .when()
            .post(COMMENTS_ENDPOINT)
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPostUnprocessableEntity(String testData) {
        //Генерируем комментарий
        var gson = new Gson();
        comment = gson.fromJson(testData, CommentModel.class);
        var errorStr = JsonParser.parseString(testData).getAsJsonObject().getAsJsonArray("errorModel").toString();
        var error = gson.fromJson(errorStr, ErrorModel[].class);

        //Вызов запроса
        var responseError = given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(COMMENTS_ENDPOINT)
            .then()
            .statusCode(422)
            .extract()
            .as(ErrorModel[].class);

        assertThat(responseError)
            .as("Сравнение фактической ошибки с ожидаемой")
            .hasSize(error.length)
            .containsExactlyInAnyOrderElementsOf(Lists.newArrayList(error));
    }

    public void checkPostUnprocessableEntityPost() {
        //Генерируем комментарий
        comment = generateComment(0);

        //Вызов запроса
        given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(COMMENTS_ENDPOINT)
            .then()
            .statusCode(422)
            .body("message", hasItems("must exist"))
            .body("field", hasItems("post"));
    }
}
