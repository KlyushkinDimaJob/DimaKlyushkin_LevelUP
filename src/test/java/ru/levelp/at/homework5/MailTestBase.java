package ru.levelp.at.homework5;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.levelp.at.Util;
import ru.levelp.at.homework5.steps.FolderMessageSteps;
import ru.levelp.at.homework5.steps.PageMessageSteps;
import ru.levelp.at.homework5.steps.WindowMessageSteps;

public abstract class MailTestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties properties;
    protected WindowMessageSteps windowMessageSteps;
    protected PageMessageSteps pageMessageSteps;
    protected FolderMessageSteps folderMessageSteps;

    @BeforeMethod(groups = {"hw5"})
    protected void beforeMethod() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        properties = Util.loadProperties("homework5/user.properties");
        windowMessageSteps = new WindowMessageSteps(driver);
        pageMessageSteps = new PageMessageSteps(driver);
        folderMessageSteps = new FolderMessageSteps(driver);
        authorization();
    }

    @AfterMethod(groups = {"hw5"})
    protected void afterMethod() {
        logout();
        driver.quit();
    }

    protected void authorization() {
        //Войти в почту
        LoginMailPage loginMailPage = new LoginMailPage(driver);
        loginMailPage.open();
        loginMailPage.login(properties.getProperty("username"), properties.getProperty("password"));

        //Assert, что вход выполнен успешно
        BaseMailPage baseMailPage = new BaseMailPage(driver);
        assertEquals(baseMailPage.getEmail(), properties.getProperty("email"), "Вход в почту не выполнен");
    }

    protected void logout() {
        //Выйти из учётной записи
        BaseMailPage baseMailPage = new BaseMailPage(driver);
        baseMailPage.logout();
    }
}
