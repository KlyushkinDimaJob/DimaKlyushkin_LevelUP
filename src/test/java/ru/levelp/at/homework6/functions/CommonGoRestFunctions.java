package ru.levelp.at.homework6.functions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import ru.levelp.at.homework6.functions.GoRestEnum.GenderUser;
import ru.levelp.at.homework6.functions.GoRestEnum.StatusUser;
import ru.levelp.at.homework6.model.response.CommentModel;
import ru.levelp.at.homework6.model.response.PostModel;
import ru.levelp.at.homework6.model.response.UserModel;

public class CommonGoRestFunctions {
    public static final String BASE_URL = "https://gorest.co.in/public/v2";
    public static final String USERS_ENDPOINT = "/users";
    public static final String POSTS_ENDPOINT = "/posts";
    public static final String COMMENTS_ENDPOINT = "/comments";
    public static final String TOKEN = "1503fc47d8afe5a69f1b5c14aafc59abccb75c4c8607d50435626b6c963a9932";

    @BeforeClass(groups = {"hw6", "users", "posts", "comments"})
    public void beforeClass() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    }

    /**
     * Возвращает случайного пользователя.
     *
     * @return UserModel - модель пользователя
     */
    protected UserModel getRandomUser() {
        //Получаем пользователей
        ArrayList<UserModel> users = Arrays.stream(given()
            .when()
            .get(USERS_ENDPOINT)
            .then()
            .statusCode(200)
            .extract()
            .as(UserModel[].class)).collect(Collectors.toCollection(ArrayList::new));

        //Получаем случайного пользователя
        return users.stream().findAny().orElseThrow();
    }

    /**
     * Возвращает случайный пост.
     *
     * @return PostModel - модель поста
     */
    protected PostModel getRandomPost() {
        //Получаем посты
        ArrayList<PostModel> posts = Arrays.stream(given()
            .when()
            .get(POSTS_ENDPOINT)
            .then()
            .statusCode(200)
            .extract()
            .as(PostModel[].class)).collect(Collectors.toCollection(ArrayList::new));

        //Получаем случайный пост
        return posts.stream().findAny().orElseThrow();
    }

    /**
     * Возвращает случайный комментарий.
     *
     * @return CommentModel - модель комментария
     */
    protected CommentModel getRandomComment() {
        //Получаем посты
        ArrayList<CommentModel> comments = Arrays.stream(given()
            .when()
            .get(COMMENTS_ENDPOINT)
            .then()
            .statusCode(200)
            .extract()
            .as(CommentModel[].class)).collect(Collectors.toCollection(ArrayList::new));

        //Получаем случайный комментарий
        return comments.stream().findAny().orElseThrow();
    }

    /**
     * Возвращает случайный gender.
     *
     * @return GenderUser - случайный gender пользователя
     */
    protected GenderUser getRandomGenderUser() {
        return Arrays.stream(GenderUser.values()).findAny().orElseThrow();
    }

    /**
     * Возвращает случайный status.
     *
     * @return StatusUser - случайный status пользователя
     */
    protected StatusUser getRandomStatusUser() {
        return Arrays.stream(StatusUser.values()).findAny().orElseThrow();
    }

    /**
     * Возвращает сгенерированного пользователя.
     *
     * @return UserModel - модель пользователя
     */
    protected UserModel generateUser() {
        final var faker = new Faker();

        return new UserModel(faker.name().firstName() + " " + faker.name().lastName(),
            faker.internet().emailAddress(), getRandomGenderUser().value, getRandomStatusUser().value);
    }

    /**
     * Возвращает сгенерированный пост.
     *
     * @return PostModel - модель поста
     */
    protected PostModel generatePost(Integer userId) {
        final var faker = new Faker();

        return new PostModel(userId, faker.lorem().sentence(6), faker.lorem().sentence(10));
    }

    /**
     * Возвращает сгенерированный комментарий.
     *
     * @return CommentModel - модель комментария
     */
    protected CommentModel generateComment(Integer postId) {
        final var faker = new Faker();

        return new CommentModel(postId, faker.name().firstName() + " " + faker.name().lastName(),
            faker.internet().emailAddress(), faker.lorem().sentence(10));
    }

    /**
     * Дополняет тело запроса параметрами для фильтрации и тело ответа для проверки фильтрации.
     */
    protected void buildRequestAndResponseSpec(RequestSpecBuilder reqSpec, ResponseSpecBuilder resSpec,
                                               String nameParam, Object valueParam) {
        reqSpec.addParam(nameParam, valueParam);
        resSpec.expectBody(nameParam, hasItems(valueParam));
    }
}
