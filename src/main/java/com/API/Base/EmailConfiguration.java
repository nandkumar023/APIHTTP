package com.API.Base;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.API.Utilities.Utils;

/**
 * @author Shivakumar
 *
 */

public class EmailConfiguration {
	private final String SUBJECT = "API Automation Execution Report ";
	private final String HeadingMailBody = "Banking Project Report Summary";
	private final Integer PASS_TC = BaseTest.PASSED_TC_COUNTER;
	private final Integer FAIL_TC = BaseTest.FAILED_TC_COUNTER;
	private final Integer SKIP_TC = BaseTest.SKIPPED_TC_COUNTER;;
	private final Integer TOTAL_TC_EXECUTED = PASS_TC + FAIL_TC + SKIP_TC;
	private final String pdfReport_Path = ExecutionStatusReport.PDFPATH;

	public  void sendingExecutionStatusReportViaEmail() throws Exception {
		if (pdfReport_Path == null || pdfReport_Path.isEmpty())
			throw new Exception("PDF File Path Variable seems to be empty");
		File pdffile = new File(pdfReport_Path);
		boolean isexits = (pdffile.exists() && pdffile.isFile()) && (pdffile.isFile() && !pdffile.isDirectory());
		if (!isexits)
			throw new Exception(
					"Invalid PDF file path or pdf is not generated sucessfully or pdf directory is invalid");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss a");
		LocalDateTime now = LocalDateTime.now();
		final String usernameFrom = new Utils().getGlobalValues("EmailFromUserName");
		final String password = new Utils().getGlobalValues("EmailFromPassword");
		final String userNameTo = new Utils().getGlobalValues("EmailToUserName");
		String emailbody = HeadingMailBody;
		emailbody += "\n\n Environment: ";
		emailbody += "\n\nTest Cases Executed: "+TOTAL_TC_EXECUTED;
		emailbody += "\nTest Cases Passed: "+PASS_TC;
		emailbody += "\nTest Cases Failed: "+FAIL_TC;
		emailbody += "\nTest Cases Skipped: "+SKIP_TC;
		emailbody += "\n\nAttached is the detailed execution report. Please reach out to Automation Team for more details.";
		emailbody += "\n\nRegards,\nAutomation Team";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.port", "465");
		//props.put("mail.transport.protocol", "smtp");
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtp.socketFactory.fallback", "true");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usernameFrom, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(usernameFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userNameTo));
			message.setSubject(SUBJECT+" || "+dtf.format(now));
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(emailbody);
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = pdfReport_Path;
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart2);
			multipart.addBodyPart(messageBodyPart1);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("=====Email Sent Successfully=====");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
