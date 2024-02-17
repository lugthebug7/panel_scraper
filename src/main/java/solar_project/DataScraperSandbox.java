package solar_project;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class DataScraperSandbox {

    public static void main(String[] args) throws IOException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "/Users/kaidenjones/Downloads/chromedriver-mac-x64/chromedriver");
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        try {
            //Go to MySunPower login page
            driver.get("https://login.mysunpower.com/login/login.htm");

            // Input Professor Weinberg's login credentials
            driver.findElement(By.id("okta-signin-username")).sendKeys("aarondweinberg@gmail.com");
            driver.findElement(By.id("okta-signin-password")).sendKeys("QM%d6K7&r61k");

            // Click the login button
            driver.findElement(By.id("okta-signin-submit")).click();

            // Wait for login to complete (explicit wait)
            new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe("https://sds.mysunpower.com/monitor/"));

            // Navigate to the panels page
            driver.get("https://sds.mysunpower.com/monitor/panels");



            // starting the test to see if hover works



            // Wait for the data element to be visible
             WebElement dateElement = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/div[1]/div[2]/section/span")));

            // Extract the date text
            String dateText = dateElement.getText();

            System.out.println("----------");
            System.out.println("Days date: " + dateText);
            System.out.println("----------");
            
            // setting element of test panel
            WebElement panelItem = driver.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/div[3]/section[3]/article[1]"));
            //printing out panelItem info
            String panelItemInfo = driver.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/div[3]/section[3]/article[1]")).getText();

            System.out.println("All panel items info: " + panelItemInfo);
            System.out.println("----------");

            // collecting serial num of test panel
            String serialNumber = panelItem.findElement(By.cssSelector(".serial-number")).getText();

            System.out.println("Panel serial nember: " + serialNumber);
            System.out.println("----------");

            // Clicks into panel to see hourly data
            panelItem.click();

            System.out.println("clicked into panel");
            System.out.println("----------");

            // Waiting for side panel to come out
            WebDriverWait wait = new WebDriverWait(driver, 10);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/aside")));
            // getting the bar element item
            List<WebElement> rechartItems = driver.findElements(By.cssSelector("article.recharts-bar-rectangle"));
            // WebElement rechartItem = driver.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/aside/div/div[2]/div[7]/div[1]/svg/g[4]/g/g/g[10]"));

            System.out.println("after wait for side panel to come out");
            System.out.println("----------");

            // moves to 
            for (WebElement rechartItem : rechartItems){

                System.out.println("Starting loop");
                System.out.println("----------");

                Actions action = new Actions(driver);
                action.moveToElement(rechartItem).perform();

                System.out.println("After moving to bar item");
                System.out.println("----------");

                // WebDriverWait wait = new WebDriverWait(driver, 10);
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/section/div/div[2]/aside/div/div[2]/div[7]/div[1]/svg/g[5]")));

                System.out.println("end of loop ");
                System.out.println("----------");
            }

            System.out.println("end of code ");
            System.out.println("----------");
            
        }
        finally{}
    }
}












