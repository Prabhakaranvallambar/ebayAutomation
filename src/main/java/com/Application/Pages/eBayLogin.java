package com.Application.Pages;


import java.util.List;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mobile.automation.framrwork.GenericMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;


public class eBayLogin extends GenericMethods{
	private static final Logger LOGGER = Logger.getLogger(eBayLogin.class);
	public boolean stepStatus;
	public static int searchVal,randomResult;
	public static String productName,itemDesc,productPrice;
	ExtentReports extent;
	ExtentTest logger;
	
	public static String usernameText,passwordText;
	
	@FindBy(xpath="//android.widget.Button[@text='SIGN IN']")
	public static WebElement sign_In_Btn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_username']")
	public WebElement userName;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_password']")
	public WebElement password;
	
	@FindBy(xpath="//*[@text='Home']")
	public WebElement homeBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement homeButton;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
	@FindBy(xpath="//android.widget.Button[@text='MAYBE LATER']")
	public WebElement mayBeLater;
	
	@FindBy(xpath="//android.widget.Button[@text='NOT NOW']")
	public WebElement notNow;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_sign_in_status']")
	public WebElement signInStatus;
	
	public eBayLogin(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Method Name			: SignIn
	Purpose of Method	: To sign in with the given username and password and verify if the sign in was successful
		
**********************************************************************************************************************************/	

	@SuppressWarnings({ "deprecation", "rawtypes" })
public boolean SignIn(WebDriver driver,String usernameText, String passwordText) throws Exception {
		
		
		try {
			stepStatus = false;
			waitForInvisibility(pageLoad);
			if (isElementPresent(sign_In_Btn, driver)) {
				clickOnElement(driver,sign_In_Btn);
				waitForInvisibility(pageLoad);
				if (isElementPresent(userName, driver)) {
					LOGGER.info(usernameText);
					userName.sendKeys(usernameText);
					password.sendKeys(passwordText);
				}
				if (isElementPresent(sign_In_Btn, driver)) {
					clickOnElement(driver, sign_In_Btn);
					waitForInvisibility(pageLoad);
				}
				
				if (isElementPresent(mayBeLater, driver)) {
					clickOnElement(driver, mayBeLater);
				}
				
				if (isElementPresent(notNow, driver)) {
					clickOnElement(driver, notNow);
				}
			}
			waitForInvisibility(pageLoad);
			if (isElementPresent(homeButton, driver)) {
				clickOnElement(driver, homeButton);
				if (isElementPresent(signInStatus, driver)) {
					stepStatus=true;
					clickOnElement(driver,homeBtn);
					LOGGER.info("User is signed in successfully with the following username: "+usernameText+" and password: "+passwordText);
				}else{
					LOGGER.info("User is not signed in successfully with the following username: "+usernameText+" and password: "+passwordText);
				}
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		return stepStatus;
		}
}


