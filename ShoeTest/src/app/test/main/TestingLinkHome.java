package app.test.main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestingLinkHome {

	WebDriver driver = null;
	
	@BeforeClass
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rb-shoe-store.herokuapp.com/");

	}

	@SuppressWarnings("deprecation")
	@Test
	public void TS5_Home_TC1_HomeLink() throws InterruptedException {
		

	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[text()='January']")).click();

		driver.findElement(By.xpath("//a[text()='Home']")).click();

		WebElement homePageAsserter = driver.findElement(By.xpath("//h2[text()='Welcome to Shoe Store!']"));

		Assert.assertTrue(homePageAsserter.isDisplayed(), "Welcome to Shoe Store! is not displayed");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TS5_Home_TC2_BackLink() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[text()='All Shoes']")).click();

		driver.findElement(By.xpath("//a[text()='Back']")).click();

		WebElement homePageAsserter = driver.findElement(By.xpath("//h2[text()='Welcome to Shoe Store!']"));

		Assert.assertTrue(homePageAsserter.isDisplayed(), "Welcome to Shoe Store! is not displayed");
	}
	
	@AfterClass
	public void closeBrowser() {

		driver.quit();

	}

}
