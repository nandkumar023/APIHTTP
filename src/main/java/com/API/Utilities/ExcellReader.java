package com.API.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Shivakumar
 *
 */

public class ExcellReader {

	@SuppressWarnings("resource")
	public ArrayList<String> getDataFromExcellSheet(String sheetName, String title, String TestCaseName)
			throws IOException {
		ArrayList<String> al = new ArrayList<String>();
		FileInputStream file = new FileInputStream(
				"Resources/demoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				// 1.Identify Test Cases column by scanning the entire first row
				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cells = firstRow.cellIterator();

				int k = 0;
				int column = 0;
				while (cells.hasNext()) {
					Cell cellValue = cells.next();
					if (cellValue.getStringCellValue().equalsIgnoreCase(title)) {
						// desired column
						System.out.println("Working");
						column = k;
					}
					k++;
				}

				System.out.println(column);
				// Once Column is identified then scan entire test case column to identify
				// purchase test case row
				while (rows.hasNext()) {
					Row singleRow = rows.next();
					if (singleRow.getCell(column).getStringCellValue().equalsIgnoreCase(TestCaseName)) {
						// After we grab purchase test case row = pull all the data of that row and feed
						// into test
						Iterator<Cell> ceelsAtTestCaseNameRow = singleRow.cellIterator();

						while (ceelsAtTestCaseNameRow.hasNext()) {
							Cell value = ceelsAtTestCaseNameRow.next();
							if (value.getCellType() == CellType.STRING) {
								al.add(value.getStringCellValue());
							} else {
								al.add(NumberToTextConverter.toText(value.getNumericCellValue()));
							}
						}
					}
				}
			}
		}
		return al;
	}

}
