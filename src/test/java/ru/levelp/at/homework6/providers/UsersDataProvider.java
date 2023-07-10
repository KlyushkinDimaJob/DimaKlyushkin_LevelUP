package ru.levelp.at.homework6.providers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import ru.levelp.at.Util;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersUser;

public class UsersDataProvider {
    @DataProvider(name = "usersGetAllParamDataProvider")
    public static Object[][] usersGetAllParamDataProvider() {
        return new Object[][] {
            {ParametersUser.NAME},
            {ParametersUser.EMAIL},
            {ParametersUser.GENDER},
            {ParametersUser.STATUS},
            {ParametersUser.NAME, ParametersUser.EMAIL},
            {ParametersUser.NAME, ParametersUser.GENDER},
            {ParametersUser.NAME, ParametersUser.STATUS},
            {ParametersUser.EMAIL, ParametersUser.GENDER},
            {ParametersUser.EMAIL, ParametersUser.STATUS},
            {ParametersUser.GENDER, ParametersUser.STATUS},
            {ParametersUser.NAME, ParametersUser.EMAIL, ParametersUser.GENDER},
            {ParametersUser.NAME, ParametersUser.EMAIL, ParametersUser.STATUS},
            {ParametersUser.NAME, ParametersUser.GENDER, ParametersUser.STATUS},
            {ParametersUser.EMAIL, ParametersUser.GENDER, ParametersUser.STATUS},
            {ParametersUser.NAME, ParametersUser.EMAIL, ParametersUser.GENDER, ParametersUser.STATUS},
        };
    }

    @DataProvider(name = "usersPostWithoutFieldDataProvider")
    public static Iterator<Object[]> usersPostWithoutFieldDataProvider() {
        String file = Util.readFile("homework6/usersPostWithoutField.json");
        JsonArray json = new Gson()
            .fromJson(file, JsonArray.class);
        ArrayList<Object[]> array = new ArrayList<>();
        json.asList().forEach(x -> array.add(new Object[] {x.toString()}));
        return array.iterator();
    }
}
