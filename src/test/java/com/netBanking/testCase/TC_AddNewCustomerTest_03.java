package com.netBanking.testCase;

import org.testng.annotations.Test;

import com.netBanking.pageObject.AddCustomerPage;
import com.netBanking.pageObject.LoginPage;

public class TC_AddNewCustomerTest_03 extends BaseClass {

	@Test
	public void addNewCustomer() throws InterruptedException {

		LoginPage lp = new LoginPage(driver);
		lp.setUserName(p.getProperty("username"));
		logger.info("**** Entered Username ****");
		
		lp.setPassword(p.getProperty("password"));
		logger.info("**** Entered password ****");
		lp.clickSubmit();
		logger.info("**** Logged in successfully ****");

		Thread.sleep(3000);

		AddCustomerPage addcust = new AddCustomerPage(driver);

		addcust.clickAddNewCustomer();
		logger.info("**** Providing customer details ****");
		addcust.custName("Sohil");
		addcust.custgender("male");
		addcust.custdob("10", "15", "1995");
		Thread.sleep(3000);
		addcust.custaddress("India");
		addcust.custcity("Mumbai");
		addcust.custstate("Maharashtra");
		addcust.custpinno("400001");
		addcust.custtelephoneno("9876543210");

		String email = randomString() + "@gmail.com";
		addcust.custemailid(email);

		addcust.custpassword("abcdef");
		addcust.custsubmit();

		Thread.sleep(5000);
		
		boolean res = driver.getPageSource().contains("Customer Registered Successfully!!!");
		
		logger.info("**** Verifying new customer added ****");
		if(res==true) {
			assert true;
			logger.info("**** New Customer added successfully ****");
		}
		else {
			assert false;
			logger.error("**** Failed to add new customer ****");
		}

	}


}
