import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class MiamiTest {

    private static WebDriver driver;
    private String GOOGLE_URL = "https://www.google.com/travel/";

    @BeforeClass
    public static void setUp(){
        driver = new ChromeDriver();
    }

    @Test
    public void miamiHotelTest() throws Exception{
        driver.get(GOOGLE_URL);
        WebElement destinationBox = driver.findElement(By.cssSelector(".cQnuXe > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > input:nth-child(1)"));
        destinationBox.click();
        WebElement destinationText = driver.findElement(By.cssSelector(".ZGEB9c > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > input:nth-child(1)"));
        destinationText.sendKeys("Miami Hilton");
        destinationText.sendKeys(Keys.ENTER);
        Thread.sleep(Duration.ofSeconds(5));
        WebElement dropDown = driver.findElement(By.cssSelector(".qFroOe > div:nth-child(1) > div:nth-child(1)"));
        dropDown.click();
        WebElement minusButton = driver.findElement(By.cssSelector("div.MlZqJf:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1) > button:nth-child(1)"));
        minusButton.click();
        WebElement doneButton = driver.findElement(By.cssSelector(".TkZUKc"));
        doneButton.click();
        Thread.sleep(Duration.ofSeconds(3));
        WebElement seePrices = driver.findElement(By.cssSelector(".K1smNd > c-wiz:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > button:nth-child(1)"));
        seePrices.click();
        Thread.sleep(Duration.ofSeconds(5));
        WebElement dates = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[2]/c-wiz[1]/c-wiz/div/div/div/div/div/div/div/section/div[1]/div[1]/div/div[2]/div[1]"));
        dates.click();
        
    }
}
