package ru.levelp.at.homework6.functions.comments;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.CommentModel;

public class CommentsGetFunctions extends CommonGoRestFunctions {
    public void checkGetByCommentIdOK() {
        //Получаем случайный комментарий
        var comment = getRandomComment();

        //Вызов запроса
        var responseCommentModel = given()
            .when()
            .get(COMMENTS_ENDPOINT + "/" + comment.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(CommentModel.class);

        Assert.assertEquals(responseCommentModel, comment, "Ответы не совпадают");
    }

    public void checkGetByCommentIdNotFound() {
        //Вызов запроса
        given()
            .when()
            .get(COMMENTS_ENDPOINT + "/" + 0)
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
