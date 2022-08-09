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

import app.test.util.ReadExcelFile;

public class TestingFieldEmail {

	WebDriver driver = null;

	@BeforeClass
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rb-shoe-store.herokuapp.com/");

	}

	@SuppressWarnings("deprecation")
	@Test
	public void TS1_Email_TC1_Emptyfield() throws InterruptedException {

		WebElement emailSubmitButton = driver.findElement(By.id("remind_email_submit"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Click button "Submit"
		emailSubmitButton.click();

		String displayMessage = driver.findElement(By.xpath("//div[@class='flash alert_danger']")).getText();
		String expectedMessage = "Please enter an email address";

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testdata_TS1_Email_TC2_PositiveDataInField")
	public void TS1_Email_TC2_PositiveDataInField(String emailFormat) {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement emailInputField = driver.findElement(By.cssSelector("input[name='email']"));
		// Enter the data into the field
		emailInputField.sendKeys(emailFormat);

		WebElement emailSubmitButton = driver.findElement(By.id("remind_email_submit"));

		// Click button "Submit"
		emailSubmitButton.click();

		String displayMessage = driver.findElement(By.cssSelector("div[class='flash flash_success']")).getText();
		String expectedMessage = "Thanks! We will notify you of our new shoes at this email: " + emailFormat;

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testdata_TS1_Email_TC3_NegativeDataInField")
	public void TS1_Email_TC3_NegativeDataInField(String emailFormat) {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement emailInputField = driver.findElement(By.cssSelector("input[name='email']"));
		// Enter the data into the field
		emailInputField.sendKeys(emailFormat);

		WebElement emailSubmitButton = driver.findElement(By.id("remind_email_submit"));

		// Click button "Submit"
		emailSubmitButton.click();

		String displayMessage = driver.findElement(By.xpath("//div[@class='flash alert_danger']")).getText();
		String expectedMessage = "Invalid email format. Ex. name@example.com";

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@AfterClass
	public void closeBrowser() {

		driver.quit();

	}

	@DataProvider(name = "testdata_TS1_Email_TC2_PositiveDataInField")
	public Object[] testData_TS1_Email_TC2_PositiveDataInField() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet9",
				"TS1_Email_TC2_PositiveDataInField");
		return data;

	}

	@DataProvider(name = "testdata_TS1_Email_TC3_NegativeDataInField")
	public Object[] testdata_TS1_Email_TC3_NegativeDataInField() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet10",
				"TS1_Email_TC3_NegativeDataInField");
		return data;

	}

}
