package com.netBanking.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class myListener implements ITestListener {

	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@Override
	public void onTestStart(ITestResult result) {

		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/myReports.html");

		sparkReporter.config().setDocumentTitle("Automation_Testing");
		sparkReporter.config().setReportName("Function_testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Computer Name","LocalHost");
		extent.setSystemInfo("Tester Name","aamir");
		extent.setSystemInfo("OS","Windows 10");
		extent.setSystemInfo("Browser","Chrome");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("Build Number","1.1.0");
		extent.setSystemInfo("Sprint Number","Sprint-1");
		extent.setSystemInfo("Team Name","NetBanking");
		

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
