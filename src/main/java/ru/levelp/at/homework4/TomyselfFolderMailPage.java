package ru.levelp.at.homework4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class TomyselfFolderMailPage extends BaseMailPage {

    @FindBy(xpath = "//span[@class='ll-sj__normal']")
    private List<WebElement> messages;

    public TomyselfFolderMailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isExistMessage(String title) {
        return messages.stream().anyMatch(x -> x.getText().contains(title));
    }

    public void openMessage(String title) {
        wait.withTimeout(Duration.ofSeconds(1));
        messages.stream().filter(x -> x.getText().contains(title)).findFirst()
                .ifPresentOrElse(WebElement::click,
                    () -> {
                        throw new IllegalArgumentException("Не найдено письмо с переданной темой: " + title);
                    });
    }
}