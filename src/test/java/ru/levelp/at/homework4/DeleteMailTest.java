package ru.levelp.at.homework4;

import org.testng.annotations.Test;
import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DeleteMailTest extends MailTestBase {
    @Test
    public void testDeleteMessage() {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage("Типо тема " + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage("Типо текст письма");

        //Отправить письмо
        incomingFolderMailPage.sendMessage();
        incomingFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо появилось в папке Входящие
        incomingFolderMailPage.openTomyselfFolder();
        TomyselfFolderMailPage tomyselfFolderMailPage = new TomyselfFolderMailPage(driver);
        assertTrue(tomyselfFolderMailPage.isExistMessage("Типо тема " + dateNow),  "Письмо не найдено в папке Входящие");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        tomyselfFolderMailPage.openMessage("Типо тема " + dateNow);
        MessagePage messagePage = new MessagePage(driver);
        assertEquals(messagePage.getMessageReceiver(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(messagePage.getMessageTitle(), "Типо тема " + dateNow, "Тема письма не совпадает");
        assertTrue(messagePage.getMessageText().contains("Типо текст письма"), "Текст письма не совпадает");

        //Удалить письмо
        messagePage.delMessage();

        //Verify что письмо появилось в папке Корзина
        TrashFolderMailPage trashFolderMailPage = new TrashFolderMailPage(driver);
        trashFolderMailPage.openTrashFolder();
        trashFolderMailPage.isExistMessage("Типо тема " + dateNow);
    }
}
