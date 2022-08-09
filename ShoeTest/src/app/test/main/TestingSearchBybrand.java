package app.test.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import app.test.util.ReadExcelFile;

public class TestingSearchBybrand {
	WebDriver driver = null;

	@BeforeClass
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rb-shoe-store.herokuapp.com/");

	}

	@SuppressWarnings("deprecation")
	@Test
	public void TS3_ChooseBrand_TC1_Emptyfield() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement searchButton = driver.findElement(By.id("search_button"));

		// Click button "Submit"
		searchButton.click();
		String displayMessage = driver.findElement(By.xpath("//div[@class='flash notice']")).getText();
		String expectedMessage = "Please Select a Brand";

		Assert.assertEquals(displayMessage, expectedMessage);

	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testdata_TS3_ChooseBrand_TC2_SearchByBrandNolist")
	public void TS3_ChooseBrand_TC2_SearchByBrandNolist(String brand) throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Select choseBrand = new Select(driver.findElement(By.id("brand")));

		choseBrand.selectByVisibleText(brand);

		WebElement searchButton = driver.findElement(By.id("search_button"));

		// Click button "Submit"
		searchButton.click();

		String title = driver.findElement(By.xpath("//div[@class='title']/h2")).getText();

		Assert.assertTrue(title.contains(brand), "Navigation failed for the results page of " + brand);

		try {

			WebElement shoeList = driver.findElement(By.xpath("//ul[@id='shoe_list']/li"));

			Assert.assertFalse(shoeList.isDisplayed(), "Shoe results are dispalyed for " + brand);

		} catch (NoSuchElementException e) {
			e.getMessage();
		}

		driver.findElement(By.xpath("//a[text()='Home']")).click();
	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testData_TS3_ChooseBrand_TC3_SearchByBrandWithlist")
	public void TS3_ChooseBrand_TC3_SearchByBrandWithlist(String brand) throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Select choseBrand = new Select(driver.findElement(By.id("brand")));

		choseBrand.selectByVisibleText(brand);

		WebElement searchButton = driver.findElement(By.id("search_button"));

		// Click button "Submit"
		searchButton.click();

		String title = driver.findElement(By.xpath("//div[@class='title']/h2")).getText();

		Assert.assertTrue(title.contains(brand));

		List<WebElement> listedBrandNames = driver
				.findElements(By.xpath("//td[@class='shoe_result_value shoe_brand']/a"));

		for (WebElement e : listedBrandNames) {

			String brandName = e.getText();

			Assert.assertEquals(brandName, brand);
		}
		driver.findElement(By.xpath("//a[text()='Home']")).click();
	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testData_TS3_ChooseBrand_TC4_DropDownDataValidation")
	public void TS3_ChooseBrand_TC4_DropDownDataValidation(String expectedValues) throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Select choseBrand = new Select(driver.findElement(By.id("brand")));

		List<WebElement> options = choseBrand.getOptions();
		Set<String> values = new HashSet<>();

		for (WebElement e : options) {
			values.add(e.getText());
		}

		for (String name : values) {
			Assert.assertTrue(expectedValues.contains(name), name + " not present in the list of values");
		}
		driver.findElement(By.xpath("//a[text()='Home']")).click();

	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testData_TS3_ChooseBrand_TC5_DropDownValueKeyboardAccesible")
	public void TS3_ChooseBrand_TC5_DropDownValueKeyboardAccesible(String brand) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement choseBrand = driver.findElement(By.id("brand"));

		choseBrand.sendKeys(brand);

		WebElement searchButton = driver.findElement(By.id("search_button"));

		// Click button "Submit"
		searchButton.click();

		String title = driver.findElement(By.xpath("//div[@class='title']/h2")).getText();

		Assert.assertTrue(title.contains(brand));
		driver.findElement(By.xpath("//a[text()='Home']")).click();

	}

	@DataProvider(name = "testdata_TS3_ChooseBrand_TC2_SearchByBrandNolist")
	public Object[] testData_TS3_ChooseBrand_TC2_SearchByBrandNolist() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet4",
				"TS3_ChooseBrand_TC2_SearchByBrandNolist");
		return data;

	}

	@DataProvider(name = "testData_TS3_ChooseBrand_TC3_SearchByBrandWithlist")
	public Object[] testData_TS3_ChooseBrand_TC3_SearchByBrandWithlist() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet5",
				"TS3_ChooseBrand_TC3_SearchByBrandWithlist");
		return data;

	}
	
	@DataProvider(name = "testData_TS3_ChooseBrand_TC4_DropDownDataValidation")
	public Object[] testData_TS3_ChooseBrand_TC4_DropDownDataValidation() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet1",
				"TS3_ChooseBrand_TC4_DropDownDataValidation");
		return data;

	}
	
	@DataProvider(name = "testData_TS3_ChooseBrand_TC5_DropDownValueKeyboardAccesible")
	public Object[] testData_TS3_ChooseBrand_TC5_DropDownValueKeyboardAccesible() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet6",
				"TS3_ChooseBrand_TC5_DropDownValueKeyboardAccesible");
		return data;

	}

	@AfterClass
	public void closeBrowser() {

		driver.quit();

	}

}
