import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@RunWith(JUnitParamsRunner.class)
public class HoustonTest {

    private static WebDriver driver;
    private String GOOGLE_URL = "https://www.google.com/travel/";

    private static Connection connection;


    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:prices.sqlite");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @Test
    @Parameters({"Houston Hilton", "Houston Marriott", "Aloft Houston by the Galleria", "Holiday Inn Houston Downtown", "Comfort Inn & Suites SW Houston Sugarland"})

    public void HoustonHotelTest(String location) throws InterruptedException {
        Integer price = null; // Declare and initialize price outside try block

        try{
        driver.get(GOOGLE_URL);
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        WebElement searchBox = null;
        for (WebElement temp : inputs) {
            if (temp.isDisplayed() && temp.getAttribute("placeholder").equals("Search for flights, hotels and more")) {
                searchBox = temp;
                break;
            }
        }
        //searchBox.sendKeys("New York Hilton");
        //searchBox.sendKeys("Las Vegas Hilton");
        searchBox.sendKeys(location);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        //clicking the first option
        driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div[1]/div[1]/div[2]/div/div/div/div[2]/div[3]/ul/li[1]/span[2]/span[1]")).click();
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
        LocalDate endDate = LocalDate.of(2024, 6, 1);
        while (!startDate.equals(LocalDate.of(2024, 6, 1))) {
//            String startDateStr = startDate.format(formatter);
//            String endDateStr = startDate.plusDays(1).format(formatter);
            //getPrice
            Thread.sleep(Duration.ofSeconds(2));
            WebElement getPrice = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[2]/c-wiz/div[1]/div/div[1]/div[2]/span/div[1]/div/div/div/div/a/div[1]/div[2]/span/span/span/span[2]"));
            price = Integer.parseInt(getPrice.getText().replace("$", "").replace(",", ""));

            //print price
            System.out.println("Price: $" + price);
            //print date


            //save price to database
            Thread.sleep(2500);
            insertToDBGoogleFlights(location, startDate.toString(), price);


            //go next date
            startDate = startDate.plusDays(1);
            driver.findElement(By.xpath("/html/body/c-wiz[2]/div/c-wiz/div[1]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div[2]/span[1]/c-wiz[1]/c-wiz[3]/div/section/div[1]/div[1]/div/div[2]/div[1]/div/div[2]/button")).click();
        }
} catch (NoSuchElementException e) {
    // Handle the NoSuchElementException here
    System.err.println("Element not found: " + e.getMessage());
    // Optionally, you can take appropriate action such as logging or retrying
    price = null; // Set price to null

} catch (NumberFormatException e) {
    // Handle the NumberFormatException here
    System.err.println("Error parsing price: " + e.getMessage());
    // Optionally, you can take appropriate action such as logging or retrying
    price = null; // Set price to null

}



    }

    public void insertToDBGoogleFlights(String destination, String startDate, int price) {

        String sql = "insert into miamiHotels(destination, startDate, price) values (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, destination);
            ps.setString(2, startDate);
            ps.setInt(3, price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getCheapestFlights() {
        String sql = "SELECT destination, startDate, endDate, price FROM hotel WHERE (destination, price) " +
                "IN (SELECT destination, MIN(price) AS minPrice FROM hotel GROUP BY destination)";
        ResultSet cheapestFlights = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cheapestFlights = rs;

                // Prints the cheapest flights queried result set
                System.out.println("Destination: " + cheapestFlights.getString("destination") +
                        ", Start_Date: " + cheapestFlights.getString("startDate") +
                        ", Price: " + cheapestFlights.getString("price"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void cleanUp()throws SQLException {
        driver.close();
        connection.close();
    }
}


