package de.nerdis.apis;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class WebPageScreenshotTaker {

    private WebDriver driver;

    public WebPageScreenshotTaker() {
        this.driver = initDriver();
    }

    private WebDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(-2000, 0));

        return driver;
    }

    public File capture(String url) {
        this.driver.get(url);
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
    }

    public void destroy() {
        this.driver.close();
    }
}
