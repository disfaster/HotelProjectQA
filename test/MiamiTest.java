import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MiamiTest {

    private static WebDriver driver;
    private String EXPEDIA_URL = "https://www.expedia.com/hoteldeals";

    @BeforeClass
    public static void setUp(){
        driver = new ChromeDriver();
    }

    @Test
    public void miamiHotelTest(){
        driver.get(EXPEDIA_URL);
        WebElement destination = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/form/div[2]/div[1]/div/div[2]/div[1]/button"));
        destination.click();
        WebElement destinationText = driver.findElement(By.id("hotel-destination"));
        destinationText.sendKeys("Miami");
        WebElement city = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/form/div[2]/div[1]/div/div[1]/div/div/section/div/div[2]/div/ul/li[1]/div/button"));
    }
}
