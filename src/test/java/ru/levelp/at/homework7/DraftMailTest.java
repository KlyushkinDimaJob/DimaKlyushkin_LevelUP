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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

@Epic("Черновик")
@Story("Создание черновика и его отправка")
public class DraftMailTest extends MailTestBase {
    @Test(description = "Проверка создания черновика")
    @Feature("Успешное создание черновика")
    public void testDraftMessage() {
        LocalDateTime dateNow = LocalDateTime.now();

        step("Создать новое письмо (заполнить адресата, тему письма и тело)", () -> {
            WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
            btnNewMessage.click();
            WebElement editMessageReceiver = driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
            editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));

            editMessageTitle.sendKeys("Типо тема " + dateNow);
            WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
            editMessageText.sendKeys("Типо текст письма");
        });

        step("Сохранить его как черновик", () -> {
            WebElement btnSaveMessage = driver.findElement(By.cssSelector("button[data-test-id='save']"));
            btnSaveMessage.click();
        });

        step("Verify, что письмо сохранено в черновиках", () -> {
            var notifyDraft = driver.findElements(
                By.xpath("//span[@class='notify__message__text' and contains(.,'Сохранено в черновиках')]"));
            assertTrue(notifyDraft.size() > 0, "Письмо не сохранено в черновики");
            WebElement infoSendMessage = driver.findElement(By.xpath("//button[@title='Закрыть']"));
            infoSendMessage.click();
            WebElement linkDrafts = driver.findElement(By.cssSelector("a.nav__item[title='Черновики']"));
            linkDrafts.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не сохранено в черновики");
        });

        step("Verify контент, адресата и тему письма (должно совпадать с пунктом 3)", () -> {
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            message.get(0).click();
            WebElement editMessageReceiver =
                driver.findElement(By.cssSelector("div[class='contactsContainer--3RMuQ'] span[class='text--1tHKB']"));
            assertEquals(editMessageReceiver.getText(), "klyushkindimatest@mail.ru", "Адресат письма не совпадает");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
            assertEquals(editMessageTitle.getAttribute("value"), "Типо тема " + dateNow, "Тема письма не совпадает");
            WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
            assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");
        });

        step("Отправить письмо", () -> {
            WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
            btnSendMessage.click();
            WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
            infoSendMessage.click();
        });

        step("Verify, что письмо исчезло из черновиков", () -> {
            boolean deleteDraftMessage = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]")));
            assertTrue(deleteDraftMessage, "Письмо осталось в черновиках");
        });

        step("Verify, что письмо появилось в папке отправленные", () -> {
            WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
            linkSent.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в отправленных");
        });

        step("Выход", this::logout);
    }

    @Test(description = "Проверка создания черновика")
    @Feature("Не успешное создание черновика")
    public void testDraftMessageFailed() {
        LocalDateTime dateNow = LocalDateTime.now();

        step("Создать новое письмо (заполнить адресата, тему письма и тело)", () -> {
            WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
            btnNewMessage.click();
            WebElement editMessageReceiver = driver.findElement(By.cssSelector("div.inputContainer--nsqFu > input"));
            editMessageReceiver.sendKeys("klyushkindimatest@mail.ru");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));

            editMessageTitle.sendKeys("Типо тема " + dateNow);
            WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
            editMessageText.sendKeys("Типо текст письма");
        });

        step("Сохранить его как черновик", () -> {
            WebElement btnSaveMessage = driver.findElement(By.cssSelector("button[data-test-id='save']"));
            btnSaveMessage.click();
        });

        step("Verify, что письмо сохранено в черновиках", () -> {
            var notifyDraft = driver.findElements(
                By.xpath("//span[@class='notify__message__text' and contains(.,'Сохранено в черновиках')]"));
            assertTrue(notifyDraft.size() > 0, "Письмо не сохранено в черновики");
            WebElement infoSendMessage = driver.findElement(By.xpath("//button[@title='Закрыть']"));
            infoSendMessage.click();
            WebElement linkDrafts = driver.findElement(By.cssSelector("a.nav__item[title='Черновики']"));
            linkDrafts.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не сохранено в черновики");
        });

        step("Verify контент, адресата и тему письма (должно совпадать с пунктом 3)", () -> {
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            message.get(0).click();
            WebElement editMessageReceiver =
                driver.findElement(By.cssSelector("div[class='contactsContainer--3RMuQ'] span[class='text--1tHKB']"));
            assertEquals(editMessageReceiver.getText(), "klyushkindimatest@mail.ru", "Адресат письма не совпадает");
            WebElement editMessageTitle = driver.findElement(By.cssSelector("input[name='Subject']"));
            // TODO: 10.07.2023 для ошибки проверки темы. было("Типо тема " + dateNow), стало("Типо пусто " + dateNow)
            assertEquals(editMessageTitle.getAttribute("value"), "Типо пусто " + dateNow, "Тема письма не совпадает");
            WebElement editMessageText = driver.findElement(By.cssSelector("div.cke_editable_inline > div"));
            assertTrue(editMessageText.getText().contains("Типо текст письма"), "Текст письма не совпадает");
        });

        step("Отправить письмо", () -> {
            WebElement btnSendMessage = driver.findElement(By.cssSelector("button[data-test-id='send']"));
            btnSendMessage.click();
            WebElement infoSendMessage = driver.findElement(By.xpath("//span[@title='Закрыть']"));
            infoSendMessage.click();
        });

        step("Verify, что письмо исчезло из черновиков", () -> {
            boolean deleteDraftMessage = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]")));
            assertTrue(deleteDraftMessage, "Письмо осталось в черновиках");
        });

        step("Verify, что письмо появилось в папке отправленные", () -> {
            WebElement linkSent = driver.findElement(By.cssSelector("a.nav__item[title='Отправленные']"));
            linkSent.click();
            var message =
                driver.findElements(
                    By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
            assertEquals(message.size(), 1, "Письмо не найдено в отправленных");
        });

        step("Выход", this::logout);
    }
}
