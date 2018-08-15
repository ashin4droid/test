package com.wipro.automation.snow.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelReporting
{
	
	/*
	 * Excel Functions Using poi START
	 * */
	
	//private File file = null;
	
	public String dataFromExcelPoi(File f, String sheetName,int row,int col) throws Exception
	{
		FileInputStream file = new FileInputStream(f);
		
		org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
		
		try
		{
			sheet.getRow(row-1).getCell(col-1).setCellType(Cell.CELL_TYPE_STRING);
			String cellValue = sheet.getRow(row-1).getCell(col-1).getStringCellValue();
			workbook.close();
			file.close();
			return cellValue;
		}
		catch (Exception e) { e.printStackTrace(); file.close(); return null;}
		
	}
	
	public void writeToExcelPoi(File f, String sheetName, int row, int col, String value) throws Exception
	{
		
		FileInputStream file = new FileInputStream(f);
		
		org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
		//org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(sheetName);
		
		try
		{
			sheet.createRow(row-1).createCell(col-1).setCellValue(value);
			
			FileOutputStream fileOut = new FileOutputStream(f);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file.close();
		}
		catch (Exception e) { file.close(); e.printStackTrace();}
		
	}
	
	public void appendExcelPoi(File f, String sheetName, int row, int col, String value) throws Exception
	{
		FileInputStream fisread = new FileInputStream(f.getAbsolutePath());
		
		org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fisread);
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
		
		Row r = sheet.getRow(row-1) == null ? sheet.createRow(row-1) : sheet.getRow(row-1);
		Cell cell = r.createCell(col-1);
		cell.setCellValue(value);
		
		fisread.close();
		FileOutputStream fileOut = new FileOutputStream(f);
		workbook.write(fileOut);
		fileOut.close();
	}
	
	/*
	 * Excel Functions Using poi END 
	 */
	
	
	
	
	
	
	
}
