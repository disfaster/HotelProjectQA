import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebElement;

import javax.print.attribute.standard.Destination;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(JUnitParamsRunner.class)
public class FlightPriceTest {

    //private static final String DB_LOCATION = "jdbc:sqlite:flights.sqlite";
    private static final String GOOGLE_FLIGHTS_URL = "https://www.google.com/travel/flights?tfs=CBwQARopEgoyMDI0LTA1LTAxagwIAhIIL20vMDEzeXFyDQgDEgkvbS8wMXE5OG0aKRIKMjAyNC0wNS0wN2oNCAMSCS9tLzAxcTk4bXIMCAISCC9tLzAxM3lxQAFIAXABggELCP___________wGYAQE";
    private static WebDriver driver;
    private static Connection connection;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:flights.sqlite");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @Test
    //@Parameters({"Cancun"})
    @Parameters({"Cancun", "Las Vegas", "Denver", "Rome", "Milan", "Paris", "Madrid", "Amsterdam", "Singapore"})
    public void googleSearchExample(String endLocation){
        try {
            //Connects to FireFox and finds the indicated fields on the website Google Flights.
            driver.get("https://www.google.com/travel/flights");

            // Set the end location
            WebElement toLocationText = driver.findElement(By.cssSelector("div.WKeVIb:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(1)"));
            toLocationText.clear();
            toLocationText.sendKeys(endLocation);
            Thread.sleep(500);
            WebElement toLocationSelect = driver.findElement(By.id("c2"));
            toLocationSelect.click();

            // Set up the dates
            // DateTimeFormatter info:
            // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
            //  https://www.baeldung.com/java-datetimeformatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
            LocalDate startDate = LocalDate.of(2024, 5, 1);
            LocalDate endDate = LocalDate.of(2024, 8, 15);

            while (!startDate.equals(LocalDate.of(2024, 8, 10))) {
                String startDateStr = startDate.format(formatter);
                String endDateStr = startDate.plusDays(6).format(formatter);

                try {
                    // Set the first date
                    WebElement departureDateSelector = driver.findElement(By.cssSelector(".uNiB1 > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(2)"));
                    departureDateSelector.click();
                    departureDateSelector.sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
                    Thread.sleep(150);

                    WebElement departureDate = driver.findElement(By.cssSelector(".X4feqd > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(2)"));
                    departureDate.sendKeys(startDateStr);
                    Thread.sleep(150);

                    // Set the second date
                    WebElement returnDate = driver.findElement(By.cssSelector(".X4feqd > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > input:nth-child(1)"));
                    returnDate.sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
                    returnDate.sendKeys(endDateStr, Keys.ENTER);
                    Thread.sleep(150);

                    // Confirm the dates
                    WebElement dateDoneButton = driver.findElement(By.cssSelector(".rtW97"));
                    dateDoneButton.click();

                    if (startDateStr.equals("May 1")) {
                        // Submit
                        WebElement submitQueryButton = driver.findElement(By.cssSelector(".TUT4y"));
                        submitQueryButton.sendKeys(Keys.ENTER);
                        Thread.sleep(150);

                        // nonstop flight
                        WebElement filterFlightButton = driver.findElement(By.cssSelector("div.cwYgqc:nth-child(1) > span:nth-child(1) > button:nth-child(1)"));
                        filterFlightButton.click();
                        Thread.sleep(150);
                        WebElement nonstopClick = driver.findElement(By.cssSelector("div.m76nmf:nth-child(2)"));

                        // check to see if nonstop is an option if not close the menu
                        try {
                            nonstopClick.click();
                            Thread.sleep(150);
                        } catch (Exception e) {
                            WebElement closeFilter = driver.findElement(By.cssSelector(".HJuSVb"));
                            closeFilter.click();
                            Thread.sleep(150);
                            return;
                        }
                        Thread.sleep(150);
                        WebElement closeFilter = driver.findElement(By.cssSelector(".HJuSVb"));
                        closeFilter.click();
                        Thread.sleep(150);
                    }
                    Thread.sleep(150);

                    // get the flight price
                    WebElement getPrice = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[2]/div[3]/ul/li[1]/div/div[2]/div/div[2]/div[6]/div[1]/div[2]/span"));
                    int price = Integer.parseInt(getPrice.getText().replace("$", "").replace(",", ""));

                    // print everything
                    System.out.println("End Location: " + endLocation +
                            ", Departure Date: " + startDateStr +
                            ", Return Date: " + endDateStr +
                            ", Price: $" + price);

                    //send all data to the flights DB
                    insertToDBGoogleFlights(endLocation, startDateStr, endDateStr, price);

                    Thread.sleep(150);
                } catch (Exception e) {
                    try {
                        // if first xpath fails for the price try the next one
                        WebElement getPriceAlternative = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[2]/c-wiz/div[1]/c-wiz/div[2]/div[2]/div[4]/ul/li[1]/div/div[2]/div/div[2]/div[6]/div[1]/div[2]/span"));
                        int price = Integer.parseInt(getPriceAlternative.getText().replace("$", "").replace(",", ""));

                        // print everything
                        System.out.println("End Location: " + endLocation +
                                ", Departure Date: " + startDateStr +
                                ", Return Date: " + endDateStr +
                                ", Price: $" + price);

                        //send all data to the flights DB
                        insertToDBGoogleFlights(endLocation, startDateStr, endDateStr, price);

                        Thread.sleep(150);
                    } catch (Exception ex) {
                        System.out.println("Both XPath failed");
                    }
                }
                // increments the date by 1 day
                startDate = startDate.plusDays(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Part 1 is not working.");
        }
    }

    public void insertToDBGoogleFlights(String destination, String startDate, String endDate, int price) {

        String sql = "insert into flights (destination, startDate, endDate, price) values (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, destination);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ps.setInt(4, price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // May be needed to handle null data at another time:

    // if (vacationDestination != null)
    //                destination = vacationDestination.getText();
    //            else
    //                destination = "Bermuda Triangle";
    //            if (vacationStartDate != null) {
    //                startDate = vacationStartDate.getText();
    //            } else {
    //                startDate = "??/??/????";
    //            }
    //            if (vacationEndDate != null) {
    //                endDate = vacationEndDate.getText();
    //            } else {
    //                endDate = "??/??/????";
    //            }
    //            if (vacationPrice != null)
    //                price = Integer.parseInt(vacationPrice.getText().replace("$","").replace(",",""));
    //            else
    //                price = -1;

    @Test
    public void getCheapestFlights() {
        String sql = "SELECT destination, startDate, endDate, price FROM flights WHERE (destination, price) " +
                "IN (SELECT destination, MIN(price) AS minPrice FROM flights GROUP BY destination)";
        ResultSet cheapestFlights = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cheapestFlights = rs;

                // Prints the cheapest flights queried result set
                System.out.println("Destination: " + cheapestFlights.getString("destination") +
                        ", Start_Date: " + cheapestFlights.getString("startDate") +
                        ", End_Date: " + cheapestFlights.getString("endDate") +
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