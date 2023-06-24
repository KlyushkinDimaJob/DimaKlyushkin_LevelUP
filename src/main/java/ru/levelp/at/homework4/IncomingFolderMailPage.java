package ru.levelp.at.homework4;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IncomingFolderMailPage extends BaseMailPage {

    @FindBy(xpath = "//span[@class='ll-sj__normal']")
    private List<WebElement> messages;

    public IncomingFolderMailPage(WebDriver driver) {
        super(driver);
    }
}
