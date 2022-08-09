package app.test.main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestingFieldPromoCode {

	WebDriver driver = null;

	@BeforeClass
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rb-shoe-store.herokuapp.com/");

	}

	@SuppressWarnings("deprecation")
	@Test
	public void TS2_PromCode_TC1_Emptyfield() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement couponSubmitButton = driver.findElement(By.id("promo_code_submit"));

		// Click button "Submit"
		couponSubmitButton.click();
		String displayMessage = driver.findElement(By.xpath("//div[@class='flash alert_danger']")).getText();
		String expectedMessage = "Please enter a promotional code";

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void TS2_PromCode_TC3_NegativeDataInField() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement couponInputField = driver.findElement(By.cssSelector("input#promo_code_input"));
		couponInputField.sendKeys("ABCXYZ123");

		WebElement couponSubmitButton = driver.findElement(By.id("promo_code_submit"));

		// Click button "Submit"
		couponSubmitButton.click();
		String displayMessage = driver.findElement(By.xpath("//div[@class='flash alert_danger']")).getText();
		String expectedMessage = "Invalid code format";

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@AfterClass
	public void closeBrowser() {

		driver.quit();

	}

	@DataProvider(name = "testdata")
	public Object[][] testData() {
		Object[][] data = new Object[1][2];
		return data;
	}

}
