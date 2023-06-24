package ru.levelp.at.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RuleMoveMailTest extends MailTestBase {
    @Test
    public void testRuleMessageTest() {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage("Тест " + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage("Типо текст письма");

        //Отправить письмо
        incomingFolderMailPage.sendMessage();
        incomingFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо появилось в папке отправленные
        SentFolderMailPage sentFolderMailPage = new SentFolderMailPage(driver);
        sentFolderMailPage.openSentFolder();
        assertTrue(sentFolderMailPage.isExistMessage("Тест " + dateNow), "Письмо не найдено в отправленных");

        //Verify, что письмо появилось в папке «Тест»
        TestFolderMailPage testFolderMailPage = new TestFolderMailPage(driver);
        testFolderMailPage.openTestFolder();
        assertTrue(testFolderMailPage.isExistMessage("Тест " + dateNow), "Письмо не найдено в папке Тест");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        testFolderMailPage.openMessage("Тест " + dateNow);
        MessagePage messagePage = new MessagePage(driver);
        assertEquals(messagePage.getMessageReceiver(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(messagePage.getMessageTitle(), "Тест " + dateNow, "Тема письма не совпадает");
        assertTrue(messagePage.getMessageText().contains("Типо текст письма"), "Текст письма не совпадает");
    }
}
