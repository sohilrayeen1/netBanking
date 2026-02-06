package com.netBanking.testCase;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.netBanking.pageObject.LoginPage;
import com.netBanking.utilities.XLUtils;

public class TC_LoginDDT_002 extends BaseClass {

	@Test(dataProvider = "LoginData")
	public void LoginDDT(String user, String pwd) throws InterruptedException {
		// Implementation for data-driven login test will go here

		LoginPage lp = new LoginPage(driver);
		lp.setUserName(user);
		Thread.sleep(2000);
		logger.info("****Entered Username ****");
		lp.setPassword(pwd);
		Thread.sleep(2000);
		logger.info("****Entered password ****");
		lp.clickSubmit();
		Thread.sleep(2000);
		
	
		if (isAlertPresent() == true) {
			driver.switchTo().alert().accept(); // close alert
			driver.switchTo().defaultContent();
			logger.warn("**** Login failed ****");
		} else {
			logger.info("**** Login passed ****");
			lp.clickLogout();
			// wait for logout alert and accept if present
			try {
				WebDriverWait wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.alertIsPresent());
				Alert logoutAlert = driver.switchTo().alert();
				logoutAlert.accept();
				driver.switchTo().defaultContent();
			} catch (Exception e) {
				// alert not present — continue without failing the test
				logger.info("Logout alert not present or already handled; continuing");
			}
		}
	
	}
	public boolean isAlertPresent() {
		// Implementation to check if alert is present will go here
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		// Implementation for data provider to fetch login data will go here
		String path = System.getProperty("user.dir") + "/src/test/java/com/netBanking/testData/LoginData.xlsx";

		int rownum = XLUtils.getRowCount(path, "Sheet1");
		int colcount = XLUtils.getCellCount(path, "Sheet1", 1);
		
		String logindata[][] = new String[rownum][colcount];
		
		for(int i=1; i<=rownum; i++) {
			for(int j=0; j<colcount; j++) {
				logindata[i-1][j] = XLUtils.getData(path, "Sheet1", i, j);
			}
		}
		return logindata;
		
	}
}