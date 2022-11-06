package com.API.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.API.Base.BaseTest;

/**
 * @author Shivakumar
 *
 */

public class LogUtils {
	public static final long WAIT = 15;

	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String randomNumber() {
		Random rand = new Random();
		String num1 = String.format("%06d", rand.nextInt(10000));
		String num = "8" + num1;
		return num;
	}

	public String GenerateRandomNumber(int charLength) {
		return String.valueOf(charLength < 1 ? 0
				: new Random().nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
						+ (int) Math.pow(10, charLength - 1));
	}

	public void log(String txt) {
		BaseTest base = new BaseTest();
		String msg = Thread.currentThread().getId() + ":" + ":"
				+ Thread.currentThread().getStackTrace()[2].getClassName() + ":" + txt;
		System.out.println(msg);
		String strFile = "logs" + File.separator + "_" + File.separator + base.getDateTime1();
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(logFile + File.separator + "log.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(msg);
		printWriter.close();
	}

	public Logger log() {
		PropertyConfigurator.configure("./Configurations/log4j.properties");
		return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}
}