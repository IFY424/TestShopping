package com.job.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyShoppingFunctionality {
	@Test(enabled = true)
	public void VerifyShoppingFuncion() throws Exception {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver",
				"/Users/husenjanemet/eclipse-workspace/HusenjanTestingProject/src/test/resources/chromedriver");
		driver = new ChromeDriver();// this line open web browser
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();// maximize the browser window
		// below setting page load time from 0s up to 30s max
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/index.php");
		// Verify home page
		String pageTitle = driver.getTitle();
		String expectedTitle = "My Store";
		Assert.assertEquals(pageTitle, expectedTitle);

		// switch to Iframe
		WebElement firstImage = driver.findElement(By.xpath("//img[@src='http://automationpractice.com/img/p/1/1-home_default.jpg']"));
		firstImage.click();
		WebElement iFrameElem = driver.findElement(By.className("fancybox-iframe"));
		driver.switchTo().frame(iFrameElem);

		WebElement quantityField = driver.findElement(By.xpath("//input[@id='quantity_wanted']"));
		String itemQuantity = "1";
		Assert.assertEquals(quantityField.getAttribute("value"), itemQuantity);
		String itemName = "Faded Short Sleeve T-shirts";
		String itemPrice = "$16.51";

		
		// click on submit
		driver.findElement(By.name("Submit")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//a[@class='btn btn-default button button-medium']")).click();
      
		// scroll down the page
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)");

		// verify the item unit, name and price
		WebElement quantityFieldOne = driver
				.findElement(By.xpath("//tr[@id='product_1_1_0_0']/td[@class='cart_quantity text-center']/input"));
		Assert.assertEquals(quantityFieldOne.getAttribute("value"), itemQuantity);

		String itemNameOne = driver
				.findElement(
						By.xpath("//tr[@id='product_1_1_0_0']/td[@class='cart_description']/p[@class='product-name']"))
				.getText();
		Assert.assertEquals(itemNameOne, itemName);

		String priceOne = driver
				.findElement(By.xpath("//tr[@id='product_1_1_0_0']/td[@class='cart_total']/span[@class='price']"))
				.getText();
		Assert.assertEquals(priceOne, itemPrice);

		// verify the total price
		String totalProducts = driver.findElement(By.xpath("//td[@id='total_product']")).getText();
		String shipping = driver.findElement(By.xpath("//td[@id='total_shipping']")).getText();
		String tax = driver.findElement(By.xpath("//td[@id='total_tax']")).getText();
		String totalPrice = driver.findElement(By.xpath("//td[@id='total_price_container']")).getText();

		float x = Float.parseFloat(totalProducts.replace("$",""));
		float y = Float.parseFloat(shipping.replace("$",""));
		float  z = Float.parseFloat(tax.replace("$",""));
		float  sum = Float.parseFloat(totalPrice.replace("$",""));
		Assert.assertEquals(x + y + z, sum);

		driver.quit();


	}

}
