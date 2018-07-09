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
	
	@FindBy(xpath="//android.widget.RadioButton[@text='UPI']")
	public WebElement UPIRadioBtn;
	
	@FindBy(xpath="//*[@text='UPI']")
	public WebElement UPI;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']")
	public WebElement VPA;
	
	@FindBy(xpath="//*[@resource-id='validateUser']")
	public WebElement makePayment;
	
	@FindBy(xpath="//*[@resource-id='android:id/message']")
	public WebElement popMessage;
	
	@FindBy(xpath="//*[@text='OK']")
	public WebElement okBtn;
	
	
	public eBayPayment(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Method Name			: processPayment
	Purpose of Method	: To enter payment options and procced with payment steps
		
**********************************************************************************************************************************/	

public boolean processPayment(WebDriver driver, String upiName) throws Exception {
	
	
	try {
		stepStatus = false;
		waitForInvisibility(pageLoad);
		if (isElementPresent(UPI, driver)) {
			clickOnElement(driver, UPI);
			clickOnElement(driver, UPIRadioBtn);
			clickOnElement(driver, payBtn);
			waitForInvisibility(pageLoad);
			type(VPA, upiName);
			clickOnElement(driver, makePayment);
			if (isElementPresent(makePayment, driver)) {
				clickOnElement(driver, makePayment);
			}
				String message=popMessage.getAttribute("text");
				if (message.contains("Please enter your register Virtual")) {
					stepStatus=false;
					LOGGER.info("The following error message is displayed"+message);
					clickOnElement(driver, okBtn);
				}else{
					stepStatus=true;
				}
				if (isElementPresent(home, driver)) {
					clickOnElement(driver, home);
				}
				
		}
		
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return stepStatus;
		}


}


