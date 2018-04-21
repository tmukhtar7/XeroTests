package modules;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import scripts.InitialDriver;

public class Reporting 
{
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	public static void setLogger()
	{
		String reportPath=InitialDriver.path+"/data/Report.html";
		ExtentHtmlReporter reporter= new ExtentHtmlReporter(reportPath);
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		
//		extent.setSystemInfo("HostName", "");
//		extent.setSystemInfo("Environment", "");
		extent.setSystemInfo("UserName", "Tahmina");
		
		reporter.config().setDocumentTitle("Tests Reports");
		reporter.config().setReportName("Functional Testing of Salesforce");		
	}
	
	public static void startReport(String testName)
	{
		logger=extent.createTest(testName);
		System.out.println(testName);
	}

	public static void endReport()
	{
		extent.flush();
	}
}
