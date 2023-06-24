package ru.levelp.at.homework3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class RuleMoveMailTest extends MailTestBase {
    @Test
    public void testRuleMessageTest() {
        //Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
        btnNewMessage.click();
        WebElement editMessageReceiver = driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
        editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
        WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
        LocalDateTime dateNow = LocalDateTime.now();
        editMessageTitle.sendKeys("Тест " + dateNow);
        WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
        editMessageText.sendKeys("Типо текст письма");

        //Отправить письмо
        WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
        btnSendMessage.click();
        WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
        infoSendMessage.click();

        //Verify, что письмо появилось в папке отправленные
        WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
        linkSent.click();
        var message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в отправленных");

        //Verify, что письмо появилось в папке «Тест»
        WebElement navigationMenu = driver.findElement(By.cssSelector("span.sidebar__menu-ico"));
        navigationMenu.click();
        WebElement testFolder =
            driver.findElement(By.xpath("//div[@class='nav__folder-name__txt' and contains(.,'Тест')]/ancestor::a"));
        driver.navigate().to(testFolder.getAttribute("href"));
        message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в папке Тест");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        message.get(0).click();
        editMessageReceiver = driver.findElement(By.cssSelector("span.letter-contact"));
        assertEquals(editMessageReceiver.getAttribute("Title"), "klyushkindimatest@mail.ru",
            "Адресат письма не совпадает");
        editMessageTitle = driver.findElement(By.cssSelector("h2.thread-subject"));
        assertEquals(editMessageTitle.getText(), "Тест " + dateNow, "Тема письма не совпадает");
        editMessageText = driver.findElement(By.cssSelector("div.letter-body"));
        assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");
    }
}
