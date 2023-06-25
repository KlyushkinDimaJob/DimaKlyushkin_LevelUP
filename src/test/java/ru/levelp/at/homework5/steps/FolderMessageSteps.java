package ru.levelp.at.homework5.steps;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.MessagesContainPage;

public class FolderMessageSteps {
    private final MessagesContainPage messagesContainPage;

    public FolderMessageSteps(WebDriver driver) {
        messagesContainPage = new MessagesContainPage(driver);
    }

    public void isExistMessage(String title) {
        assertTrue(messagesContainPage.isExistMessage(title), "Письмо не найдено");
    }

    public void isNotExistMessage(String title) {
        assertTrue(messagesContainPage.isNotExistMessage(title), "Письмо найдено");
    }

    public void openMessage(String title) {
        messagesContainPage.openMessage(title);
    }

    public void openDraftFolder() {
        messagesContainPage.openDraftFolder();
    }

    public void openIncomingFolder() {
        messagesContainPage.openIncomingFolder();
    }

    public void openSentFolder() {
        messagesContainPage.openSentFolder();
    }

    public void openTestFolder() {
        messagesContainPage.openTestFolder();
    }

    public void openTomyselfFolder() {
        messagesContainPage.openTomyselfFolder();
    }

    public void openTrashFolder() {
        messagesContainPage.openTrashFolder();
    }
}
