package com.wipro.automation.snow.listeners;

import java.lang.reflect.Field;
import java.util.List;

import org.testng.IExecutionListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentTest;


public class TestResultListener implements ITestListener, IExecutionListener, IMethodInterceptor
{
	
	@Override
	public void onTestSuccess(ITestResult result)
	{
		try {
			Field[] test = result.getTestClass().getRealClass().getFields();
			try {
				System.out.println("Before");
				String te = (String) result.getTestClass().getRealClass().getField("test").get("name");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Listener : " + result.getTestClass().getRealClass().getField("test").get("test"));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTestFailure(ITestResult result)
	{
		
	}
	
	@Override
	public void onTestSkipped(ITestResult result)
	{
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
		
	}
	
	@Override
	public void onStart(ITestContext context)
	{
		
	}
	
	@Override
	public void onFinish(ITestContext context)
	{
		
	}
	
	@Override
	public void onTestStart(ITestResult result)
	{
		
	}

	
	
	
	
	@Override
	public void onExecutionStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
