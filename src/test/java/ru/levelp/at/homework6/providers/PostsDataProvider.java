package ru.levelp.at.homework6.providers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import ru.levelp.at.Util;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersPost;

public class PostsDataProvider {
    @DataProvider(name = "postsGetAllParamDataProvider")
    public static Object[][] postsGetAllParamDataProvider() {
        return new Object[][] {
            {ParametersPost.USER_ID},
            {ParametersPost.TITLE},
            {ParametersPost.BODY},
            {ParametersPost.USER_ID, ParametersPost.TITLE},
            {ParametersPost.USER_ID, ParametersPost.BODY},
            {ParametersPost.TITLE, ParametersPost.BODY},
            {ParametersPost.USER_ID, ParametersPost.TITLE, ParametersPost.BODY},
        };
    }

    @DataProvider(name = "postsPostWithoutFieldDataProvider")
    public static Iterator<Object[]> postsPostWithoutFieldDataProvider() {
        String file = Util.readFile("homework6/postsPostWithoutField.json");
        JsonArray json = new Gson()
            .fromJson(file, JsonArray.class);
        ArrayList<Object[]> array = new ArrayList<>();
        json.asList().forEach(x -> array.add(new Object[] {x.toString()}));
        return array.iterator();
    }
}
