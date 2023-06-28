package ru.levelp.at.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MessagePage extends BaseMailPage {
    @FindBy(css = "span.letter-contact")
    protected WebElement messageReceiver;
    @FindBy(css = "h2.thread-subject")
    protected WebElement messageTitle;
    @FindBy(css = "div.letter-body")
    protected WebElement messageText;
    @FindBy(xpath = "//div[@class='button2__txt' and contains(.,'Удалить')]")
    protected WebElement btnDel;

    public MessagePage(WebDriver driver) {
        super(driver);
    }

    public String getMessageReceiver() {
        return messageReceiver.getAttribute("Title");
    }

    public String getMessageTitle() {
        return messageTitle.getText();
    }

    public String getMessageText() {
        return messageText.getText();
    }

    public void delMessage() {
        btnDel.click();
    }
}
