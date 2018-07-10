package com.eBay.Module;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.Application.Pages.Login;
import com.Application.Pages.PaymentPage;
import com.Application.Pages.ProductPage;
import com.mobile.automation.framrwork.GenericMethods;
import com.mobile.automation.framrwork.MobileBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SampleTest extends MobileBase{
	
	private static final Logger LOGGER = Logger.getLogger(SampleTest.class);
	public boolean stepResult;
	public static String detailInstanceName;
	ExtentReports extent;
	ExtentTest logger;
	//private final String applicationBrowserProperties = "application.properties";
	
	public Properties applicationProp;
	InputStream inputStreamForApplicationProp;
	@BeforeTest(alwaysRun=true)
	 public void startReport() throws Exception{
		try {
			applicationProp=new Properties();
			inputStreamForApplicationProp=getClass().getClassLoader().getResourceAsStream("config.properties");;
			
			applicationProp.load(inputStreamForApplicationProp);
			System.out.println("Value from properties file "+applicationProp.getProperty("usernameText"));
		 extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/EbayExtentReport.html", true);
		 extent
		                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
		                .addSystemInfo("Environment", "Automation Testing")
		                .addSystemInfo("User Name", "Prabhakaran");
		                extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	 }
	
	@Test(priority=0)
	public void eBay_OrderEntry() throws Exception {
		try{
		String detailInstanceName = new Object() {}.getClass().getEnclosingMethod().getName();
		logger = extent.startTest(detailInstanceName);
		String usernameText=applicationProp.getProperty("usernameText");
		String passwordText=applicationProp.getProperty("passwordText");
		String searchText=applicationProp.getProperty("searchText");
		String upiName=applicationProp.getProperty("upiName");
		Login eB=new Login(driver);
		stepResult=eB.SignIn(driver,usernameText,passwordText);
		if (stepResult) {
			logger.log(LogStatus.PASS, "User is able to SignIn with the following Username :"+usernameText+" and password :"+passwordText);
			assertTrue(stepResult);
		}else{
			logger.log(LogStatus.FAIL, "User is unable to SignIn with the following Username :"+usernameText+" and password :"+passwordText);
			assertEquals(stepResult, true);
		}
		ProductPage eBPP=new ProductPage(driver);
		stepResult=eBPP.searchData(driver,searchText);
		if (stepResult) {
			logger.log(LogStatus.PASS, "Search operation was successful with the following search data :"+searchText);
			assertTrue(stepResult);
		}else{
			logger.log(LogStatus.FAIL, "Search operation was unsuccessful with the following search data :"+searchText);
			assertEquals(stepResult, true);
		}
		
		stepResult=eBPP.selectingSearchResults(driver);
		if (stepResult) {
			logger.log(LogStatus.PASS, "Selection of random product from the search results was successful");
			assertTrue(stepResult);
		}else{
			logger.log(LogStatus.FAIL, "Selection of random product from the search results was unsuccessful");
			assertEquals(stepResult, true);
		}
		stepResult=eBPP.proceedOrder(driver);
		if (stepResult) {
			logger.log(LogStatus.PASS, "Order was reviewed and successfully proceeded till payment page");
			assertTrue(stepResult);
		}else{
			logger.log(LogStatus.FAIL, "Order was not proceeded till payment page");
			assertEquals(stepResult, true);
		}
		PaymentPage eBP=new PaymentPage(driver);
		stepResult=eBP.processPayment(driver,upiName);
		if (stepResult) {
			logger.log(LogStatus.PASS, "Payment operation was successful");
			//assertTrue(stepResult);
		}else{
			logger.log(LogStatus.FAIL, "Payment operation was unsuccessful as payment details were not given");
			assertEquals(stepResult, true);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	 public void getResult(ITestResult result){
	 if(result.getStatus() == ITestResult.FAILURE){
	 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
	 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
	 }else if(result.getStatus() == ITestResult.SKIP){
	 logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
	 }
	 extent.endTest(logger);
	 }
	 @AfterTest
	 public void endReport(){
	                extent.flush();
	                extent.close();
	    }
	
}




