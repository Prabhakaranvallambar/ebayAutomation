package com.Application.Pages;


import java.util.List;
import java.util.Properties;

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


public class eBayPayment extends GenericMethods{
	private static final Logger LOGGER = Logger.getLogger(eBayPayment.class);
	public boolean stepStatus;
	ExtentTest logger;
	public static String usernameText,passwordText;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
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
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement home;
	
	public eBayPayment(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Method Name			: processPayment
	Purpose of Method	: To enter payment options and procced with payment steps
		
**********************************************************************************************************************************/	

public boolean processPayment(WebDriver driver, String cardNum,String creditCardName, String cardExpiryMonth,String cardExpiryYear,String cardCvv) throws Exception {
	
	
	try {
		stepStatus = false;
		
		waitForInvisibility(pageLoad);
		if (isElementPresent(creditCard, driver)) {
			clickOnElement(driver, creditCard);
			clickOnElement(driver, cardType);
			clickOnElement(driver, payBtn);
			waitForInvisibility(pageLoad);
			if (isElementPresent(creditCard, driver)) {
				clickOnElement(driver, creditCard);
			}
			if (cardNum.equals("")) {
				clickOnElement(driver, home);
				LOGGER.info("Payment details are not provided");
			}else{
				clickOnElement(driver, cardNumber);
				cardNumber.sendKeys(cardNum);
				clickOnElement(driver, cardName);
				cardName.sendKeys(creditCardName);
				clickOnElement(driver, expiryMonth);
				clickOnElement(driver, By.xpath("//*[@text='"+cardExpiryMonth+"']"));
				clickOnElement(driver, expiryYear);
				clickOnElement(driver, By.xpath("//*[@text='"+cardExpiryYear+"']"));
				cvv.sendKeys(cardCvv);
				clickOnElement(driver, home);
				stepStatus=true;
			}
			
			
		}
	
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return stepStatus;
		}


}


