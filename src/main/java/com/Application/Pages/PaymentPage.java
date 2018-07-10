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


public class PaymentPage extends GenericMethods{
	private static final Logger LOGGER = Logger.getLogger(PaymentPage.class);
	public boolean stepStatus;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	private WebElement pageLoad;
	
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
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement home;
	
	@FindBy(xpath="//android.widget.RadioButton[@text='UPI']")
	private WebElement UPIRadioBtn;
	
	@FindBy(xpath="//*[@text='UPI']")
	private WebElement UPI;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']")
	private WebElement VPA;
	
	@FindBy(xpath="//*[@resource-id='validateUser']")
	private WebElement makePayment;
	
	@FindBy(xpath="//*[@resource-id='android:id/message']")
	private WebElement popMessage;
	
	@FindBy(xpath="//*[@text='OK']")
	private WebElement okBtn;
	
	
	public PaymentPage(WebDriver driver) {
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
			String messageBox=popMessage.getAttribute("text");
				if (messageBox.contains("Please enter your register Virtual")) {
					stepStatus=false;
					LOGGER.info("The following error message is displayed"+messageBox);
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


