package ru.levelp.at.homework5;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseMailPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    @FindBy(css = "img.ph-avatar-img")
    protected WebElement imgProfile;
    @FindBy(css = "span.sidebar__menu-ico")
    protected WebElement navigationMenu;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Отправленные')]")
    protected WebElement sentFolder;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Входящие')]")
    protected WebElement incomingFolder;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Письма себе')]")
    protected WebElement tomyselfFolder;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Черновики')]")
    protected WebElement draftFolder;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Корзина')]")
    protected WebElement trashFolder;
    @FindBy(xpath = "//div[@class='sidebar__full fn-enter']//div[@class='nav__folder-name__txt' "
        + "and contains(.,'Тест')]")
    protected WebElement testFolder;
    @FindBy(css = "div[data-testid='whiteline-account']")
    protected WebElement btnAccount;
    @FindBy(css = "div[data-testid='whiteline-account-exit']")
    protected WebElement btnExit;
    @FindBy(css = "a[title='Написать письмо']")
    protected WebElement btnNewMessage;
    @FindBy(css = "div.inputContainer--nsqFu > input")
    protected WebElement editMessageReceiver;
    @FindBy(css = "input[name='Subject']")
    protected WebElement editMessageTitle;
    @FindBy(css = "div.cke_editable_inline > div")
    protected WebElement editMessageText;
    @FindBy(css = "button[data-test-id='send']")
    protected WebElement btnSendMessage;
    @FindBy(css = "button[data-test-id='save']")
    protected WebElement btnSaveMessage;
    @FindBy(css = "button[title='Закрыть']")
    protected WebElement btnCloseMessage;
    @FindBy(xpath = "//span[@title='Закрыть']")
    protected WebElement infoSendMessage;
    @FindBy(css = "div[class='contactsContainer--3RMuQ'] span[class='text--1tHKB']")
    protected WebElement editTextMessageReceiver;
    @FindBy(css = "input[name='Subject']")
    protected WebElement editTextMessageTitle;
    @FindBy(css = "div.cke_editable_inline > div")
    protected WebElement editTextMessageText;

    public BaseMailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    public String getEmail() {
        return imgProfile.getAttribute("alt");
    }

    public void openSentFolder() {
        navigationMenu.click();
        sentFolder.click();
        wait.until(ExpectedConditions.titleContains("Отправленные"));
    }

    public void openIncomingFolder() {
        navigationMenu.click();
        incomingFolder.click();
        wait.until(ExpectedConditions.titleContains("Входящие"));
    }

    public void openTomyselfFolder() {
        navigationMenu.click();
        tomyselfFolder.click();
        wait.until(ExpectedConditions.titleContains("Письма себе"));
    }

    public void openDraftFolder() {
        navigationMenu.click();
        draftFolder.click();
        wait.until(ExpectedConditions.titleContains("Черновики"));
    }

    public void openTrashFolder() {
        navigationMenu.click();
        trashFolder.click();
        wait.until(ExpectedConditions.titleContains("Корзина"));
    }

    public void openTestFolder() {
        navigationMenu.click();
        testFolder.click();
        wait.until(ExpectedConditions.titleContains("Тест"));
    }

    public void logout() {
        btnAccount.click();
        btnExit.click();
    }

    public void openNewMessage() {
        btnNewMessage.click();
    }

    public void fillTitleMessage(String text) {
        editMessageTitle.sendKeys(text);
    }

    public void fillReceiverMessage(String text) {
        editMessageReceiver.sendKeys(text);
    }

    public void fillTextMessage(String text) {
        editMessageText.sendKeys(text);
    }

    public String getTitleMessage() {
        return editTextMessageTitle.getAttribute("value");
    }

    public String getReceiverMessage() {
        return editTextMessageReceiver.getText();
    }

    public String getTextMessage() {
        return editTextMessageText.getText();
    }

    public void sendMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSendMessage)).click();
    }

    public void saveMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveMessage)).click();
    }

    public void closeMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCloseMessage)).click();
    }

    public void closeInfoSendMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(infoSendMessage)).click();
    }
}
