package ru.levelp.at.homework5.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.MessagePage;

public class PageMessageSteps {
    private final MessagePage messagePage;

    public PageMessageSteps(WebDriver driver) {
        messagePage = new MessagePage(driver);
    }

    public void checkMessage(String title, String email, String text) {
        assertEquals(messagePage.getMessageReceiver(), email, "Адресат письма не совпадает");
        assertEquals(messagePage.getMessageTitle(), title, "Тема письма не совпадает");
        assertTrue(messagePage.getMessageText().contains(text), "Текст письма не совпадает");
    }

    public void deleteMessage() {
        messagePage.delMessage();
    }
}
