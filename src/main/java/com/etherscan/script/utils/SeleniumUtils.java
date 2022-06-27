package com.etherscan.script.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

@Slf4j
public class SeleniumUtils
{
    public static String findUrl(WebDriver driver, Integer rowNumber) {
        WebElement element = null;
        try
        {
            driver.findElement(By.partialLinkText("Expand")).click();
            Thread.sleep(3000);
            driver.findElement(By.name("input_" + rowNumber)).sendKeys("30");
            driver.findElement(By.id("btn_" + rowNumber)).click();
            Thread.sleep(2000);
            element = driver.findElement(By.id("myanswer_" + rowNumber));
        }
        catch (Exception e)
        {
            log.error("WebDriver ERROR:", e);
        }
        return element == null ? "" : UrlUtils.extract(element.getText()).get(0);
    }

    public static WebDriver create(String remoteWebDriverUrl) {
        ChromeOptions chromeOptions = new ChromeOptions();
        try
        {
            return new RemoteWebDriver(new URL(remoteWebDriverUrl), chromeOptions);
        }
        catch (Exception e)
        {
            log.error("Error creating web driver", e);
            return null;
        }
    }
}
