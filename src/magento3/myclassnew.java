package magento3;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import magento3.parametres;

public class myclassnew extends parametres {
	
	
	@BeforeTest
	public void mySetup() {
		driver.manage().window().maximize();

	}

	@Test(invocationCount = 4)

	public void AddOneRandomItem() throws InterruptedException, IOException {
		driver.get(myURL);

		Random rand = new Random();

		int myRandomIndex = rand.nextInt(3);
		int RandomITem = rand.nextInt(4);

//		String[] myURLS = { "https://magento.softwaretestingboard.com/women.html",
//				"https://magento.softwaretestingboard.com/men.html",
//				"https://magento.softwaretestingboard.com/gear.html" };
//
//		
//		
//		driver.get(myURLS[myRandomIndex]);

		WebElement[] myIDs = { driver.findElement(By.id("ui-id-4")), driver.findElement(By.id("ui-id-5")),
				driver.findElement(By.id("ui-id-6")) };

		myIDs[myRandomIndex].click();
		Thread.sleep(1000);

		if (driver.getCurrentUrl().contains("gear")) {
			WebElement container = driver.findElement(By.cssSelector(".product-items.widget-product-grid"));
			List<WebElement> myItems = container.findElements(By.tagName("li"));

			myItems.get(RandomITem).click();

			Thread.sleep(1000);
			driver.findElement(By.id("product-addtocart-button")).click();
			Thread.sleep(3000);
			String ActualMsg = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div")).getText();
			System.out.println(ActualMsg);
			Assert.assertEquals(ActualMsg.contains("The requested qty is not available"), true); 
			TakeScreenShotFunction();


		} else {
			WebElement container = driver.findElement(By.cssSelector(".product-items.widget-product-grid"));
			List<WebElement> myItems = container.findElements(By.tagName("li"));

			myItems.get(RandomITem).click();
			Thread.sleep(1000);

			WebElement SizeBox = driver
					.findElement(By.cssSelector("div[class='swatch-attribute size'] div[role='listbox']"));
			List<WebElement> sizeBoxOptions = SizeBox.findElements(By.tagName("div"));
			int randomSize = rand.nextInt(sizeBoxOptions.size());
			sizeBoxOptions.get(randomSize).click();

			WebElement ColorBox = driver
					.findElement(By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']"));
			List<WebElement> ColorBoxOptions = ColorBox.findElements(By.tagName("div"));
			int randomColor = rand.nextInt(ColorBoxOptions.size());
			ColorBoxOptions.get(randomColor).click();
			Thread.sleep(1000);

			driver.findElement(By.id("product-addtocart-button")).click();
			Thread.sleep(3000);
			
			String ActualMsg = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div")).getText();
			System.out.println(ActualMsg);
			Assert.assertEquals(ActualMsg.contains("You added"), true);
			TakeScreenShotFunction();
		}

	}

	@AfterTest
	public void PostTesting() {
		
	}
	
    static void TakeScreenShotFunction() throws IOException {
		
		
	Date date = new Date();

	String ActualDate = date.toString().replace(":", "-");
	System.out.println(ActualDate);
	TakesScreenshot src = ((TakesScreenshot) driver);
	File srcFile = src.getScreenshotAs(OutputType.FILE);
	File dest = new File(".//myPictures/" + ActualDate + ".png");

	FileUtils.copyFile(srcFile, dest);
	}
	
	

}
