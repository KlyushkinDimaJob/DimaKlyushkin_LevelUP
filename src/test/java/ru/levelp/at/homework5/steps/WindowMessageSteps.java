package ru.levelp.at.homework5.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.MessagesContainPage;

public class WindowMessageSteps {
    private final MessagesContainPage messagesContainPage;

    public WindowMessageSteps(WebDriver driver) {
        messagesContainPage = new MessagesContainPage(driver);
    }

    public void sendMessage(String title, String email, String text) {
        fillFieldMail(title, email, text);
        messagesContainPage.sendMessage();
        messagesContainPage.closeInfoSendMessage();
    }

    public void sendDraft() {
        messagesContainPage.sendMessage();
        messagesContainPage.closeInfoSendMessage();
    }

    public void saveMessage(String title, String email, String text) {
        fillFieldMail(title, email, text);
        messagesContainPage.saveMessage();
        messagesContainPage.closeMessage();
    }

    public void checkMessage(String title, String email, String text) {
        assertEquals(messagesContainPage.getReceiverMessage(), email, "Адресат письма не совпадает");
        assertEquals(messagesContainPage.getTitleMessage(), title, "Тема письма не совпадает");
        assertTrue(messagesContainPage.getTextMessage().contains(text), "Текст письма не совпадает");
    }

    private void fillFieldMail(String title, String email, String text) {
        messagesContainPage.openNewMessage();
        messagesContainPage.fillTitleMessage(title);
        messagesContainPage.fillReceiverMessage(email);
        messagesContainPage.fillTextMessage(text);
    }
}
