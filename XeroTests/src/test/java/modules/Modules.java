package modules;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;

import scripts.InitialDriver;
import scripts.Scripts;

public class Modules extends ReusableMethods
{
	public static void launchApplication(String browser, String URL) throws InterruptedException 
	{
		if(browser.equalsIgnoreCase("FireFox"))
		{
			String DriverPath=InitialDriver.path+"/framework/geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", DriverPath);
			Scripts.driver= new FirefoxDriver();
		}
		if(browser.equalsIgnoreCase("Chrome"))
		{
			String DriverPath=InitialDriver.path+"/framework/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", DriverPath);
			Scripts.driver= new ChromeDriver();
			Scripts.driver.manage().window().maximize();
		}
		Scripts.driver.get(URL);
		Thread.sleep(3000);		
	}

	public static void validateForClearFields() throws InterruptedException 
	{
		Thread.sleep(15000);
		PerformClick("getStarted");
		ValidateTextArea("noFirstName", Scripts.DataProps.getProperty("noFirstName"));
		ValidateTextArea("noLastName", Scripts.DataProps.getProperty("noLastName"));
		ValidateTextArea("noEmail", Scripts.DataProps.getProperty("noEmail"));
		ValidateTextArea("noPhoneNo", Scripts.DataProps.getProperty("noPhoneNo"));
	}

	public static void validateInvalidEmail() throws InterruptedException 
	{
		EnterTextBox("firstName", Scripts.DataProps.getProperty("firstName"));
		EnterTextBox("lastName", Scripts.DataProps.getProperty("firstName"));
		EnterTextBox("newEmail", Scripts.DataProps.getProperty("firstName"));
		EnterTextBox("phoneNo", Scripts.DataProps.getProperty("phoneNo"));
		SelectDropDown("countryDropDown",Scripts.DataProps.getProperty("country"));
		Thread.sleep(8000);
		PerformClick("agreeTerms");
		PerformClick("getStarted");
		ValidateTextArea("invalidEmail", Scripts.DataProps.getProperty("invalidEmail"));
		
	}

	public static void validateUnclickedPolicies() throws InterruptedException 
	{
		EnterTextBox("newEmail", Scripts.DataProps.getProperty("newEmail"));
		Thread.sleep(8000);
		PerformClick("agreeTerms");
		PerformClick("getStarted");
		ValidateElement("termsCheckbox");
	}

	public static void ValidateNewTab(String link, String pageTitle) throws InterruptedException 
	{
		PerformClick(link);
		Set<String> handles=Scripts.driver.getWindowHandles();
		Iterator<String> it=handles.iterator();
		while (it.hasNext())
		{
			String parent = it.next();
			String newwin = it.next();
			Scripts.driver.switchTo().window(newwin);
			ValidatePageTitle(pageTitle);
			Scripts.driver.close();
			Scripts.driver.switchTo().window(parent);
		}
		
	}

	public static void logIn(String email, String psw) throws InterruptedException 
	{
		EnterTextBox("email",email);
		EnterTextBox("password",psw);
		ValidatePageOnClick("login", "Xero | Dashboard");
	}

	public static void ValidatePageOnClick(String element, String pageTitle) throws InterruptedException 
	{
		PerformClick(element);
		ValidatePageTitle(pageTitle);
		
	}

	public static void ValidateDropDown(String element, String element2) throws InterruptedException 
	{
		PerformClick(element);
		Thread.sleep(4000);
		ValidateElement(element2);
	}

	public static void ValidateFrame(String element,String element2, String frameId) throws InterruptedException 
	{
		PerformClick(element);
		Scripts.driver.switchTo().frame(frameId);
		ValidateElement(element2);
		Scripts.driver.switchTo().defaultContent();
	}

	public static void GoToMyXero() throws InterruptedException 
	{
		PerformClick("navMenu");
		Thread.sleep(5000);
		PerformClick("myXero");
		ValidatePageTitle("My Xero | Home");
	}

	public static void addNewOrgDetails(String property, String property2, String property3, String property4) throws InterruptedException, AWTException 
	{
		PerformClick("addOrg");
		EnterTextBox("nameOrg",property);
		EnterTextBox("countryTax",property2);
		EnterTextBox("time",property3);
		Thread.sleep(8000);
		Robot r=new Robot();
		if(Scripts.browser.equals("FireFox"))
		{
			
			r.keyPress(KeyEvent.VK_ENTER);
		}
		else
		{
			Keyboard key=((HasInputDevices)Scripts.driver).getKeyboard();
			key.pressKey(Keys.ENTER);
		}
		EnterTextBox("buissness",property4);
		Thread.sleep(8000);
		if(Scripts.browser.equals("FireFox"))
		{
			Thread.sleep(3000);
			r.keyPress(KeyEvent.VK_ENTER);
		}
	}

	public static void addBillingInfo(String property, String property2, String property3, String property4) throws InterruptedException 
	{
		PerformClick("billing");
		EnterTextBox("street", property);
		EnterTextBox("city", property2);
		SelectDropDown("state", property3);
		EnterTextBox("zipcode", property4);
		PerformClick("continue");
		ValidatePageTitle("Xero | Confirm");
	}
	
	

}
