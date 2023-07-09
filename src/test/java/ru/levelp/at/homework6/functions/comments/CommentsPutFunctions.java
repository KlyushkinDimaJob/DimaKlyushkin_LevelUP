package ru.levelp.at.homework6.functions.comments;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.CommentModel;

public class CommentsPutFunctions extends CommonGoRestFunctions {
    private CommentModel comment;

    @BeforeMethod(groups = {"hw6", "comments"})
    public void beforeMethod() {
        //Получаем случайный пост
        var post = getRandomPost();

        //Генерируем комментарий
        comment = generateComment(post.getId());

        //Вызов запроса
        comment = given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(COMMENTS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(CommentModel.class);
    }

    @AfterMethod(groups = {"hw6", "comments"})
    public void afterMethod() {
        if (comment != null && comment.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(COMMENTS_ENDPOINT + "/" + comment.getId());
        }
    }

    public void checkPutOK() {
        //Генерируем новые данные комментария
        var commentNew = generateComment(comment.getPostId());

        //Вызов запроса
        CommentModel responseComment = given()
            .body(commentNew.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(COMMENTS_ENDPOINT + "/" + comment.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(CommentModel.class);

        assertThat(responseComment)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(commentNew);

        assertThat(responseComment.getId())
            .as("Сравнение id пользователя")
            .isEqualTo(comment.getId());
    }

    public void checkPutUnauthorized() {
        //Получаем случайный комментарий
        var commentNew = getRandomComment();

        //Вызов запроса
        given()
            .body(comment.toString())
            .when()
            .put(COMMENTS_ENDPOINT + "/" + commentNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPutNotFound() {
        //Вызов запроса
        given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(COMMENTS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }

    public void checkPutUnprocessableEntityPost() {
        //Изменяем значение post_id на не существующий
        comment.setPostId(0);

        //Вызов запроса
        given()
            .body(comment.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(COMMENTS_ENDPOINT + "/" + comment.getId())
            .then()
            .statusCode(422)
            .body("message", hasItems("must exist"))
            .body("field", hasItems("post"));
    }
}
