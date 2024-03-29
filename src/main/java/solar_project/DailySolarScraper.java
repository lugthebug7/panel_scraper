package solar_project;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DailySolarScraper {
    public static void main(String[] args) throws IOException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-arm64/chromedriver");
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

            // Number of days you want to scrape
            int numberOfDaysToScrape = 1836;

            FileWriter fileWriter = new FileWriter("solar_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Loop to navigate through days and scrape data
            for (int i = 0; i < numberOfDaysToScrape; i++) {

                // Wait for the data element to be visible
                WebElement dateElement = new WebDriverWait(driver, 10)
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/div[2]/div[1]/div[2]/section/span")));

                // Extract the date text
                String dateText = dateElement.getText();
                
                // Wait for the panel list to be visible
                List<WebElement> panelItems = driver.findElements(By.cssSelector("article.panel-list-item-container"));

                // Iterate through each panel item  
                for (WebElement panelItem : panelItems) {
                    // Extract the serial number
                    String serialNumber = panelItem.findElement(By.cssSelector(".serial-number")).getText();
                    
                    // Extract the panel value (Total Energy in kWh)
                    String panelValue = panelItem.findElement(By.cssSelector(".panel-value")).getText();

                    bufferedWriter.write(serialNumber + "," + panelValue + "," + dateText);
                    bufferedWriter.newLine(); 
                }
            
                // Locate and click the left arrow (going back one day)
                WebElement leftArrow = driver.findElement(By.xpath("//div[@class='arrow']/img[contains(@src, 'left-arrow-light')]"));
                leftArrow.click();
            }
            bufferedWriter.close();

        } finally {
            // Close the browser once done
            driver.quit();
        }
    }
}






