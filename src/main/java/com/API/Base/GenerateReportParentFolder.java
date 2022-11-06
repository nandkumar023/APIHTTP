package com.API.Base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shivakumar
 *
 */

public class GenerateReportParentFolder {
	public String SSPath = "C:";

	public String GenerateParentTestResultWrapper() throws IOException {
		List<String> folderPaths = new ArrayList<String>();
		String testResultFolder = SSPath + "/API_Automation_Test_Results";
		folderPaths.add(testResultFolder);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeformatter1 = new SimpleDateFormat("HH");
		SimpleDateFormat timeformatter2 = new SimpleDateFormat("mm");
		SimpleDateFormat timeformatter3 = new SimpleDateFormat("ss");
		SimpleDateFormat timeformatter4 = new SimpleDateFormat("aa");
		String folderDate = testResultFolder + "/" + formatter.format(date);
		String timerFolder = timeformatter1.format(date) + "Hrs." + timeformatter2.format(date) + "Mins."
				+ timeformatter3.format(date) + "Sec " + timeformatter4.format(date);
		String timeFolder = folderDate + "/" + timerFolder;
		folderPaths.add(timeFolder);
		String projectFolder = timeFolder + "/" + "API";
		folderPaths.add(projectFolder);
		for (String folderPath : folderPaths) {
			if (!(new File(folderPath)).exists()) {
				Files.createDirectories(new File(folderPath).toPath());
			}
		}
		return projectFolder;
	}

}
