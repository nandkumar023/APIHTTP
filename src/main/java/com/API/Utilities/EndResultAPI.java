package com.API.Utilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.API.Base.BaseTest;
import com.API.Base.BaseTestUIConstant;
import com.API.Others.Block;
import com.API.Others.Board;
import com.API.Others.Table;

/**
 * @author Shivakumar
 *
 */

public class EndResultAPI {
	private SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
	private SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss.sss aa");
	private static String company;
	private static List<String> t1Headers;
	private static List<List<String>> t1Rows;
	private static String t2Desc;
	private static List<String> t2Headers;
	private static List<List<String>> t2Rows;
	private static List<Integer> t2ColWidths;
	private static String t3Desc;
	private static List<String> t3Headers;
	private static List<List<String>> t3Rows;
	private static String summary;
	private static String summaryVal;
	private final static String sign1 = System.getProperty("user.name") + "\n" + "" + "---------------------\n"
			+ "PROFINCH EXTERNAL\n";
	private final static String sign2 = "" + "---------------------\n" + "Bank Banking, INDIA BY\n";
	private final static String advertise = "© Profinch Solutions Pvt. Ltd. 2021";

	public void createExecutionPrintSummary() throws Exception {
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yy hh:mm:ss.sss aa");
		Date d1 = dtf.parse(BaseTest.start);
		Date d2 = dtf.parse(BaseTest.end);
		long difference_In_Time = d2.getTime() - d1.getTime();
		long difference_In_Timwe = (difference_In_Time / 1000);
		long difference_In_Seconds = (difference_In_Time / 1000) % 60;
		long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
		long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		BaseTestUIConstant.DurationofExecution = difference_In_Days + " Days, " + difference_In_Hours + " Hrs, "
				+ difference_In_Minutes + " Min, " + difference_In_Seconds + " Sec and " + difference_In_Timwe + " MS";
		company = "" + "PROFINCH SOLUTIONS PVT. LTD\n" + "www.profinch.com\n" + " \n"
				+ "API AUTOMATION EXECUTION SUMMARY REPORT\n" + " \n";
		t1Headers = Arrays.asList("REPORT INFO", "CUSTOMER");
		t1Rows = Arrays.asList(
				Arrays.asList("DATE: " + new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", java.util.Locale.ENGLISH)
						.format(Calendar.getInstance().getTime()), "Bank Banking, INDIA"),
				Arrays.asList("Product scope : BANK", BaseTest.BASE_URL));
		t2Desc = "EXECUTION SUMMARY";
		t2Headers = Arrays.asList("PARAM", "VALUE");
		t2Rows = Arrays.asList(Arrays.asList("User Name", System.getProperty("user.name")),
				// Arrays.asList("Browser Name", BaseTest.BROWSERNAME + " - " +
				// BaseTest.VERSION),
				Arrays.asList("Start Date", dateformat.format(dtf.parse(BaseTest.start)).toString()),
				Arrays.asList("Start Time", timeformat.format(dtf.parse(BaseTest.start)).toString()),
				Arrays.asList("End Date", dateformat.format(dtf.parse(BaseTest.end)).toString()),
				Arrays.asList("End Time", timeformat.format(dtf.parse(BaseTest.end)).toString()),
				Arrays.asList("Total Time Taken (Overall)", BaseTestUIConstant.DurationofExecution));
		Integer GD = BaseTest.PASSED_TC_COUNTER + BaseTest.FAILED_TC_COUNTER + BaseTest.SKIPPED_TC_COUNTER;
		t2ColWidths = Arrays.asList(39, 40);
		t3Desc = "Run: Documentation";
		t3Headers = Arrays.asList("PARAM", "COUNT");
		t3Rows = Arrays.asList(Arrays.asList("Total Test Case", String.valueOf(GD)),
				Arrays.asList("Passed", String.valueOf(BaseTest.PASSED_TC_COUNTER)),
				Arrays.asList("Blocked", String.valueOf(0)),
				Arrays.asList("Failed", String.valueOf(BaseTest.FAILED_TC_COUNTER)),
				Arrays.asList("Skipped", String.valueOf(BaseTest.SKIPPED_TC_COUNTER)),
				Arrays.asList("Ignored", String.valueOf(0)));
		summary = "" + "Pass Percentage\n" + "Fail Percentage\n" + "Skip Percentage\n";
		summaryVal = "" + String.valueOf((float) ((BaseTest.PASSED_TC_COUNTER * 100) / GD) + "%") + "\n"
				+ String.valueOf((float) ((BaseTest.FAILED_TC_COUNTER * 100) / GD) + "%") + "\n"
				+ String.valueOf((float) ((BaseTest.SKIPPED_TC_COUNTER * 100) / GD) + "%") + "\n";
	}

	public void PrintExecutionSummary() {
		Board b = new Board(84);
		b.setInitialBlock(new Block(b, 80, 4, company).allowGrid(true).setBlockAlign(Block.BLOCK_LEFT)
				.setDataAlign(Block.DATA_CENTER));
		b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 82, t1Headers, t1Rows));
		b.getBlock(3).setBelowBlock(new Block(b, 80, 1, t2Desc).setDataAlign(Block.DATA_CENTER));
		b.appendTableTo(5, Board.APPEND_BELOW, new Table(b, 79, t2Headers, t2Rows, t2ColWidths));
		b.getBlock(8).setBelowBlock(new Block(b, 80, 1, t3Desc).setDataAlign(Block.DATA_CENTER));
		b.appendTableTo(10, Board.APPEND_BELOW, new Table(b, 70, t3Headers, t3Rows, t2ColWidths));
		Block summaryBlock = new Block(b, 70, 4, summary).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
		b.getBlock(13).setBelowBlock(summaryBlock);
		Block summaryValBlock = new Block(b, 12, 4, summaryVal).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
		summaryBlock.setRightBlock(summaryValBlock);
		Block sign1Block = new Block(b, 45, 4, sign1).setDataAlign(Block.DATA_BOTTOM_MIDDLE).allowGrid(false);
		summaryBlock.setBelowBlock(sign1Block);
		sign1Block.setRightBlock(new Block(b, 30, 4, sign2).setDataAlign(Block.DATA_BOTTOM_MIDDLE).allowGrid(false));
		sign1Block.setBelowBlock(new Block(b, 84, 3, advertise).setDataAlign(Block.DATA_CENTER).allowGrid(false));
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());
		System.out.println(b.invalidate().build().getPreview());
	}

}
