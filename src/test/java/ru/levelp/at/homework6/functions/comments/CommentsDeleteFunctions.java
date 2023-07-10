package ru.levelp.at.homework6.functions.comments;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.CommentModel;

public class CommentsDeleteFunctions extends CommonGoRestFunctions {
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

    public void checkDeleteNoContent() {
        //Вызов запроса
        given()
            .queryParam("access-token", TOKEN)
            .when()
            .delete(COMMENTS_ENDPOINT + "/" + comment.getId())
            .then()
            .statusCode(204);
    }

    public void checkDeleteUnauthorized() {
        //Получаем случайный комментарий
        var commentNew = getRandomComment();

        //Вызов запроса
        given()
            .when()
            .delete(COMMENTS_ENDPOINT + "/" + commentNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkDeleteNotFound() {
        //Вызов запроса
        given()
            .when()
            .delete(COMMENTS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
