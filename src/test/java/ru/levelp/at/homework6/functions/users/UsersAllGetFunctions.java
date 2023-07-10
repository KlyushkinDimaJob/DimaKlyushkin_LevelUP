package ru.levelp.at.homework6.functions.users;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import java.util.Arrays;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersUser;

public class UsersAllGetFunctions extends CommonGoRestFunctions {
    public void checkGetAllUser(ParametersUser[] param) {
        //Получаем случайного пользователя
        var user = getRandomUser();

        //Добавляем параметры в запрос
        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        ResponseSpecBuilder resSpec = new ResponseSpecBuilder();
        Arrays.stream(param).forEach(x -> {
            switch (x) {
                case EMAIL:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, user.getEmail());
                    break;
                case GENDER:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, user.getGender());
                    break;
                case NAME:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, user.getName());
                    break;
                case STATUS:
                    buildRequestAndResponseSpec(reqSpec, resSpec, x.value, user.getStatus());
                    break;
                default:
                    throw new IllegalArgumentException("Неверно переданный параметр: " + x.value);
            }
        });

        //Вызов запроса
        given()
            .spec(reqSpec.build())
            .when()
            .get(USERS_ENDPOINT)
            .then()
            .spec(resSpec.build())
            .statusCode(200);
    }
}
