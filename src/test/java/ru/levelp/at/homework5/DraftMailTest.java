package ru.levelp.at.homework5;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class DraftMailTest extends MailTestBase {
    @Test(groups = {"hw5"}, dataProvider = "draftMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testDraftMessage(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата, тему письма и тело)
        //Сохранить его как черновик
        LocalDateTime dateNow = LocalDateTime.now();
        windowMessageSteps.saveMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);

        //Verify, что письмо сохранено в черновиках
        folderMessageSteps.openDraftFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        folderMessageSteps.openMessage(titleMessage + dateNow);
        windowMessageSteps.checkMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);

        //Отправить письмо
        windowMessageSteps.sendDraft();

        //Verify, что письмо исчезло из черновиков
        folderMessageSteps.isNotExistMessage(titleMessage + dateNow);

        //Verify, что письмо появилось в папке отправленные
        folderMessageSteps.openSentFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);
    }
}
