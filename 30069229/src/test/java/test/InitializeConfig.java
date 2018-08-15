package test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.wipro.automation.snow.logging.LogService;


public class InitializeConfig
{
	public static ExtentReports report;
	public static String reportFolder = null;
	
	
	@BeforeSuite
	public void beforeSuite() throws IOException
	{
		LogService.configure();
		report = new ExtentReports(generateReportsFolder());
	}
	
	
	@AfterSuite
	public void afterSuite()
	{
		report.flush();
		report.close();
	}
	
	
	private String generateReportsFolder() throws IOException
	{
		File f = new File("reports/"+ folderNameAsDate() +"/ExecutionReport.html");
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		
		reportFolder = f.getParent();
		System.out.println("Path -> " + f.getPath());
		System.out.println("Path -> " + f.getAbsolutePath());
		System.out.println("Path -> " + f.getCanonicalPath());
		
		return f.getPath();
	}
	
	private String folderNameAsDate()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd [HH-mm-ss]");
		//return "2018-08-14 [11-47-20]";
		return dateFormat.format(new Date());
	}
}
