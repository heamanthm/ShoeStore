package app.test.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public class TestingMonthFilter {
	WebDriver driver = null;

	@BeforeClass
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://rb-shoe-store.herokuapp.com/");

	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testdata_TS4_MonthFilter_TC1_SelectingMonth")
	public void TS4_MonthFilter_TC1_SelectingMonth(String month) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[text()='" + month + "']")).click();

		List<WebElement> displayedListMonth = driver
				.findElements(By.xpath("//td[@class='shoe_result_value shoe_release_month']/a"));

		int check = displayedListMonth.size() - 2;

		for (int i = 0; i < check; i++) {

			String actulaMonth = displayedListMonth.get(i).getText();
			Assert.assertEquals(actulaMonth, month);

		}

		driver.findElement(By.xpath("//a[text()='Home']")).click();
	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "testdata_TS4_MonthFilter_TC2_SelectingAllShoesOption")
	public void TS4_MonthFilter_TC2_SelectingAllShoesOption(String expectedDisplayMonths) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[text()='All Shoes']")).click();

		List<WebElement> displayedListMonth = driver
				.findElements(By.xpath("//td[@class='shoe_result_value shoe_release_month']/a"));
		Set<String> months = new HashSet<>();

		for (int i = 0; i < displayedListMonth.size(); i++) {

			String actulaMonth = displayedListMonth.get(i).getText();
			months.add(actulaMonth);
		}

		for (String month : months) {
			Assert.assertTrue(expectedDisplayMonths.contains(month), month + " not Displayed");
		}

	}

	@DataProvider(name = "testdata_TS4_MonthFilter_TC1_SelectingMonth")
	public Object[] testData_TS4_MonthFilter_TC1_SelectingMonth() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet7",
				"TS4_MonthFilter_TC1_SelectingMonth");
		return data;

	}

	@DataProvider(name = "testdata_TS4_MonthFilter_TC2_SelectingAllShoesOption")
	public Object[] testData_TS4_MonthFilter_TC2_SelectingAllShoesOption() {
		ReadExcelFile getData = new ReadExcelFile();
		Object[] data = getData.getDataFromExcel(
				System.getProperty("user.dir") + "/src/app/test/data/ShoeTestData.xlsx", "Sheet8",
				"TS4_MonthFilter_TC2_SelectingAllShoesOption");
		return data;

	}

	@AfterClass
	public void closeBrowser() {

		driver.quit();

	}

}
