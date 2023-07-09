package ru.levelp.at.homework6.functions.posts;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import java.util.Arrays;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersPost;

public class PostsAllGetFunctions extends CommonGoRestFunctions {
    public void checkGetAllPosts(ParametersPost[] param) {
        //Получаем случайный пост
        var post = getRandomPost();

        //Добавляем параметры в запрос
        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        ResponseSpecBuilder resSpec = new ResponseSpecBuilder();
        Arrays.stream(param).forEach(x -> {
            switch (x) {
                case USER_ID:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, post.getUserId());
                    break;
                case TITLE:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, post.getTitle());
                    break;
                case BODY:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, post.getBody());
                    break;
                default:
                    throw new IllegalArgumentException("Неверно переданный параметр: " + x.value);
            }
        });

        //Вызов запроса
        given()
            .spec(reqSpec.build())
            .when()
            .get(POSTS_ENDPOINT)
            .then()
            .spec(resSpec.build())
            .statusCode(200);
    }
}
