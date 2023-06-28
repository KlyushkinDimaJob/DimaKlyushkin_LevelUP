package ru.levelp.at.homework4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class RuleMoveMailTest extends MailTestBase {
    @Test(groups = {"hw4"}, dataProvider = "ruleMoveMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testRuleMessageTest(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage(titleMessage + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage(textMessage);

        //Отправить письмо
        incomingFolderMailPage.sendMessage();
        incomingFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо появилось в папке отправленные
        SentFolderMailPage sentFolderMailPage = new SentFolderMailPage(driver);
        sentFolderMailPage.openSentFolder();
        assertTrue(sentFolderMailPage.isExistMessage(titleMessage + dateNow), "Письмо не найдено в отправленных");

        //Verify, что письмо появилось в папке «Тест»
        TestFolderMailPage testFolderMailPage = new TestFolderMailPage(driver);
        testFolderMailPage.openTestFolder();
        assertTrue(testFolderMailPage.isExistMessage(titleMessage + dateNow), "Письмо не найдено в папке Тест");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        testFolderMailPage.openMessage(titleMessage + dateNow);
        MessagePage messagePage = new MessagePage(driver);
        assertEquals(messagePage.getMessageReceiver(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(messagePage.getMessageTitle(), titleMessage + dateNow, "Тема письма не совпадает");
        assertTrue(messagePage.getMessageText().contains(textMessage), "Текст письма не совпадает");
    }
}
