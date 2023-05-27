package ru.levelp.at.homework2;

import org.testng.annotations.DataProvider;

public class LuckyTicketDataProvider {
    @DataProvider(name = "luckyTicketDataProvider")
    public static Object[][] luckyTicketDataProvider() {
        return new Object[][] {
            {123321}, {246822}, {0}, {1001}, {999999}
        };
    }

    @DataProvider(name = "notLuckyTicketDataProvider")
    public static Object[][] notLuckyTicketDataProvider() {
        return new Object[][] {
            {111112}, {211111}, {2001}
        };
    }

    @DataProvider(name = "sizeNumberDataProvider")
    public static Object[][] sizeNumberDataProvider() {
        return new Object[][] {
            {-1001}, {1011011}, {1001011}
        };
    }
}
