package scripts;

import java.awt.AWTException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import modules.Modules;
import modules.Reporting;
import modules.ReusableMethods;

public class Scripts extends ReusableMethods
{
	public  static WebDriver driver;
	public static String browser;
	public static String status;
	public static Properties DataProps;
	
	public static void LoginToXERO_01A() throws InterruptedException
	{
		Reporting.startReport(("TestID01A: Login to XERO Application on "+browser));
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		ValidatePageTitle("Login | Xero");
		EnterTextBox("email",DataProps.getProperty("email"));
		EnterTextBox("password",DataProps.getProperty("password"));
		PerformClick("login");
		ValidatePageTitle("Xero | Dashboard");
		
		Reporting.endReport();
		driver.close();	
	}
	
	public static void LoginIncorrectPsw_01B() throws InterruptedException
	{
		Reporting.startReport("TestID01B: Login to XERO Application with Incorrect Password on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		ValidatePageTitle("Login | Xero");
		EnterTextBox("email",DataProps.getProperty("email"));
		EnterTextBox("password",DataProps.getProperty("incorrectPsw"));
		PerformClick("login");
		ValidateTextArea("errorMsg",DataProps.getProperty("errorMsg"));
		
		Reporting.endReport();
		driver.close();
	}

	public static void LoginIncorrectEmail_01C() throws InterruptedException
	{
		Reporting.startReport("TestID01C: Login to XERO Application with Incorrect Email on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		ValidatePageTitle("Login | Xero");
		EnterTextBox("email",DataProps.getProperty("incorrectEmail"));
		EnterTextBox("password",DataProps.getProperty("password"));
		PerformClick("login");
		ValidateTextArea("errorMsg",DataProps.getProperty("errorMsg"));
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void ForgotPassword_01D() throws InterruptedException
	{
		Reporting.startReport("TestID01D: Forgot Passwor Link on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		ValidatePageTitle("Login | Xero");
		PerformClick("forgotPsw");
		ValidatePageTitle("Forgotten Password");
		EnterTextBox("username", DataProps.getProperty("email"));
		PerformClick("sendLink");
		ValidateTextArea("sentEmail",DataProps.getProperty("forgotPswMsg"));
		
		Reporting.endReport();
		driver.close();
	}
		
	public static void SignUpToXDC_02A() throws InterruptedException
	{
		Reporting.startReport("TestID02A: Sign Up on XERO on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLTrial"));
		ValidatePageTitle("Accounting Software & Online");
		Thread.sleep(3000);
		PerformClick("freeTrial");
		ValidatePageTitle("Signup for Xero");
		EnterTextBox("firstName", DataProps.getProperty("firstName"));
		EnterTextBox("lastName", DataProps.getProperty("lastName"));
		EnterTextBox("newEmail", DataProps.getProperty("newEmail"));
		EnterTextBox("phoneNo", DataProps.getProperty("phoneNo"));
		SelectDropDown("countryDropDown",DataProps.getProperty("country"));
		Thread.sleep(5000);
		PerformClick("agreeTerms");
		PerformClick("getStarted");
		Thread.sleep(20000);
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void SignUpToXDC_02B() throws InterruptedException
	{
		Reporting.startReport("TestID02B: Validate Invalid Fields on SignUp page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLTrial"));
		ValidatePageTitle("Accounting Software & Online");
		PerformClick("freeTrial");
		ValidatePageTitle("Signup for Xero");
		
		Modules.validateForClearFields();
		Modules.validateInvalidEmail();
		Modules.validateUnclickedPolicies();
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void SignUpToXDC_02C() throws InterruptedException
	{
		Reporting.startReport("TestID02C: Validate Links on SignUp page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLTrial"));
		ValidatePageTitle("Accounting Software & Online");
		PerformClick("freeTrial");
		ValidatePageTitle("Signup for Xero");
		
		Modules.ValidateNewTab("termsLink","");
		Modules.ValidateNewTab("privacyLink","");
		
		Reporting.endReport();
		driver.quit();
	}
	
	public static void SignUpToXDC_02D() throws InterruptedException
	{
		Reporting.startReport("TestID02D: Validate FullOffer Link on SignUp page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLTrial"));
		ValidatePageTitle("Accounting Software & Online");
		PerformClick("freeTrial");
		ValidatePageTitle("Signup for Xero");
		
		Modules.ValidateNewTab("offerDetails","Offer details");
		
		Reporting.endReport();
		driver.quit();
	}
	
	public static void SignUpToXDC_02E() throws InterruptedException
	{
		Reporting.startReport("TestID02E: Validate Accountant Link on SignUp page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLTrial"));
		ValidatePageTitle("Accounting Software & Online");
		PerformClick("freeTrial");
		ValidatePageTitle("Signup for Xero");
		
		PerformClick("accountant");
		ValidatePageTitle("Sign up");
		
		Reporting.endReport();
		driver.quit();
	}
	
	public static void TestAllTabsPage_03A() throws InterruptedException
	{
		Reporting.startReport("TestID03A: Validate All Tabs on the Home Page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		
		Modules.ValidatePageOnClick("dashboard","Xero | Dashboard");
		Modules.ValidateDropDown("accounts","accountsDD");
		Modules.ValidateDropDown("reports", "reportsDD");
		Modules.ValidateDropDown("contacts", "contactsDD");
		Modules.ValidateDropDown("settings", "settingsDD");
		Modules.ValidateDropDown("new", "newDD");
		Modules.ValidatePageOnClick("files", "Xero | Files");
		Modules.ValidateFrame("notif","notifTab","post_office_frame");
		Modules.ValidateFrame("search","searchTab", "GlobalSearchApp");
		Modules.ValidateDropDown("help", "helpTab");
		ValidateElement("helpTextArea");
		
		Reporting.endReport();
		driver.quit();
	}
	
	public static void TestLogoutFunctionality_04A() throws InterruptedException
	{
		Reporting.startReport("TestID04A: Validate Logout from the Home Page on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		
		PerformClick("usernameTab");
		PerformClick("logout");
		ValidatePageTitle("Login | Xero");
		ValidateTextBox("email", DataProps.getProperty("email"));
		
		Reporting.endReport();
		driver.quit();
	}
	
	public static void TestUploadProfileImage_06A() throws InterruptedException
	{
		Reporting.startReport("TestID06A: Validate Upload profile image on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		
		PerformClick("usernameTab");
		PerformClick("profilePage");
		PerformClick("uploadImage");
		EnterTextBox("browseImage",DataProps.getProperty("profileImage"));
		ValidateElement("profileImage");
		PerformClick("upload");
				
		Reporting.endReport();
		driver.quit();
	}
	
	public static void AddAnotherOrganizationTrial_08A() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08A: Add another organization Trail version - 1  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		Modules.GoToMyXero();
		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("startTrial");
		
		Modules.GoToMyXero();
		ValidateTextArea("inTrial","In Trial");
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void AddAnotherOrganizationPaid_08B() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08B: Add another organization Paid version on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		Modules.GoToMyXero();
		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("buyNow");
		Thread.sleep(8000);
		ValidatePageTitle("Xero | Pricing");
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void AddAnotherOrganizationStarter_08C() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08C: Add another organization - Starter Plan  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		Modules.GoToMyXero();
		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("buyNow");
		Thread.sleep(6000);
		ValidatePageTitle("Xero | Pricing");
		Thread.sleep(3000);
		PerformClick("starter");		
		Modules.addBillingInfo(DataProps.getProperty("street"),DataProps.getProperty("city"),DataProps.getProperty("state"),DataProps.getProperty("zipcode"));
		Reporting.endReport();
		driver.close();
	}
	
	public static void AddAnotherOrganizationStandard_08D() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08C: Add another organization - Standard Plan  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		Modules.GoToMyXero();
		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("buyNow");
		
		PerformClick("standard");
		PerformClick("billing");
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void AddAnotherOrganizationPro_08E() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08E: Add another organization - Pro Plan  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		Modules.GoToMyXero();
		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("buyNow");
		Thread.sleep(5000);
		PerformClick("premium");
		PerformClick("billing");
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void AddAnotherOrganizationCurrent_08F() throws InterruptedException, AWTException
	{
		Reporting.startReport("TestID08E: Add another organization - Pro Plan  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));

		Modules.addNewOrgDetails(DataProps.getProperty("nameOrg"),DataProps.getProperty("countryTax"),DataProps.getProperty("time"),DataProps.getProperty("buissness"));
		PerformClick("quickBooks");
		PerformClick("bookConv");
		PerformClick("buyNow");
		ValidateTextArea("dispText", DataProps.getProperty("dispText"));
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void CheckSubscriptionBilling_10A() throws InterruptedException
	{
		Reporting.startReport("TestID10A: Check if users can lookout for their subscription and billing  on " + browser);
		
		Modules.launchApplication(browser,DataProps.getProperty("URLLogin"));
		Modules.logIn(DataProps.getProperty("email"),DataProps.getProperty("password"));
		
		PerformClick("accounts");
		PerformClick("purchases");
		String url=driver.getCurrentUrl();
		Modules.ValidateDropDown("paid", "billsTable");
		driver.get(url);
		Modules.ValidateDropDown("repeating", "billsTable");
		driver.get(url);
		Modules.ValidateDropDown("seeAll", "billsTable");
		
		Reporting.endReport();
		driver.close();
	}
}
