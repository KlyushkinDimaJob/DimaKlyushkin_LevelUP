package ru.levelp.at.homework3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class DraftMailTest extends MailTestBase {
    @Test
    public void testDraftMessage() {
        //Создать новое письмо (заполнить адресата, тему письма и тело)
        WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
        btnNewMessage.click();
        WebElement editMessageReceiver = driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
        editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
        WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
        LocalDateTime dateNow = LocalDateTime.now();
        editMessageTitle.sendKeys("Типо тема " + dateNow);
        WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
        editMessageText.sendKeys("Типо текст письма");

        //Сохранить его как черновик
        WebElement btnSaveMessage = driver.findElement(By.cssSelector("button[data-test-id='save']"));
        btnSaveMessage.click();

        //Verify, что письмо сохранено в черновиках
        var notifyDraft = driver.findElements(
            By.xpath("//span[@class='notify__message__text' and contains(.,'Сохранено в черновиках')]"));
        assertTrue(notifyDraft.size() > 0, "Письмо не сохранено в черновики");
        WebElement infoSendMessage = driver.findElement(By.xpath("//button[@title='Закрыть']"));
        infoSendMessage.click();
        WebElement linkDrafts = driver.findElement(By.cssSelector("a.nav__item[title='Черновики']"));
        linkDrafts.click();
        var message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не сохранено в черновики");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        message.get(0).click();
        editMessageReceiver =
            driver.findElement(By.cssSelector("div[class='contactsContainer--3RMuQ'] span[class='text--1tHKB']"));
        assertEquals(editMessageReceiver.getText(), "klyushkindimatest@mail.ru", "Адресат письма не совпадает");
        editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
        assertEquals(editMessageTitle.getAttribute("value"), "Типо тема " + dateNow, "Тема письма не совпадает");
        editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
        assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");

        //Отправить письмо
        WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
        btnSendMessage.click();
        infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
        infoSendMessage.click();

        //Verify, что письмо исчезло из черновиков
        boolean deleteDraftMessage = wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]")));
        assertTrue(deleteDraftMessage, "Письмо осталось в черновиках");

        //Verify, что письмо появилось в папке отправленные
        WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
        linkSent.click();
        message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в отправленных");
    }
}
