package core;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookChromeFluent {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {

	Logger logger = Logger.getLogger("");
	logger.setLevel(Level.OFF);
	TreeMap<String, Object> sizeAndLocations = new TreeMap<String, Object>();
	String driverPath = "./resources/webdrivers/pc/chromedriver.exe";

	String url = "https://facebook.com/";
	String email_address = "testusera056@gmail.com";
	String password = "passwordForUser056";

	if (System.getProperty("os.name").toUpperCase().contains("MAC"))
	    driverPath = "./resources/webdrivers/mac/chromedriver";
	else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
	    driverPath = "./resources/webdrivers/pc/chromedriver.exe";
	else
	    throw new IllegalArgumentException("Unknown OS");

	System.setProperty("webdriver.chrome.driver", driverPath);
	System.setProperty("webdriver.chrome.silentOutput", "true");
	ChromeOptions option = new ChromeOptions();
	option.addArguments("disable-infobars");
	option.addArguments("--disable-notifications");
	if (System.getProperty("os.name").toUpperCase().contains("MAC"))
	    option.addArguments("-start-fullscreen");
	else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
	    option.addArguments("--start-maximized");
	else
	    throw new IllegalArgumentException("Unknown OS");
	driver = new ChromeDriver(option);
	// driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	WebDriverWait wait = new WebDriverWait(driver, 10);

	driver.get(url);

	// Pause in milleseconds (1000 – 1 sec)

	String title = driver.getTitle();
	WebElement copyright = wait.until(
		ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\'pageFooter\']/div[3]/div/span")));
	sizeAndLocations.put(" Size of copyright ", getSize(copyright));
	sizeAndLocations.put(" Location of copyright ", getLocation(copyright));

	String cr = copyright.getText();
	WebElement userName = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
	sizeAndLocations.put(" Size of email ", getSize(userName));
	sizeAndLocations.put(" Location of email ", getLocation(userName));
	userName.clear();
	userName.sendKeys(email_address);

	WebElement pwd = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass")));
	sizeAndLocations.put(" Size of pass ", getSize(pwd));
	sizeAndLocations.put(" Location of pass ", getLocation(pwd));
	pwd.sendKeys(password);

	WebElement lBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginbutton")));
	sizeAndLocations.put(" Size of Login button ", getSize(lBtn));
	sizeAndLocations.put(" Location of Login button ", getLocation(lBtn));
	lBtn.click();

	WebElement tmlBtn = wait.until(
		ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='u_0_a']/div[1]/div[1]/div/a/span")));
	sizeAndLocations.put(" Size of Timeline button ", getSize(tmlBtn));
	sizeAndLocations.put(" Location of Timeline button ", getLocation(tmlBtn));
	tmlBtn.click();

	WebElement friends = wait
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/ul/li[3]/a/span[1]")));

	String frnd = String.valueOf(friends.getText());
	sizeAndLocations.put(" Size of Friends button ", getSize(friends));
	sizeAndLocations.put(" Location of Friends button ", getLocation(friends));

	if (frnd.isEmpty()) {
	    frnd = "0";
	}

	WebElement navLabl = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userNavigationLabel")));
	sizeAndLocations.put(" Size of Account settings button ", getSize(navLabl));
	sizeAndLocations.put(" Location of Account settings button ", getLocation(navLabl));
	navLabl.click();
	// driver.findElement(By.partialLinkText("Log Out")).click();

	WebElement lOut = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Log Out")));
	// WebElement lOut =
	// wait.until(ExpectedConditions.(By.xpath("/html/body/div[29]/div/div/div/div/div[1]/div/div/ul/li[15]/a/span/span")));
	sizeAndLocations.put(" Size of Log out button ", getSize(lOut));
	sizeAndLocations.put(" Location of Log out button ", getLocation(lOut));
	lOut.click();

	driver.quit();

	System.out.println("Browser: Chrome");
	System.out.println("Title of the page: " + title);

	for (Map.Entry<String, Object> m : sizeAndLocations.entrySet()) {
	    System.out.println(m.getKey() + " " + m.getValue());
	}
	System.out.println("Copyright: " + cr);
	System.out.println("Friends: You have " + frnd + " friends");

    }

    private static Dimension getSize(WebElement id) {
	if ((id != null)) {
	    return (Dimension) id.getSize();
	}
	return null;
    }

    private static Point getLocation(WebElement id) {
	if ((id != null)) {
	    return (Point) id.getLocation();
	}
	return null;
    }

}
