package ru.levelp.at.homework7;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

@Epic("Правила")
@Story("Перемещение входящего письмо по правилу")
public class RuleMoveMailTest extends MailTestBase {
    @Test(description = "Проверка правил сохранения писем")
    @Feature("Успешное перемещение")
    public void testRuleMessageTest() {
        LocalDateTime dateNow = LocalDateTime.now();
        step(
            "Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)",
            () -> {
                WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
                btnNewMessage.click();
                WebElement editMessageReceiver =
                    driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
                editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
                WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
                editMessageTitle.sendKeys("Тест " + dateNow);
                WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
                editMessageText.sendKeys("Типо текст письма");
            });

        step("Отправить письмо", () -> {
            WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
            btnSendMessage.click();
            WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
            infoSendMessage.click();
        });

        step("Verify, что письмо появилось в папке отправленные", () -> {
            WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
            linkSent.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в отправленных");
        });

        step("Verify, что письмо появилось в папке «Тест»", () -> {
            WebElement navigationMenu = driver.findElement(By.cssSelector("span.sidebar__menu-ico"));
            navigationMenu.click();
            WebElement testFolder =
                driver.findElement(
                    By.xpath("//div[@class='nav__folder-name__txt' and contains(.,'Тест')]/ancestor::a"));
            driver.navigate().to(testFolder.getAttribute("href"));
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в папке Тест");
        });

        step("Verify контент, адресата и тему письма (должно совпадать с пунктом 3)", () -> {
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            message.get(0).click();
            WebElement editMessageReceiver = driver.findElement(By.cssSelector("span.letter-contact"));
            assertEquals(editMessageReceiver.getAttribute("Title"), "klyushkindimatest@mail.ru",
                "Адресат письма не совпадает");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("h2.thread-subject"));
            assertEquals(editMessageTitle.getText(), "Тест " + dateNow, "Тема письма не совпадает");
            WebElement editMessageText = driver.findElement(By.cssSelector("div.letter-body"));
            assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");
        });

        step("Выход", this::logout);
    }

    @Test(description = "Проверка правил сохранения писем")
    @Feature("Не успешное перемещение")
    public void testRuleMessageFailedTest() {
        LocalDateTime dateNow = LocalDateTime.now();
        step(
            "Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)",
            () -> {
                WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
                btnNewMessage.click();
                WebElement editMessageReceiver =
                    driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
                editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
                WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
                editMessageTitle.sendKeys("Тест " + dateNow);
                WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
                editMessageText.sendKeys("Типо текст письма");
            });

        step("Отправить письмо", () -> {
            WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
            btnSendMessage.click();
            WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
            //infoSendMessage.click();
            // TODO: 10.07.2023 чтобы сломать});
        });

        step("Verify, что письмо появилось в папке отправленные", () -> {
            WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
            linkSent.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в отправленных");
        });

        step("Verify, что письмо появилось в папке «Тест»", () -> {
            WebElement navigationMenu = driver.findElement(By.cssSelector("span.sidebar__menu-ico"));
            navigationMenu.click();
            WebElement testFolder =
                driver.findElement(
                    By.xpath("//div[@class='nav__folder-name__txt' and contains(.,'Тест')]/ancestor::a"));
            driver.navigate().to(testFolder.getAttribute("href"));
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в папке Тест");
        });

        step("Verify контент, адресата и тему письма (должно совпадать с пунктом 3)", () -> {
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Тест " + dateNow + "')]"));
            message.get(0).click();
            WebElement editMessageReceiver = driver.findElement(By.cssSelector("span.letter-contact"));
            assertEquals(editMessageReceiver.getAttribute("Title"), "klyushkindimatest@mail.ru",
                "Адресат письма не совпадает");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("h2.thread-subject"));
            assertEquals(editMessageTitle.getText(), "Тест " + dateNow, "Тема письма не совпадает");
            WebElement editMessageText = driver.findElement(By.cssSelector("div.letter-body"));
            assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");
        });

        step("Выход", this::logout);
    }
}
