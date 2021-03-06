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


public class ProductPage extends GenericMethods{
	private static final Logger LOGGER = Logger.getLogger(ProductPage.class);
	public boolean stepStatus;
	public static String productName,itemPrice;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/search_box']")
	private WebElement searchBox;
	
	@FindBy(xpath="//*[@text='Home']")
	private WebElement home;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/text_slot_1']")
	private WebElement text_Slot;

	@FindBy(xpath="//android.widget.RelativeLayout[3]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")
	private WebElement searchSelect;
	
	@FindBy(xpath="//android.widget.Button[@text='BUY IT NOW']")
	private WebElement buyItNow;
	
	@FindBy(xpath="//android.widget.Button[@text='REVIEW']")
	private WebElement review;
	
	@FindBy(xpath="//android.widget.Button[@text='Proceed to Pay']")
	private WebElement proceedToPay;
	
	@FindBy(xpath="//*[contains(@text,'FILTER')]")
	private WebElement filter;
	
	@FindBy(xpath="//*[contains(@text,'Price range')]")
	private WebElement priceRange;
	
	@FindBy(xpath="//*[contains(@text,'Custom price range')]")
	private WebElement customPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/minimum_price_view']")
	private WebElement customMinPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/maximum_price_view']")
	private WebElement customMaxPriceRange;
	
	@FindBy(xpath="//android.widget.Button[@text='OK']")
	private WebElement filterOkBtn;
	
	@FindBy(xpath="//android.widget.Button[@text='DONE']")
	private WebElement filterDoneBtn;
	
	@FindBy(xpath="//*[contains(@text,'Credit Card')]")
	private WebElement creditCard;
	
	@FindBy(xpath="//android.widget.RadioButton[@text='American Express']")
	private WebElement cardType;
	
	@FindBy(xpath="//*[@resource-id='btnPay']")
	private WebElement payBtn;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[1]")
	private WebElement cardNumber;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[2]")
	private WebElement cardName;
	
	@FindBy(xpath="//android.widget.Spinner[@text='MM']")
	private WebElement expiryMonth;
	
	@FindBy(xpath="//android.widget.Spinner[@text='YY']")
	private WebElement expiryYear;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[3]")
	private WebElement cvv;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement closePaymentPage;
	
	@FindBy(xpath="//*[@text='select address']")
	private WebElement selectAddress;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	private WebElement pageLoad;
	
	public ProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	

/**********************************************************************************************************************************	
	Method Name			: searchData
	Purpose of Method	: To search with the given input data
		
**********************************************************************************************************************************/	

public boolean searchData(WebDriver driver, String searchText) throws Exception {
		
		
		try {
			stepStatus = false;
			
				if (isElementPresent(searchBox, driver)) {
					type(searchBox, searchText);
					((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
				}
				
				waitForInvisibility(pageLoad);
				
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
Method Name			: sellectingSearchResults
Purpose of Method	: To randomly select any one of the search result except the first and last result
	
**********************************************************************************************************************************/	

public boolean selectingSearchResults(WebDriver driver,String leastPrice, String hightPrice) throws Exception {
	
	
	try {
		stepStatus = false;
		setFilter(driver, leastPrice,hightPrice);
		if (isElementPresent(text_Slot, driver)) {
			clickOnElement(driver, text_Slot);
		}
		Integer minVal=2;
		List<WebElement> Val=driver.findElements(By.xpath("//*[@resource-id='com.ebay.mobile:id/cell_collection_item']"));
		Integer maxVal=Val.size();
		Integer randomResult=(int) ((Math.random()*(maxVal-minVal))+minVal);
		LOGGER.info("Item Number "+randomResult+ " in the search result is going to be selected");
		if (randomResult==1) {
			Swipe(driver, "up", "medium");
			LOGGER.info("As the random number is "+randomResult+" Swiping up the page so that 1st search result is not selected ");
		}
		WebElement itemName=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_title']"));
		productName=itemName.getAttribute("text").trim();
		String price=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")).getAttribute("text");	
		itemPrice=price.replace("₹","Rs. ");
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
		driver.getPageSource();
		sleep(3);
		if (isElementPresent(driver.findElement(By.xpath("//*[contains(@text,'"+productName+"')]")), driver) || isElementPresent(driver.findElement(By.xpath("//*[contains(@text,'"+itemPrice+"')]")), driver) ) {
			stepStatus=true;
			LOGGER.info("The product name "+productName+" and product price"+itemPrice+" matches with the details in the  product page");
		}else{
			LOGGER.info("The product name "+productName+" and product price"+itemPrice+" does not matches with the details in the  product page");
		}
		
		if (isElementPresent(proceedToPay, driver)) {
			clickOnElement(driver, proceedToPay);
		}
		
	}catch (Exception e) {
			e.printStackTrace();
			return stepStatus;
		}
		
return stepStatus;
		}

}


