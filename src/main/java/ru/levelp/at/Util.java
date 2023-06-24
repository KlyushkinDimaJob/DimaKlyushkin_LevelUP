package ru.levelp.at;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(
            "src/test/resources/ru/levelp/at/" + path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
