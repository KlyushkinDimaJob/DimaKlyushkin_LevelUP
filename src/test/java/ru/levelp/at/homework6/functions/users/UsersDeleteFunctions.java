package ru.levelp.at.homework6.functions.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.UserModel;

public class UsersDeleteFunctions extends CommonGoRestFunctions {
    private UserModel user;

    /**
     * Предварительное создание пользователя.
     */
    @BeforeMethod(groups = {"hw6", "users"})
    public void beforeMethod() {
        //Генерируем пользователя
        user = generateUser();

        //Вызов запроса
        user = given()
            .body(user.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(UserModel.class);
    }

    /**
     * Удаление пользователя после завершение кейса.
     */
    @AfterMethod(groups = {"hw6", "users"})
    public void afterMethod() {
        if (user != null && user.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(USERS_ENDPOINT + "/" + user.getId());
        }
    }

    public void checkDeleteNoContent() {
        //Вызов запроса
        given()
            .queryParam("access-token", TOKEN)
            .when()
            .delete(USERS_ENDPOINT + "/" + user.getId())
            .then()
            .statusCode(204);
    }

    public void checkDeleteUnauthorized() {
        //Получаем случайного пользователя
        var userNew = getRandomUser();

        //Вызов запроса
        given()
            .when()
            .delete(USERS_ENDPOINT + "/" + userNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkDeleteNotFound() {
        //Вызов запроса
        given()
            .when()
            .delete(USERS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }
}
