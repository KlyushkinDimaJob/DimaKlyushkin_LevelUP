package ru.levelp.at.homework6.functions.users;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.UserModel;

public class UsersPutFunctions extends CommonGoRestFunctions {
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

    public void checkPutOK() {
        //Генерируем новые данные пользователя
        var userNew = generateUser();

        //Вызов запроса
        UserModel responseUser = given()
            .body(userNew.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(USERS_ENDPOINT + "/" + user.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(UserModel.class);

        assertThat(responseUser)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(userNew);

        assertThat(responseUser.getId())
            .as("Сравнение id пользователя")
            .isEqualTo(user.getId());
    }

    public void checkPutUnauthorized() {
        //Получаем случайного пользователя
        var userNew = getRandomUser();

        //Вызов запроса
        given()
            .body(user.toString())
            .when()
            .put(USERS_ENDPOINT + "/" + userNew.getId())
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPutNotFound() {
        //Вызов запроса
        given()
            .body(user.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(USERS_ENDPOINT + "/000001")
            .then()
            .statusCode(404)
            .body("message", equalTo("Resource not found"));
    }

    public void checkPutUnprocessableEntityEmail() {
        //Генерируем новые данные пользователя
        var userNew = generateUser();
        var anyUser = getRandomUser();
        userNew.setEmail(anyUser.getEmail());

        //Вызов запроса
        given()
            .body(userNew.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .put(USERS_ENDPOINT + "/" + user.getId())
            .then()
            .statusCode(422)
            .body("message", hasItems("has already been taken"))
            .body("field", hasItems("email"));
    }
}
