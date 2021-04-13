package setup;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import utils.Config;

public class TestSetup {
	
	
private static WebDriver driver = null;
	
	public static WebDriver getDriver() {
		return driver;
	}


	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	@BeforeSuite
	public void beforeSuite() {
		
		WebDriverManager.chromedriver().setup();
	}
	
	
	@BeforeMethod
	public void beforeMethod() {
		
		WebDriver tempDriver = null;
		
		tempDriver = new ChromeDriver();
		
		setDriver(tempDriver);
		TestSetup.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		//RestAPI
		RestAssured.baseURI = Config.getProperty("apiurl");
		
		
		
	}

	@AfterMethod
	public void afterMethod() {
		
		getDriver().quit();
	}

}
