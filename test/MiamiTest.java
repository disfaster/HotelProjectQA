import junitparams.Parameters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MiamiTest {

    private static WebDriver driver;
    private String GOOGLE_URL = "https://www.google.com/travel/";

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

    }

    @Test
    @Parameters({L})
    public void miamiHotelTest() throws InterruptedException {
        driver.get(GOOGLE_URL);
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println(inputs.size());
        WebElement searchBox = null;
        for (WebElement temp : inputs) {
            if (temp.isDisplayed() && temp.getAttribute("placeholder").equals("Search for flights, hotels and more")) {
                searchBox = temp;
            }
        }
        //searchBox.sendKeys("New York Hilton");
        searchBox.sendKeys("Las Vegas Hilton");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        //clicking the first option
        driver.findElement(By.xpath("//*[@id=\"h0T7hb-48-51\"]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        //opens the date page
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div/div[2]/div[1]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        //changing date
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[1]/div/input")).sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[1]/div/input")).sendKeys("may 1");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[1]/div/input")).sendKeys(Keys.ENTER);
        //clicking done
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div[2]/div/div[2]/div[4]/div[2]/button[2]")).click();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        while (!startDate.equals(LocalDate.of(2024, 5, 3))) {
            String startDateStr = startDate.format(formatter);
            String endDateStr = startDate.plusDays(1).format(formatter);
            //getPrice
            Thread.sleep(Duration.ofSeconds(2));
            WebElement getPrice = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[2]/c-wiz/div[1]/div/div[1]/div[2]/span/div[1]/div/div/div/div/a/div[1]/div[2]/span/span/span/span[2]"));
            int price = Integer.parseInt(getPrice.getText().replace("$","").replace(",",""));

            //print
            System.out.println("Price: $" + price);
            //save price to database
            Thread.sleep(Duration.ofSeconds(2));

            //go next date
            startDate = startDate.plusDays(1);
            driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div/div[2]/div[1]/div/div[2]/button")).click();

        }







        //List<WebElement> PricesElement = driver.findElements(By.cssSelector(".CylAxb.n3qw7.UNMzKf.julK3b"));
        //List<String> prices = new ArrayList<>();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

//        for (WebElement element : PricesElement) {
//            String price = element.getText();
//            prices.add(price);
//        }
//        System.out.println(prices);

        //driver.findElement(By.xpath("//*[@id=\"dwrFZd0\"]/div[2]/div[1]/div/input")).click();

        }
    }


//        WebElement hotelButton = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div[1]/div[1]/div[2]/div/div/div/div[1]/div/div/div[2]/input"));
//        hotelButton.click();
//        WebElement destinationText = driver.findElement(By.id("hotel-destination"));
//        destinationText.sendKeys("Miami");
//        WebElement city = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/form/div[2]/div[1]/div/div[1]/div/div/section/div/div[2]/div/ul/li[1]/div/button"));