package scripts;

import java.lang.reflect.Method;

import modules.Reporting;
import modules.ReusableMethods;

public class InitialDriver 
{
public static String path;
	
	static String[][] recData;
	public static int firefoxColumn=0,chromeColumn=0,statusC=0,TCColumn=0,statusF=0;
	
	public static void main(String[] args) throws Exception
	{
		path=System.getProperty("user.dir");
		path=path+"/src/test/java";
		
		recData= ReusableMethods.readDataFromXl();
		ReusableMethods.setColumnNumber(recData);
		Reporting.setLogger();
		Scripts.DataProps=ReusableMethods.readProperties("Data");
		ReusableMethods.elementProps=ReusableMethods.readProperties("elements");
		
		//Running every test case from the excel sheet based on the browser
		for(int i=1; i<recData.length; i++)
		{
			//checking if we need to run or not
			if(recData[i][firefoxColumn].equalsIgnoreCase("Y"))
			{
				ReusableMethods.status="PASS";
				Scripts.browser="FireFox";
				Method testScript = Scripts.class.getMethod(recData[i][TCColumn]);
				testScript.invoke(testScript);
				ReusableMethods.writeDataToXL(i,statusF,statusF);
			}
			if(recData[i][chromeColumn].equalsIgnoreCase("Y"))
			{
				Scripts.browser="Chrome";
				Method testScript = Scripts.class.getMethod(recData[i][TCColumn]);
				testScript.invoke(testScript);
				ReusableMethods.writeDataToXL(i,statusC,statusC);
			}
		}
	}

}
