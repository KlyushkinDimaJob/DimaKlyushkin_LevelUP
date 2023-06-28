package ru.levelp.at.homework4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class DeleteMailTest extends MailTestBase {
    @Test(groups = {"hw4"}, dataProvider = "deleteMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testDeleteMessage(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        IncomingFolderMailPage incomingFolderMailPage = new IncomingFolderMailPage(driver);
        incomingFolderMailPage.openNewMessage();
        LocalDateTime dateNow = LocalDateTime.now();
        incomingFolderMailPage.fillTitleMessage(titleMessage + dateNow);
        incomingFolderMailPage.fillReceiverMessage(properties.getProperty("email"));
        incomingFolderMailPage.fillTextMessage(textMessage);

        //Отправить письмо
        incomingFolderMailPage.sendMessage();
        incomingFolderMailPage.closeInfoSendMessage();

        //Verify, что письмо появилось в папке Входящие
        incomingFolderMailPage.openTomyselfFolder();
        TomyselfFolderMailPage tomyselfFolderMailPage = new TomyselfFolderMailPage(driver);
        assertTrue(tomyselfFolderMailPage.isExistMessage(titleMessage + dateNow),
            "Письмо не найдено в папке Входящие");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        tomyselfFolderMailPage.openMessage(titleMessage + dateNow);
        MessagePage messagePage = new MessagePage(driver);
        assertEquals(messagePage.getMessageReceiver(), properties.getProperty("email"),
            "Адресат письма не совпадает");
        assertEquals(messagePage.getMessageTitle(), titleMessage + dateNow, "Тема письма не совпадает");
        assertTrue(messagePage.getMessageText().contains(textMessage), "Текст письма не совпадает");

        //Удалить письмо
        messagePage.delMessage();

        //Verify что письмо появилось в папке Корзина
        TrashFolderMailPage trashFolderMailPage = new TrashFolderMailPage(driver);
        trashFolderMailPage.openTrashFolder();
        trashFolderMailPage.isExistMessage(titleMessage + dateNow);
    }
}
