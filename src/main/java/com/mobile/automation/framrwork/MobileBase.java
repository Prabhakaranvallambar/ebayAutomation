package com.mobile.automation.framrwork;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;

public class MobileBase {
	String workingDir = System.getProperty("user.dir");
	protected static final Logger LOGGER = Logger.getLogger(MobileBase.class);
	private final String applicationBrowserProperties = "application.properties";
	
	public Properties applicationProp;
	protected InputStream inputStreamForApplicationProp;
	public static String appName;
	public static String appPackage;
	public static String appActivity;
	public static String deviceName;    
	public static String platformName;
	public static String platformVersion;
	public final static String DeviceOS=System.getProperty("device");

	public static WebDriver driver=null;
	

	public WebDriver getBaseDriver() {
		return driver;
	}
	
	 public MobileBase() {
		 try {
			applicationProp = new Properties();
			//configProp = new Properties();
			inputStreamForApplicationProp = getClass().getClassLoader().getResourceAsStream(applicationBrowserProperties);
			applicationProp.load(inputStreamForApplicationProp);
			
			deviceName = applicationProp.getProperty("deviceName");
			appPackage = applicationProp.getProperty("appPackage");
			appActivity = applicationProp.getProperty("appActivity");
			platformName = applicationProp.getProperty("platformName");
			platformVersion = applicationProp.getProperty("platformVersion");
			appName = applicationProp.getProperty("appName");
			} catch (Exception e) {
				LOGGER.info("property values are not loaded ");

			}
		
	}
	
	@BeforeSuite
	public void suite() throws Exception{
		try{
		String detailInstanceName = new Object() {}.getClass().getEnclosingMethod().getName();
		System.out.println("Application to be executed - "+appName);
		driver = setDesiredCapabilities();
		waitForActivity(appActivity, 30);
		System.out.println(driver);
		//String className = this.getClass().getSimpleName();
		}catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
public  WebDriver setDesiredCapabilities() throws InterruptedException, Exception {
	
	
		try {
			File app = new File(System.getProperty("user.dir") +"/eBay.apk");
					DesiredCapabilities capability =new DesiredCapabilities();
					/*AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
					service.start();*/
					capability .setCapability("autoAcceptAlerts",true);
					capability.setCapability("platformName",platformName);
					capability.setCapability("deviceName", deviceName);
					capability.setCapability("platformVersion",platformVersion);
					capability.setCapability("newCommandTimeout",300);
					capability.setCapability("noReset",false);
					capability.setCapability("fullReset",true);
					capability.setCapability("app", app.getAbsolutePath());
					capability.setCapability("appPackage",appPackage);
					capability.setCapability("appActivity",appActivity);
					
					driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),capability);
		}catch(Exception e) {
			e.printStackTrace();
			
			
		}
		return driver;
	}
public void waitForActivity(String desiredActivity, int wait) throws InterruptedException
{
    int counter = 0;
    do {
        Thread.sleep(1000);
        counter++;
    } while(((StartsActivity) driver).currentActivity().contains(desiredActivity) && (counter<=wait));

    LOGGER.info(("Activity appeared :" + ((StartsActivity) driver).currentActivity()));
}
	

	@AfterSuite(alwaysRun=true)
	public static void releaseDevice() throws IOException, InterruptedException{
	}
	 
	@AfterTest(alwaysRun=true)
	public void logResultToTWB() {
		/*String className = this.getClass().getSimpleName();
		logResult(result, className);*/
	}
	
	
}
