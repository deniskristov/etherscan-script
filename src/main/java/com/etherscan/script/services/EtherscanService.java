package com.etherscan.script.services;

import com.etherscan.script.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Component
public class EtherscanService
{
    private WebDriver driver;

    @PostConstruct
    public void init()
    {
        ChromeOptions chromeOptions = new ChromeOptions();
        try
        {
            driver = new RemoteWebDriver(new URL("http://selenium:4444"), chromeOptions);
        }
        catch (Exception e)
        {
            System.out.println("Exception" + e.toString());
            log.error("Error creating web driver", e);
        }
    }

    public String extractUrl(int rowNumber)
    {
        log.info("Started etherscan parsing");
        long start = System.currentTimeMillis();
        driver.get("https://etherscan.io/readContract?m=normal&a=0x34d85c9CDeB23FA97cb08333b511ac86E1C4E258&v=0x34d85c9CDeB23FA97cb08333b511ac86E1C4E258");
        driver.findElement(By.partialLinkText("Expand")).click();
        driver.findElement(By.name("input_" + rowNumber)).sendKeys(String.valueOf(rowNumber));
        driver.findElement(By.id("btn_" + rowNumber)).click();
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        WebElement element = driver.findElement(By.id("myanswer_" + rowNumber));
        log.info("Completed etherscan parsing. Duration: " + duration + " ms");
        return UrlUtils.extract(element.getText()).get(0);
    }
}
