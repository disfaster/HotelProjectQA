import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class MiamiTest {

    private static WebDriver driver;
    private String GOOGLE_URL = "https://www.google.com/travel/";

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void miamiHotelTest() {
        driver.get(GOOGLE_URL);
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println(inputs.size());
        WebElement searchBox = null;
        for (WebElement temp : inputs) {
            if (temp.isDisplayed() && temp.getAttribute("placeholder").equals("Search for flights, hotels and more")) {
                searchBox = temp;
            }
        }
        searchBox.sendKeys("New York Hilton");
        searchBox.submit();
//        WebElement hotelButton = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div[1]/div[1]/div[2]/div/div/div/div[1]/div/div/div[2]/input"));
//        hotelButton.click();
//        WebElement destinationText = driver.findElement(By.id("hotel-destination"));
//        destinationText.sendKeys("Miami");
//        WebElement city = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/form/div[2]/div[1]/div/div[1]/div/div/section/div/div[2]/div/ul/li[1]/div/button"));
        }
    }
