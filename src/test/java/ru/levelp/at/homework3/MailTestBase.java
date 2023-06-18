package ru.levelp.at.homework3;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class MailTestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    protected void beforeMethod() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        authorization();
    }

    @AfterMethod
    protected void afterMethod() {
        logout();
        driver.quit();
    }

    protected void authorization() {
        //Войти в почту
        driver.navigate().to("https://mail.ru/");
        WebElement btnAuthorization = driver.findElement(By.className("ph-login"));
        btnAuthorization.click();

        WebElement frameAuthorization = driver.findElement(By.className("ag-popup__frame__layout__iframe"));
        driver.switchTo().frame(frameAuthorization);

        WebElement editLogin = driver.findElement(By.name("username"));
        WebElement btnInputPass = driver.findElement(By.cssSelector("button[data-test-id='next-button']"));
        editLogin.sendKeys("klyushkindimatest");
        btnInputPass.click();

        WebElement editPass = driver.findElement(By.name("password"));
        WebElement btnLogin = driver.findElement(By.cssSelector("button[data-test-id='submit-button']"));
        editPass.sendKeys("autotest_1234");
        btnLogin.click();

        //Assert, что вход выполнен успешно
        WebElement imgProfile = driver.findElement(By.cssSelector("img.ph-avatar-img"));
        assertEquals(imgProfile.getAttribute("alt"), "klyushkindimatest@mail.ru", "Вход в почту не выполнен");
    }

    protected void logout() {
        //Выйти из учётной записи
        WebElement btnAccount = driver.findElement(By.cssSelector("div[data-testid='whiteline-account']"));
        btnAccount.click();
        WebElement btnExit = driver.findElement(By.cssSelector("div[data-testid='whiteline-account-exit']"));
        btnExit.click();
    }
}
