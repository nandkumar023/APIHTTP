package com.API.Utilities;

import org.testng.ITestResult;
import org.testng.SkipException;

import com.API.Base.APIExtentManager;
import com.API.Base.BaseTest;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Shivakumar
 *
 */

public class ExceptionHandling {

	private int result;

	public enum exceptionTypes {
		EXCEPTION, SKIPEXCEPTION

	}

	public void catchBlockException(Exception ex) throws Exception {
		String extype = ex.getClass().getSimpleName();
		switch (exceptionTypes.valueOf(extype.replace(" ", "").toUpperCase())) {
		case EXCEPTION:
			result = ITestResult.FAILURE;
			if (result == ITestResult.SKIP)
				BaseTest.SKIPPED_TC_COUNTER = BaseTest.SKIPPED_TC_COUNTER + 1;
			else
				BaseTest.FAILED_TC_COUNTER = BaseTest.FAILED_TC_COUNTER + 1;
			if (result != ITestResult.SKIP) {
				if (ex.getMessage().contains("Error Response"))
					APIExtentManager.apitest.log(LogStatus.FAIL, Utils.response.getStatusCode() + " "
							+ Utils.response.getStatusLine() + "<div id=error_response>" + ex.getMessage() + "</div>"
							+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
							"<pre>" + JsonWriter.formatJson(Utils.response.asString()) + "</pre>");
				else
					APIExtentManager.apitest.log(LogStatus.FAIL,
							Utils.response.getStatusCode() + " " + Utils.response.getStatusLine(),
							"<pre>" + JsonWriter.formatJson(Utils.response.asString()) + "</pre>");
				ex.getMessage();
				ex.printStackTrace();
				System.out.println(
						"*********************************************************************************************************");
			}
			throw new Exception(ex.getMessage());
		case SKIPEXCEPTION:
			result = ITestResult.SKIP;
			if (result == ITestResult.SKIP) {
				BaseTest.SKIPPED_TC_COUNTER = BaseTest.SKIPPED_TC_COUNTER + 1;
				new LogUtils().log().info("Stack track of the Exception" + System.lineSeparator());
				System.err.println("An Skipping Exception was caught!");
				ex.printStackTrace();
				System.out.println(
						"*********************************************************************************************************");
				APIExtentManager.apitest.log(LogStatus.SKIP, ex.getMessage(), "");
				throw new SkipException(ex.getMessage());
			}
			break;
		default:
			System.out.println("---- " + extype + " ----");
			result = ITestResult.FAILURE;
			if (result == ITestResult.SKIP)
				BaseTest.SKIPPED_TC_COUNTER = BaseTest.SKIPPED_TC_COUNTER + 1;
			else
				BaseTest.FAILED_TC_COUNTER = BaseTest.FAILED_TC_COUNTER + 1;
			APIExtentManager.apitest.log(LogStatus.FAIL, ex.getMessage(), "");
			if (result != ITestResult.SKIP) {
				System.out.println("print exception1 " + ex.getMessage());

				if (ex.getMessage().contains("Error Response"))
					APIExtentManager.apitest.log(LogStatus.FAIL, Utils.response.getStatusCode() + " "
							+ Utils.response.getStatusLine() + "<div id=error_response>" + ex.getMessage() + "</div>"
							+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
							"<pre>" + JsonWriter.formatJson(Utils.response.asString()) + "</pre>");
				else
					APIExtentManager.apitest.log(LogStatus.FAIL,
							Utils.response.getStatusCode() + " " + Utils.response.getStatusLine(),
							"<pre>" + JsonWriter.formatJson(Utils.response.asString()) + "</pre>");
				ex.getMessage();
				ex.printStackTrace();
				System.out.println(
						"*********************************************************************************************************");
			}
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
}
