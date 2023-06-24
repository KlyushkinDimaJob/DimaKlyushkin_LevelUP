package ru.levelp.at.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DraftMailTest extends MailTestBase {
    @Test
    public void testDraftMessage() {
        //Создать новое письмо (заполнить адресата, тему письма и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage("Типо тема " + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage("Типо текст письма");

        //Сохранить его как черновик
        incomingFolderMailPage.saveMessage();
        incomingFolderMailPage.closeMessage();

        //Verify, что письмо сохранено в черновиках
        DraftFolderMailPage draftFolderMailPage = new DraftFolderMailPage(driver);
        draftFolderMailPage.openDraftFolder();
        assertTrue(draftFolderMailPage.isExistMessage("Типо тема " + dateNow), "Письмо не сохранено в черновики");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        draftFolderMailPage.openMessage("Типо тема " + dateNow);
        assertEquals(draftFolderMailPage.getReceiverMessage(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(draftFolderMailPage.getTitleMessage(), "Типо тема " + dateNow, "Тема письма не совпадает");
        assertTrue(draftFolderMailPage.getTextMessage().contains("Типо текст письма"), "Текст письма не совпадает");

        //Отправить письмо
        draftFolderMailPage.sendMessage();
        draftFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо исчезло из черновиков
        assertFalse(draftFolderMailPage.isExistMessage("Типо тема " + dateNow), "Письмо осталось в черновиках");

        //Verify, что письмо появилось в папке отправленные
        SentFolderMailPage sentFolderMailPage = new SentFolderMailPage(driver);
        sentFolderMailPage.openSentFolder();
        assertTrue(sentFolderMailPage.isExistMessage("Типо тема " + dateNow), "Письмо не найдено в отправленных");
    }
}
