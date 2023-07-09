package ru.levelp.at.homework6.functions.comments;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import java.util.Arrays;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersComment;

public class CommentsAllGetFunctions extends CommonGoRestFunctions {
    public void checkGetAllComments(ParametersComment[] param) {
        //Получаем случайный комментарий
        var comment = getRandomComment();

        //Добавляем параметры в запрос
        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        ResponseSpecBuilder resSpec = new ResponseSpecBuilder();
        Arrays.stream(param).forEach(x -> {
            switch (x) {
                case POST_ID:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, comment.getPostId());
                    break;
                case NAME:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, comment.getName());
                    break;
                case EMAIL:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, comment.getEmail());
                    break;
                case BODY:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, comment.getBody());
                    break;
                default:
                    throw new IllegalArgumentException("Неверно переданный параметр: " + x.value);
            }
        });

        //Вызов запроса
        given()
            .spec(reqSpec.build())
            .when()
            .get(COMMENTS_ENDPOINT)
            .then()
            .spec(resSpec.build())
            .statusCode(200);
    }
}
