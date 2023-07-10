package ru.levelp.at.homework6.functions.users;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.testng.annotations.AfterMethod;
import ru.levelp.at.homework6.functions.CommonGoRestFunctions;
import ru.levelp.at.homework6.model.response.ErrorModel;
import ru.levelp.at.homework6.model.response.UserModel;

public class UsersPostFunctions extends CommonGoRestFunctions {
    private UserModel user;
    private UserModel responseUser;

    /**
     * Удаление пользователя после завершение кейса.
     */
    @AfterMethod(groups = {"hw6", "users"})
    public void afterMethod() {
        if (responseUser != null && responseUser.getId() != null) {
            given()
                .queryParam("access-token", TOKEN)
                .when()
                .delete(USERS_ENDPOINT + "/" + responseUser.getId());
        }
    }

    public void checkPostUserCreated() {
        //Генерируем пользователя
        user = generateUser();

        //Вызов запроса
        responseUser = given()
            .body(user.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .statusCode(201)
            .extract()
            .as(UserModel.class);

        assertThat(user)
            .as("Сравнение фактического тела ответа с ожидаемым")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(responseUser);
    }

    public void checkPostUnauthorized() {
        //Генерируем пользователя
        user = generateUser();

        //Вызов запроса
        given()
            .body(user.toString())
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .statusCode(401)
            .body("message", equalTo("Authentication failed"));
    }

    public void checkPostUnprocessableEntity(String testData) {
        //Генерируем пользователя
        var gson = new Gson();
        user = gson.fromJson(testData, UserModel.class);
        var errorStr = JsonParser.parseString(testData).getAsJsonObject().getAsJsonArray("errorModel").toString();
        var error = gson.fromJson(errorStr, ErrorModel[].class);

        //Вызов запроса
        var responseError = given()
            .body(user.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .statusCode(422)
            .extract()
            .as(ErrorModel[].class);

        assertThat(responseError)
            .as("Сравнение фактической ошибки с ожидаемой")
            .hasSize(error.length)
            .containsExactlyInAnyOrderElementsOf(Lists.newArrayList(error));
    }

    public void checkPostUnprocessableEntityEmail() {
        //Генерируем новые данные пользователя
        var userNew = generateUser();
        var anyUser = getRandomUser();
        userNew.setEmail(anyUser.getEmail());

        //Вызов запроса
        given()
            .body(userNew.toString())
            .queryParam("access-token", TOKEN)
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .statusCode(422)
            .body("message", hasItems("has already been taken"))
            .body("field", hasItems("email"));
    }
}
