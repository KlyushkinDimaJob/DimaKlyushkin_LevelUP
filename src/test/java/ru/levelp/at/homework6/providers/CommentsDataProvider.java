package ru.levelp.at.homework6.providers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import ru.levelp.at.Util;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersComment;

public class CommentsDataProvider {
    @DataProvider(name = "commentsGetAllParamDataProvider")
    public static Object[][] commentsGetAllParamDataProvider() {
        return new Object[][] {
            {ParametersComment.NAME},
            {ParametersComment.EMAIL},
            {ParametersComment.POST_ID},
            {ParametersComment.BODY},
            {ParametersComment.NAME, ParametersComment.EMAIL},
            {ParametersComment.NAME, ParametersComment.POST_ID},
            {ParametersComment.NAME, ParametersComment.BODY},
            {ParametersComment.EMAIL, ParametersComment.POST_ID},
            {ParametersComment.EMAIL, ParametersComment.BODY},
            {ParametersComment.POST_ID, ParametersComment.BODY},
            {ParametersComment.NAME, ParametersComment.EMAIL, ParametersComment.POST_ID},
            {ParametersComment.NAME, ParametersComment.EMAIL, ParametersComment.BODY},
            {ParametersComment.NAME, ParametersComment.POST_ID, ParametersComment.BODY},
            {ParametersComment.EMAIL, ParametersComment.POST_ID, ParametersComment.BODY},
            {ParametersComment.NAME, ParametersComment.EMAIL, ParametersComment.POST_ID, ParametersComment.BODY},
        };
    }

    @DataProvider(name = "commentsPostWithoutFieldDataProvider")
    public static Iterator<Object[]> commentsPostWithoutFieldDataProvider() {
        String file = Util.readFile("homework6/commentsPostWithoutField.json");
        JsonArray json = new Gson()
            .fromJson(file, JsonArray.class);
        ArrayList<Object[]> array = new ArrayList<>();
        json.asList().forEach(x -> array.add(new Object[] {x.toString()}));
        return array.iterator();
    }
}
