package com.netBanking.testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.netBanking.pageObject.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TC_LoginTest_01 extends BaseClass {

//allure annotations added for reporting

	@Description("Verify login by checking the page title")
	@Epic("Login Functionality")
	@Feature("Login Feature")
	@Story("User logs in with valid credentials")
	@Severity(SeverityLevel.BLOCKER)

	@Test
	public void loginTest() {

		logger.info("**** Starting TC_LoginTest_01 ****");
	

		LoginPage lp = new LoginPage(driver);

		lp.setUserName(p.getProperty("username"));
		logger.info("****Entered Username ****");
		lp.setPassword(p.getProperty("password"));
		logger.info("****Entered password ****");
		lp.clickSubmit();

		if (driver.getTitle().equals("Guru99 Bank Manager HomePage")) {
			Assert.assertTrue(true);
			logger.info("**** Login test passed ****");
		} else {
			Assert.assertTrue(false);
			logger.error("**** Login test failed ****");
		}
	}
}
