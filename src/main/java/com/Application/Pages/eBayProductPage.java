package com.Application.Pages;


import java.util.List;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mobile.automation.framrwork.GenericMethods;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;


public class eBayProductPage extends GenericMethods{
	private static final Logger LOGGER = Logger.getLogger(eBayProductPage.class);
	public boolean stepStatus;
	public static Integer searchVal,randomResult;
	public static String productName,itemDesc,itemPrice;
	ExtentTest logger;
	
	public static String usernameText,passwordText;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/search_box']")
	public WebElement searchBox;
	
	@FindBy(xpath="//*[@text='Home']")
	public WebElement home;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/text_slot_1']")
	public WebElement text_Slot;

	@FindBy(xpath="//android.widget.RelativeLayout[3]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")
	public WebElement searchSelect;
	
	@FindBy(xpath="//android.widget.Button[@text='BUY IT NOW']")
	public WebElement buyItNow;
	
	@FindBy(xpath="//android.widget.Button[@text='REVIEW']")
	public WebElement review;
	@FindBy(xpath="//android.widget.Button[@text='Proceed to Pay']")
	public WebElement proceedToPay;
	
	@FindBy(xpath="//*[contains(@text,'FILTER')]")
	public WebElement filter;
	
	@FindBy(xpath="//*[contains(@text,'Price range')]")
	public WebElement priceRange;
	
	@FindBy(xpath="//*[contains(@text,'Custom price range')]")
	public WebElement customPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/minimum_price_view']")
	public WebElement customMinPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/maximum_price_view']")
	public WebElement customMaxPriceRange;
	
	@FindBy(xpath="//android.widget.Button[@text='OK']")
	public WebElement filterOkBtn;
	
	@FindBy(xpath="//android.widget.Button[@text='DONE']")
	public WebElement filterDoneBtn;
	
	@FindBy(xpath="//*[contains(@text,'Credit Card')]")
	public WebElement creditCard;
	
	@FindBy(xpath="//android.widget.RadioButton[@text='American Express']")
	public WebElement cardType;
	
	@FindBy(xpath="//*[@resource-id='btnPay']")
	public WebElement payBtn;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[1]")
	public WebElement cardNumber;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[2]")
	public WebElement cardName;
	
	@FindBy(xpath="//android.widget.Spinner[@text='MM']")
	public WebElement expiryMonth;
	
	@FindBy(xpath="//android.widget.Spinner[@text='YY']")
	public WebElement expiryYear;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[3]")
	public WebElement cvv;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement closePaymentPage;
	
	@FindBy(xpath="//*[@text='select address']")
	public WebElement selectAddress;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
	public eBayProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	

/**********************************************************************************************************************************	
	Method Name			: SearchData
	Purpose of Method	: To search with the given input data
		
**********************************************************************************************************************************/	

public boolean searchData(WebDriver driver, String searchText) throws Exception {
		
		
		try {
			stepStatus = false;
			
				if (isElementPresent(searchBox, driver)) {
					type(searchBox, searchText);
					((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
				}
				
				if (isElementPresent(pageLoad, driver)) {
					sleep(5);
				}
				
				if (isElementPresent(text_Slot, driver)) {
					clickOnElement(driver, text_Slot);
				}
					String searchResult=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/textview_item_count']")).getAttribute("text");
					String[] resVal=searchResult.split(" ");
					Integer searchVal=Integer.parseInt(resVal[0]);
						if (searchVal>0) {
							stepStatus=true;
							LOGGER.info(searchVal+ " Results are displayed for search text entered");
						}else{
							LOGGER.info("No Results are displayed for search text");
						}
					
			}catch (Exception e) {
				e.printStackTrace();
			}
			
	return stepStatus;
			}

/**********************************************************************************************************************************	
Method Name			: SellectingSearchResults
Purpose of Method	: To randomly select any one of the search result except the first and last result
	
**********************************************************************************************************************************/	

public boolean selectingSearchResults(WebDriver driver) throws Exception {
	
	
	try {
		stepStatus = false;
		setFilter(driver, "1000", "150000");
		if (isElementPresent(text_Slot, driver)) {
			clickOnElement(driver, text_Slot);
		}
		Integer minVal=1;
		List<WebElement> Val=driver.findElements(By.xpath("//*[@resource-id='com.ebay.mobile:id/cell_collection_item']"));
		int maxVal=Val.size();
		LOGGER.info(maxVal);
		Integer randomResult=(int) ((Math.random()*(maxVal-minVal))+minVal);
		if (randomResult==1) {
			Swipe(driver, "up", "medium");
		}
		LOGGER.info(randomResult);
		WebElement itemName=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_title']"));
		productName=itemName.getAttribute("text");
		itemDesc=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/shipping_text']")).getAttribute("text");
		itemPrice=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")).getAttribute("text");	
		clickOnElement(driver, itemName);
		waitForInvisibility(pageLoad);
		LOGGER.info("Selected product's Name is : "+productName);
		LOGGER.info("Selected product's price is : "+itemPrice);
	
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return stepStatus;
		}

/**********************************************************************************************************************************	
Method Name			: setFilter
Purpose of Method	: To set a price filter for the search results, 
					  this method will make sure the results are displayed only for the given price range.
	
**********************************************************************************************************************************/	

public boolean setFilter(WebDriver driver, String minPrice, String maxPrice) throws Exception {
	
	try {
		stepStatus = false;
		if (isElementPresent(filter, driver)) {
			clickOnElement(driver, filter);
			if (isElementPresent(priceRange, driver)) {
				clickOnElement(driver, priceRange);
				clickOnElement(driver, customPriceRange);
				if (isElementPresent(customMinPriceRange, driver)) {
					customMinPriceRange.sendKeys(minPrice);
					clickOnElement(driver, customMaxPriceRange);
					customMaxPriceRange.sendKeys(maxPrice);
				}
				((AndroidDriver) driver).getPageSource();
				if (isElementPresent(filterOkBtn, driver)) {
				clickOnElement(driver, filterOkBtn);
				if (isElementPresent(filterDoneBtn, driver)) {
					clickOnElement(driver, filterDoneBtn);
					waitForInvisibility(pageLoad);
				}
				}
				stepStatus=true;
			}
		}
		
	
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return stepStatus;
		}

/**********************************************************************************************************************************	
Method Name			: proceedOrder
Purpose of Method	: To take the order entry till payment window
	
**********************************************************************************************************************************/	

public boolean proceedOrder(WebDriver driver) throws Exception {
	
	
	try {
		stepStatus = false;
		waitForInvisibility(pageLoad);
		driver.getPageSource();
		WebElement itemName=driver.findElement(By.xpath("//*[contains(@text,'"+productName+"')]"));
		WebElement productPrice =driver.findElement(By.xpath("//*[contains(@text,'"+itemPrice+"')]"));
		if (isElementPresent(itemName, driver) && isElementPresent(productPrice, driver))  {
			stepStatus=true;
			LOGGER.info("The product name "+itemName+" matches with the details in the  product page");
		}else{
			LOGGER.info("The product name "+itemName+" matches with the details in the  product page");
		}
		
		if (isElementPresent(buyItNow, driver)) {
			clickOnElement(driver, buyItNow);
			if (isElementPresent(review, driver)) {
				clickOnElement(driver, review);
			}
			waitForInvisibility(pageLoad);
			((AndroidDriver) driver).getPageSource();
		}

		Swipe(driver, "up", "fast");
		Swipe(driver, "up", "fast");
		
		if (isElementPresent(proceedToPay, driver)) {
			clickOnElement(driver, proceedToPay);
			stepStatus=true;
		}
		
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return stepStatus;
		}

}


