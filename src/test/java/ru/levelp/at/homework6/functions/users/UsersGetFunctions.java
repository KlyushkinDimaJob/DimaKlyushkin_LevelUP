package ru.levelp.at.homework6.functions.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.UserModel;

public class UsersGetFunctions extends CommonGoRestFunctions {
    public void checkGetUserByUserIdOK() {
        //Получаем случайного пользователя
        var user = getRandomUser();

        //Вызов запроса
        var responseUserModel = given()
            .when()
            .get(USERS_ENDPOINT + "/" + user.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(UserModel.class);

        Assert.assertEquals(responseUserModel, user, "Ответы не совпадают");
    }

    public void checkGetUserByUserIdNotFound() {
        //Вызов запроса
        given()
            .when()
            .get(USERS_ENDPOINT + "/" + 0)
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
