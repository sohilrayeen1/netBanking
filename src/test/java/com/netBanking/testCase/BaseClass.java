package com.netBanking.testCase;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static Logger logger;
	public Properties p;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) throws IOException {
		logger = LogManager.getLogger("netBanking");

		// loading config.properties file

		FileReader file = new FileReader(System.getProperty("user.dir") + "\\configuration\\config.properties");
		p = new Properties();
		p.load(file);

		if (br.equals("chrome")) {
			// ensure matching chromedriver binary is available
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (br.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (br.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("Browser not supported");
		}

		// Navigate to application URL from config
		String url = p.getProperty("baseURL");
		if (url != null && !url.trim().isEmpty()) {
			driver.get(url.trim());
		} else {
			System.out.println("baseURL not set in configuration/config.properties");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(p.getProperty("baseURL"));
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public static String randomString() {
		@SuppressWarnings("deprecation")
		String rand = RandomStringUtils.randomAlphabetic(5);
		return rand;
	}

	public static String randomNum() {
		@SuppressWarnings("deprecation")
		String rand1 = RandomStringUtils.randomNumeric(5);
		return rand1;
	}

}