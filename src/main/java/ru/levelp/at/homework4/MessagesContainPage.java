package ru.levelp.at.homework4;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.levelp.at.homework5.BaseMailPage;

public class MessagesContainPage extends BaseMailPage {
    @FindBy(xpath = "//span[@class='ll-sj__normal']")
    private List<WebElement> messages;
    private String messageXpath = "//span[@class='ll-sj__normal']";

    public MessagesContainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isExistMessage(String title) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(messageXpath), title));
    }

    public boolean isNotExistMessage(String title) {
        return wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(messageXpath), title));
    }

    public void openMessage(String title) {
        messages.stream().filter(x -> x.getText().contains(title)).findFirst()
                .ifPresentOrElse(WebElement::click,
                    () -> {
                        throw new IllegalArgumentException("Не найдено письмо с переданной темой: " + title);
                    });
    }
}
