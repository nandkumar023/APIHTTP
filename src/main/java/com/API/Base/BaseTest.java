package com.API.Base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import com.API.Utilities.EndResultAPI;
import com.API.Utilities.Utils;

/**
 * @author Shivakumar
 *
 */

public class BaseTest {
	public static String PARENT_TEST_RESULT_FOLDER_PATH;
	public static String ExtendReportPath;
	public static String reportName;
	public static String start;
	public static String end;
	static String starttime;
	public static Integer PASSED_TC_COUNTER = 0, FAILED_TC_COUNTER = 0, SKIPPED_TC_COUNTER = 0;
	public static List<String> userType;
	public static String strAPIUrl;
	public static String OBAPI_host;
	public static TreeMap<String, String> testDataMap;
	public static String Duration;
	SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yy hh:mm:ss.sss aa");
	public static String BROWSERNAME;
	public static String VERSION;
	public static String BankingPlatform = "Y";
	public static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	public static String BASE_URL;
	public static List<String> usrCIF;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		userType = new ArrayList<String>();
		reportName = "";
		reportName = this.getClass().getName().replace(".", " ").split(" ")[1];
		String name = this.getClass().getName().replace(".", " ").split(" ")[1];
		System.out.println("Report Name " + name);
		userType.add("API AUTOMATION");
		BASE_URL = new Utils().getGlobalValues("baseUrl");
		new APIExtentManager().GenerateEx_report();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		APIExtentManager.apiextent.endTest(APIExtentManager.apitest);
		APIExtentManager.apiextent.flush();
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception {
		PARENT_TEST_RESULT_FOLDER_PATH = new GenerateReportParentFolder().GenerateParentTestResultWrapper();
		start = dtf.format(Calendar.getInstance().getTime());
		usrCIF = new ArrayList<>();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		end = dtf.format(Calendar.getInstance().getTime());
		Date d1 = dtf.parse(start);
		Date d2 = dtf.parse(end);
		long difference_In_Time = d2.getTime() - d1.getTime();
		long difference_In_Timwe = (difference_In_Time / 1000);
		long difference_In_Seconds = (difference_In_Time / 1000) % 60;
		long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
		long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		Duration = difference_In_Days + " Days, " + difference_In_Hours + " Hrs, " + difference_In_Minutes + " Min, "
				+ difference_In_Seconds + " Sec and " + difference_In_Timwe + " MS";
		new EndResultAPI().createExecutionPrintSummary();
		new ExecutionStatusReport().generateAutomationExecutionReport();
		new EndResultAPI().PrintExecutionSummary();
		if (new Utils().getGlobalValues("EmailAutomationExecutionReport").equalsIgnoreCase("Yes"))
			new EmailConfiguration().sendingExecutionStatusReportViaEmail();
	}

	public String getDateTime1() {
		return dateTime.get();
	}

}
