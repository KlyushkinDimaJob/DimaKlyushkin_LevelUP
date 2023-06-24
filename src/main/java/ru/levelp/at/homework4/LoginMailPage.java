package ru.levelp.at.homework4;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginMailPage {
    protected static final String URL = "https://mail.ru";
    protected WebDriver driver;
    protected WebDriverWait wait;
    @FindBy(className = "ph-login")
    WebElement btnAuthorization;
    @FindBy(className = "ag-popup__frame__layout__iframe")
    WebElement frameAuthorization;
    @FindBy(name = "username")
    WebElement editLogin;
    @FindBy(css = "button[data-test-id='next-button']")
    WebElement btnInputPass;
    @FindBy(name = "password")
    WebElement editPass;
    @FindBy(css = "button[data-test-id='submit-button']")
    WebElement btnLogin;

    public LoginMailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.navigate().to(URL);
    }

    public void login(String username, String password) {
        btnAuthorization.click();
        driver.switchTo().frame(frameAuthorization);
        editLogin.sendKeys("klyushkindimatest");
        btnInputPass.click();
        editPass.sendKeys("autotest_1234");
        btnLogin.click();
    }
}
