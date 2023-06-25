package ru.levelp.at.homework5;

import org.testng.annotations.DataProvider;

public class MailDataProvider {
    @DataProvider(name = "draftMailDataProvider")
    public static Object[][] draftMailDataProvider() {
        return new Object[][] {
            {"Типо тема ", "Типо текст письма"}
        };
    }

    @DataProvider(name = "ruleMoveMailDataProvider")
    public static Object[][] ruleMoveMailDataProvider() {
        return new Object[][] {
            {"Тест ", "Типо текст письма"}
        };
    }

    @DataProvider(name = "deleteMailDataProvider")
    public static Object[][] deleteMailDataProvider() {
        return new Object[][] {
            {"Типо тема ", "Типо текст письма"}
        };
    }
}
