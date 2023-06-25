package ru.levelp.at.homework5;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class DeleteMailTest extends MailTestBase {
    @Test(groups = {"hw5"}, dataProvider = "deleteMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testDeleteMessage(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        //Отправить письмо
        LocalDateTime dateNow = LocalDateTime.now();
        windowMessageSteps.sendMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);

        //Verify, что письмо появилось в папке Входящие
        folderMessageSteps.openTomyselfFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        folderMessageSteps.openMessage(titleMessage + dateNow);
        pageMessageSteps.checkMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);

        //Удалить письмо
        pageMessageSteps.deleteMessage();

        //Verify что письмо появилось в папке Корзина
        folderMessageSteps.openTrashFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);
    }
}
