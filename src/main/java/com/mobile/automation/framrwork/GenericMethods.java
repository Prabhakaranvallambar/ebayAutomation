package com.mobile.automation.framrwork;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;

public class GenericMethods extends MobileBase{
	private static final int TIMEOUT_IN_SECONDS = 40;
	private static final Logger LOGGER = Logger.getLogger(GenericMethods.class);
	WebDriverWait wait;
	public static long max=60;
	public static long med=30;
	public static long min=10;
	public String SelectedPage=null;
	
	
	public GenericMethods() {
	}

	
/**********************************************************************************************************************************	
	Purpose of Method: To wait until the page loads
		
**********************************************************************************************************************************/	

	public static boolean waitForPageLoad(WebDriver driver){
		LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName());
		boolean waitValue=false;
		try {
			driver.manage().timeouts().pageLoadTimeout(max, TimeUnit.SECONDS);
			LOGGER.info("Page Loaded completely");
			waitValue=true;
		}catch(TimeoutException e){
			LOGGER.info("Page taking longer than "+ max +" seconds time to load");
			waitValue=true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return waitValue;
	}
	
/**********************************************************************************************************************************	
	Purpose of Method: To wait for any desired period of time
		
**********************************************************************************************************************************/	

	protected void sleep(int timeOutInSeconds) {
		try {
			Thread.sleep(timeOutInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
/**********************************************************************************************************************************	
	Purpose of Method: To click on any desired object
		
**********************************************************************************************************************************/	

	protected void clickOnElement(WebDriver driver,By element) {
		LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+element.toString());
		try{
			WebElement element1=driver.findElement(element);
			element1.click();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
/**********************************************************************************************************************************	
	Purpose of Method: To click on any desired object
		
**********************************************************************************************************************************/	

	protected void clickOnElement(WebDriver driver,WebElement element) {
		LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+element.toString());
		try{
			WebElement el=element;
			el.click();
		}catch(Exception e){
			e.printStackTrace();
			WebElement e2=element;
			e2.click();
		}
	}
	


	
/**********************************************************************************************************************************	
	Purpose of Method: To type any desired text into an object
		
**********************************************************************************************************************************/	

	protected void type(WebElement element, String text) {
		LOGGER.info("Entering Text");
		element.sendKeys(new CharSequence[] { text });
		LOGGER.info("Entered " + text + " into the " + element
				+ " text field");
	}
	


/**********************************************************************************************************************************	
	Purpose of Method: To check if an object is present in the focused app page
		
**********************************************************************************************************************************/	

	protected static boolean isElementPresent(WebElement locator, WebDriver driver)
    {

        boolean waitValue = false;
        try {
            waitValue = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOf(locator)).isDisplayed();
            LOGGER.info(locator + "Is Present");
            
        } catch (Exception e) {
            LOGGER.info(locator + "Is Not present on the page");
        }
        return waitValue;

    }
	

/**********************************************************************************************************************************	
Purpose of Method: To Swipe in any desired direction and speed
	
**********************************************************************************************************************************/	

	public void Swipe(WebDriver driver,String direction, String speed) throws InterruptedException
	{
		LOGGER.info("Entered Swipe Loop");
		try{

			direction=direction.toLowerCase();
			org.openqa.selenium.Dimension size;
			int starty,endy,startx,scrWidth,scrHeight,rpm;			
			size=driver.manage().window().getSize();
			scrWidth=size.getWidth();
			LOGGER.info("Screen width: "+scrWidth);
			scrHeight=size.getHeight();
			LOGGER.info("screen width "+scrHeight);
			startx=(int)(size.width*0.5);
			starty=(int)(size.height*0.5);
			LOGGER.info("start x "+startx);
			LOGGER.info("start y "+starty);
			LOGGER.info("right swipe starting point - "+startx);
			String sDir;
			sDir=direction.toLowerCase();
			speed=speed.toLowerCase();
			if (speed=="slow") {
				rpm=250;
			}else if(speed=="medium"){
				rpm=150;
			}else{
				rpm=50;
			}
			switch (sDir) {
			case("up"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx,starty+(scrHeight-rpm-(starty)),startx,starty-(scrHeight-rpm-(starty)),3000);
				break;
			case("down"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx,starty-(scrHeight-rpm-(starty)),startx,starty-(scrHeight-rpm-(starty)),3000);			
				break;
			case("right"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx+(scrWidth-50-(startx)),starty,startx-(scrWidth-rpm-(startx)),starty,2000);		
				break;
			case("left"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx-(scrWidth-50-(startx)),starty,startx+(scrWidth-rpm-(startx)),starty,3000);
			break;
			
			default:
				break;
			}
		}
			catch(Exception e){
				LOGGER.info("Exception block inside Swipe Page");
				e.printStackTrace();
			}
	}

/**********************************************************************************************************************************	
	Purpose of Method: To wait until an object disappears
		
**********************************************************************************************************************************/	

    
    public void waitForInvisibility(WebElement webElement) {
       try{
    	boolean exit=false;
    	do {
			if (isElementPresent(webElement, driver)) {
				LOGGER.info("Waiting for object to be invisible");
				sleep(5);
			}else{
				exit=true;
			}
		} while (exit==false);
       }catch (Exception e) {
			e.printStackTrace();
		}
    }
 /**********************************************************************************************************************************	
	Purpose of Method: To swipe in a direction until the element is visible
		
**********************************************************************************************************************************/	
 
    public void swipeUntilVisible(WebElement webElement) throws InterruptedException {
       try{
    	boolean exit=false;
    	do {
			if (isElementPresent(webElement, driver)) {
				exit=true;
			}else{
				Swipe(driver, "up", "fast");
			}
		} while (exit==false);
    }catch (Exception e) {
		e.printStackTrace();
	}
    }
    
/**********************************************************************************************************************************	
	Purpose of Method: To minimise and maximise the application
		
**********************************************************************************************************************************/	
 
    public void minimiseApp(WebDriver driver) throws InterruptedException {
        try{
        	((AndroidDriver) driver).pressKeyCode(187);
        	sleep(2);
        	((AndroidDriver) driver).pressKeyCode(187);
     	
     }catch (Exception e) {
 		e.printStackTrace();
 	}
     }
    

}
