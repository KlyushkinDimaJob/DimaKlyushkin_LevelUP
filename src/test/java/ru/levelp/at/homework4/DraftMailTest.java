package ru.levelp.at.homework4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class DraftMailTest extends MailTestBase {
    @Test(groups = {"hw4"}, dataProvider = "draftMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testDraftMessage(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата, тему письма и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage(titleMessage + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage(textMessage);

        //Сохранить его как черновик
        incomingFolderMailPage.saveMessage();
        incomingFolderMailPage.closeMessage();

        //Verify, что письмо сохранено в черновиках
        DraftFolderMailPage draftFolderMailPage = new DraftFolderMailPage(driver);
        draftFolderMailPage.openDraftFolder();
        assertTrue(draftFolderMailPage.isExistMessage(titleMessage + dateNow), "Письмо не сохранено в черновики");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        draftFolderMailPage.openMessage(titleMessage + dateNow);
        assertEquals(draftFolderMailPage.getReceiverMessage(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(draftFolderMailPage.getTitleMessage(), titleMessage + dateNow, "Тема письма не совпадает");
        assertTrue(draftFolderMailPage.getTextMessage().contains(textMessage), "Текст письма не совпадает");

        //Отправить письмо
        draftFolderMailPage.sendMessage();
        draftFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо исчезло из черновиков
        assertTrue(draftFolderMailPage.isNotExistMessage(titleMessage + dateNow), "Письмо осталось в черновиках");

        //Verify, что письмо появилось в папке отправленные
        SentFolderMailPage sentFolderMailPage = new SentFolderMailPage(driver);
        sentFolderMailPage.openSentFolder();
        assertTrue(sentFolderMailPage.isExistMessage(titleMessage + dateNow), "Письмо не найдено в отправленных");
    }
}
