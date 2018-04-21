package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import scripts.InitialDriver;
import scripts.Scripts;

public class ReusableMethods 
{
	public static String status;
	static String logMsg,FailMsg="";
	static HashMap<String, By> elements=new HashMap<String, By>();
	public static Properties elementProps;
	
	public static void setColumnNumber(String[][] recData)
	{
		//Getting the column for browser and status
		for(int j=0;j<recData[0].length;j++)
		{
			if(recData[0][j].equalsIgnoreCase("Test Case Name"))
				InitialDriver.TCColumn=j;
			if(recData[0][j].equalsIgnoreCase("Run on Firefox"))
				InitialDriver.firefoxColumn=j;
			if(recData[0][j].equalsIgnoreCase("Run on Chrome"))
				InitialDriver.chromeColumn=j;
			if(recData[0][j].equalsIgnoreCase("Status on Chrome"))
				InitialDriver.statusC=j;
			if(recData[0][j].equalsIgnoreCase("Status on FireFox"))
				InitialDriver.statusF=j;
		}
	}
	
	public static Properties readProperties(String type) throws IOException
	{
		if(type=="Data")
		{
			File file = new File(InitialDriver.path+"/data/data.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			return properties;
		}
		if(type=="elements")
		{
			File file = new File(InitialDriver.path+"/data/elements.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			return properties;
		}
		return null;
	}
	
	public static String[][] readDataFromXl() throws IOException
	{
		String dt_path=InitialDriver.path+"/data/TestSuit.xls";
		FileInputStream fs = new FileInputStream (new File(dt_path));
		
		HSSFWorkbook wb= new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheet("Sheet1");
		
		int trow= sheet.getLastRowNum()+1;
		int tcol=sheet.getRow(0).getLastCellNum();
		
		String [][]str=new String[trow][tcol];
		
		for(int i=0;i<trow;i++)
			for(int j=0;j<tcol;j++)
				str[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();	
		System.out.println();
		return str;
	}
	
	public static void writeDataToXL(int row, int column, int statusColumn) throws IOException 
	{
		String dt_path=InitialDriver.path+"/data/TestSuit.xls";
		FileInputStream fs = new FileInputStream (new File(dt_path));
		
		HSSFWorkbook wb= new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheet("Sheet1");
		
		HSSFCellStyle csR=wb.createCellStyle();
		csR.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		csR.setFillForegroundColor(IndexedColors.RED.index);
		
		
		HSSFCellStyle csG=wb.createCellStyle();
		csG.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		csG.setFillForegroundColor(IndexedColors.GREEN.index);
		
		//csR.setFillPattern(HSSFCellStyle.);
		
		String msg = "NA";
		if(status.equalsIgnoreCase("PASS"))
		{
			sheet.getRow(row).getCell(column).setCellStyle(csG);
			msg="Pass";
		}
		if(status.equalsIgnoreCase("FAIL"))
		{
			sheet.getRow(row).getCell(column).setCellStyle(csR);
			msg=sheet.getRow(row).getCell(statusColumn).getStringCellValue();
			msg=msg+" , "+FailMsg;
		}
		sheet.getRow(row).getCell(statusColumn).setCellValue(msg);
		FileOutputStream Fw= new FileOutputStream(new File(dt_path));
		wb.write(Fw);
		Fw.close();
	}
	
	public static By getElements(String elementName)
	{
		if(elements.containsKey(elementName))
			return elements.get(elementName);
		else
		{
			String property=elementProps.getProperty(elementName);
			String[] split=property.split(":");
			By by = null;
			switch(split[0])
			{
				case "id":
					by=By.id(split[1]);
					break;
				case "Xpath":
					by=By.xpath(split[1]);
					break;
				case "link":
					by=By.linkText(split[1]);
					break;
				case "class":
					by=By.className(split[1]);
					break;
			}
			elements.put(elementName, by);
			return elements.get(elementName);
		}
		
	}

	public static void ValidatePageTitle(String string) 
	{
		String title=Scripts.driver.getTitle();
		
		if(title.contains(string))
		{
			status="Pass";
			logMsg="Pass: The page title Displays "+string;
			Reporting.logger.log(Status.PASS,MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
		}
		else
		{
			status="Fail";
			logMsg="Fail: The page title displays "+title+" instead of "+string;
			FailMsg=FailMsg+logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
		}
	}

	public static void EnterTextBox(String element,String value)
	{
		WebElement ele=Scripts.driver.findElement(getElements(element));
		ele.sendKeys(value);
		logMsg=value+" entered in "+ element + " field";
		Reporting.logger.log(Status.INFO, logMsg);
	}
	
	public static void PerformClick(String element) throws InterruptedException
	{
		Scripts.driver.findElement(getElements(element)).click();
		String msg=" "+ element + " is Clicked";
		Reporting.logger.log(Status.INFO, msg);
		Thread.sleep(5000);
//		Scripts.driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
	}
	
	public static void ValidateTextArea(String element,String value)
	{
		String text=Scripts.driver.findElement(getElements(element)).getText();
		if(text.contains(value))
		{
			status="Pass";
			logMsg="Pass: "+element+" displays "+ value;
			Reporting.logger.log(Status.PASS,MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
		}
		else
		{
			status="Fail";
			logMsg="Fail: "+element+" displays "+text+" instead of "+ value;
			FailMsg=FailMsg+logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
		}
		
	}
	
	public static void SelectDropDown(String element,String value)
	{
		WebElement ele=Scripts.driver.findElement(getElements(element));
		
		Select select3=new Select(ele);
		List<WebElement> options=select3.getOptions();
		for(WebElement opt:options)
			if(opt.getText().equalsIgnoreCase(value))
			{
				opt.click();
				String msg=" "+ element + " is Clicked";
				Reporting.logger.log(Status.INFO, msg);
				return;
			}
	}
	
	public static void ValidateElement(String element)
	{
		WebElement ele=Scripts.driver.findElement(getElements(element));
		
		if(ele.isDisplayed())
		{
			status="Pass";
			logMsg="Pass: "+element+" is displayed ";
			Reporting.logger.log(Status.PASS,MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
		}
		else
		{
			status="Fail";
			logMsg="Fail: "+element+" not displayed ";
			FailMsg=FailMsg+logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
		}				
	}
	
	
	public static void ValidateTextBox(String element, String value)
	{
		WebElement ele =Scripts.driver.findElement(getElements(element));
		String text=ele.getText();
		if(text.equals(value))
		{
			status="Pass";
			logMsg="Pass: "+element+" displays "+ value;
			Reporting.logger.log(Status.PASS,MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
		}
		else
		{
			status="Fail";
			logMsg="Fail: "+element+" displays "+text+" instead of "+ value;
			FailMsg=FailMsg+logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
		}
	}
	
	
}
