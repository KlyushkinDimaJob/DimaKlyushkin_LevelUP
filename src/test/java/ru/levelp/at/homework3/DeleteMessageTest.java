package ru.levelp.at.homework3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.levelp.at.utils.SleepUtils.sleep;

import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteMessageTest {
    private WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void testDeleteMessage() {
        //Войти в почту
        driver.navigate().to("https://mail.ru/");
        WebElement btnAuthorization = driver.findElement(By.className("ph-login"));
        btnAuthorization.click();

        sleep(1000);
        WebElement frameAuthorization = driver.findElement(By.className("ag-popup__frame__layout__iframe"));
        driver.switchTo().frame(frameAuthorization);

        sleep(1000);
        WebElement editLogin = driver.findElement(By.name("username"));
        WebElement btnInputPass = driver.findElement(By.cssSelector("button[data-test-id='next-button']"));
        editLogin.sendKeys("klyushkindimatest");
        btnInputPass.click();

        sleep(1000);
        WebElement editPass = driver.findElement(By.name("password"));
        WebElement btnLogin = driver.findElement(By.cssSelector("button[data-test-id='submit-button']"));
        editPass.sendKeys("autotest_1234");
        btnLogin.click();
        sleep(5000);

        //Assert, что вход выполнен успешно
        assertTrue(driver.getTitle().contains("Входящие - Почта Mail.ru"), "Вход в почту не выполнен");

        //Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        WebElement btnNewMessage = driver.findElement(By.cssSelector("a[title='Написать письмо']"));
        btnNewMessage.click();
        sleep(1000);
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
        sleep(500);

        //Verify, что письмо появилось в папке Входящие
        driver.navigate().to("https://e.mail.ru/tomyself/");
        sleep(5000);
        var message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в папке Входящие");

        //Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        message.get(0).click();
        sleep(1000);
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
        sleep(1000);

        //Verify что письмо появилось в папке Корзина
        driver.navigate().to("https://e.mail.ru/trash/");
        sleep(5000);
        message =
            driver.findElements(By.xpath("//span[@class='ll-sj__normal' and contains(.,'Типо тема " + dateNow + "')]"));
        assertEquals(message.size(), 1, "Письмо не найдено в корзине");

        //Выйти из учётной записи
        WebElement btnAccount = driver.findElement(By.cssSelector("div[data-testid='whiteline-account']"));
        btnAccount.click();
        sleep(1000);
        WebElement btnExit = driver.findElement(By.cssSelector("div[data-testid='whiteline-account-exit']"));
        btnExit.click();
    }
}
