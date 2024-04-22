import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

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


    @Test
    public void testGetARoom() throws Exception {
        driver.manage().window().setSize(new Dimension(600,600));
        driver.get("https://www.getaroom.com");
        WebElement destination = driver.findElement(By.cssSelector("#destination"));
        destination.clear();
        destination.sendKeys("Las Vegas");
        destination.click();
        WebElement submit = driver.findElement(By.cssSelector("#enter-travel-dates"));submit.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //driver.get("https://www.getaroom.com/search?amenities=&destination=Paris&page=1&per_page=25&rinfo=%5B%5B18%5D%5D&sort_order=position&hide_unavailable=true&check_in=2024-05-13&check_out=2024-05-15&property_name=");
        WebElement filterBtn = driver.findElement(By.cssSelector("#toggle_filters_btn"));
        filterBtn.click();
        WebElement hotelName = driver.findElement(By.cssSelector("#hotelName"));
        hotelName.sendKeys("hilton");

    }
}
