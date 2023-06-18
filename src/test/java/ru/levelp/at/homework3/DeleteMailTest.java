package ru.levelp.at.homework3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class DeleteMailTest extends MailTestBase {
    @Test
    public void testDeleteMessage() {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
        btnNewMessage.click();
        WebElement editMessageReceiver = driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
        editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
        WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
        LocalDateTime dateNow = LocalDateTime.now();
        editMessageTitle.sendKeys("Типо тема " + dateNow);
        WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
        editMessageText.sendKeys("Типо текст письма");

        //Отправить письмо
        WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
        btnSendMessage.click();
        WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
        infoSendMessage.click();

        //Verify, что письмо появилось в папке Входящие
        WebElement linkIncomingMail = driver.findElement(By.cssSelector("a.nav__item[title='Входящие']"));
        linkIncomingMail.click();
        WebElement linkTomyself = driver.findElement(By.xpath("//span[contains(.,'Письма себе')]/ancestor::a"));
        linkTomyself.click();
        var message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в папке Входящие");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        message.get(0).click();
        editMessageReceiver = driver.findElement(By.cssSelector("span.letter-contact"));
        assertEquals(editMessageReceiver.getAttribute("Title"), "klyushkindimatest@mail.ru",
            "Адресат письма не совпадает");
        editMessageTitle = driver.findElement(By.cssSelector("h2.thread-subject"));
        assertEquals(editMessageTitle.getText(), "Типо тема " + dateNow, "Тема письма не совпадает");
        editMessageText = driver.findElement(By.cssSelector("div.letter-body"));
        assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");

        //Удалить письмо
        WebElement btnDel = driver.findElement(By.xpath("//div[@class='button2__txt' and contains(.,'Удалить')]"));
        btnDel.click();

        //Verify что письмо появилось в папке Корзина
        WebElement linkTrash = driver.findElement(By.cssSelector("a.nav__item[title='Корзина']"));
        linkTrash.click();
        message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в корзине");
    }
}
