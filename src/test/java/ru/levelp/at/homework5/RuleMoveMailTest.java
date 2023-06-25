package ru.levelp.at.homework5;

import java.time.LocalDateTime;
import org.testng.annotations.Test;

public class RuleMoveMailTest extends MailTestBase {
    @Test(groups = {"hw5"}, dataProvider = "ruleMoveMailDataProvider", dataProviderClass = MailDataProvider.class)
    public void testRuleMessageTest(String titleMessage, String textMessage) {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        //Отправить письмо
        LocalDateTime dateNow = LocalDateTime.now();
        windowMessageSteps.sendMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);

        //Verify, что письмо появилось в папке отправленные
        folderMessageSteps.openSentFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);

        //Verify, что письмо появилось в папке «Тест»
        folderMessageSteps.openTestFolder();
        folderMessageSteps.isExistMessage(titleMessage + dateNow);

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        folderMessageSteps.openMessage(titleMessage + dateNow);
        pageMessageSteps.checkMessage(titleMessage + dateNow, properties.getProperty("email"), textMessage);
    }
}
