package com.API.Utilities;

import java.io.File;
import java.util.Base64;
import java.util.LinkedHashMap;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;
import POJO_Classes.LoginCredentilas;

/**
 * @author Shivakumar
 *
 */

public class FilloExcellReader {

	public LinkedHashMap<String, String> getTestDataFromExcellSheet(String sheetName, String tableStartRow,
			String tableStartCell, String tableEndCell) throws Exception {
		LinkedHashMap<String, String> TestDataInListMap = new LinkedHashMap<String, String>();
		String testDataFilePath = new File("Resources\\API_TestData.xlsx").getAbsolutePath().toString();
		System.out.println(testDataFilePath);
		String QueryParser = "Select * from %s";
		System.out.println(QueryParser);
		String query = null;
		query = String.format(QueryParser, sheetName.trim());
		System.out.println(query);
		com.codoid.products.fillo.Fillo fillo = new com.codoid.products.fillo.Fillo();
		System.setProperty("ROW", tableStartRow);
		tableStartCell = !tableStartCell.isEmpty() && tableStartCell != null ? tableStartCell : "1";
		System.setProperty("COLUMN", tableStartCell);
		Connection conn;
		Recordset recordSet;
		boolean row = false;

		try {
			conn = fillo.getConnection(testDataFilePath);
			recordSet = conn.executeQuery(query);
			int cellcount = !tableStartCell.isEmpty() && tableStartCell != null ? Integer.parseInt(tableStartCell) : 1;
			int recordSetcount = 0;

			while (recordSet.next()) {

				if (row)
					break;
				for (String field : recordSet.getFieldNames()) {
					if (recordSetcount == recordSet.getFieldNames().size()) {
						row = true;
						break;

					}
					recordSetcount++;
					String first_rowCell_value = field;
					tableEndCell = !tableEndCell.isEmpty() && tableEndCell != null && tableEndCell.matches("\\d+")
							? tableEndCell
							: first_rowCell_value;
					if (String.valueOf(cellcount - 1).equalsIgnoreCase(tableEndCell)
							|| first_rowCell_value.startsWith("COLUMN")) {
						row = true;
						break;
					}
					TestDataInListMap.put(field, recordSet.getField(field));
					cellcount++;

				}

			}
		} catch (FilloException Ex) {
			Ex.printStackTrace();
			throw new Exception("Test data cannot find...");
		}
		return TestDataInListMap;
	}

	public static LoginCredentilas base64Encode(final String username, final String password) {
		String cred = username + ":" + password;
		cred = Base64.getEncoder().encodeToString(cred.getBytes());
		LoginCredentilas loginCred = new LoginCredentilas();
		loginCred.setCredentials(cred);
		return loginCred;

	}

}
