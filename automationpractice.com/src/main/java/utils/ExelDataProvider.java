package utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExelDataProvider {
	XSSFWorkbook wb;
	
	public ExelDataProvider() 
	{
		File src=new File ("./TestData/Data.xlsx");
		try {
			FileInputStream fis=new FileInputStream(src);
			
			wb=new XSSFWorkbook(fis);
		
		} catch (Exception e) {
			System.out.println("Unable to read Excel File "+e.getMessage());
			
		}
	}
	public String getStringData(String sheetName,int row , int column)
	{
		return wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
	}
	
	public int getIntData(String sheetName,int row , int column)
	{
		return (int)wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
	}
	

}
