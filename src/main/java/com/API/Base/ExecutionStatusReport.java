package com.API.Base;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import javax.swing.UIManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataBarFormatting;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.ConditionalFormattingThreshold.RangeType;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting.IconSet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.UnitType;
import org.jfree.data.general.DefaultPieDataset;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import com.aspose.cells.AutoFitterOptions;
import com.aspose.cells.ConditionalFormattingIcon;
import com.aspose.cells.IconSetType;
import com.aspose.cells.PdfCompressionCore;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.pdf.Document;
import com.aspose.pdf.DocumentInfo;
import com.aspose.pdf.GoToAction;
import com.aspose.pdf.XYZExplicitDestination;
import com.spire.pdf.PdfDocument;

/**
 * @author Shivakumar
 *
 */

@SuppressWarnings("deprecation")
public class ExecutionStatusReport {
	private final String RELEASE_NUMBER = "X";
	private final String PARENT_REPORT_FOLDER = BaseTest.PARENT_TEST_RESULT_FOLDER_PATH;
	private final String EXECUTION_START_TIME = BaseTest.start;
	private final String EXECUTION_END_TIME = BaseTest.end;
	private final String TOTAL_TIME_TAKEN = BaseTest.Duration;
	private final Integer PASS_TC = BaseTest.PASSED_TC_COUNTER;
	private final Integer FAIL_TC = BaseTest.FAILED_TC_COUNTER;
	private final Integer SKIP_TC = BaseTest.SKIPPED_TC_COUNTER;
	private final Integer TOTAL_TC_EXECUTED = PASS_TC + FAIL_TC + SKIP_TC;
	private final Integer TOTAL_TC_EXECUTED1 = TOTAL_TC_EXECUTED.equals(0) ? 100 : TOTAL_TC_EXECUTED;
	private final String PASS_PERCENTAGE = String.valueOf(Math.round((PASS_TC * 100) / TOTAL_TC_EXECUTED1) + "%");
	private final String FAIL_PERCENTAGE = String.valueOf(Math.round((FAIL_TC * 100) / TOTAL_TC_EXECUTED1) + "%");
	private final String SKIP_PERCENTAGE = String.valueOf(Math.round((SKIP_TC * 100) / TOTAL_TC_EXECUTED1) + "%");
	private final String REPORT_NAME = "Automation Execution Report (" + RELEASE_NUMBER + ")";
	private final String EXECUTED_URL = BaseTest.BASE_URL;
	private final String AUTOMATION_TYPE = "API Automation";
	private final String BANKING_PLATFORM = "OBDX";
	private final String BROWSER_NAME = BaseTest.BROWSERNAME;
	private final String BROWSER_VERSION = BaseTest.VERSION;
	private final String REPORT_NAME_FORMAT = "Profinch_BANK_OBDX_Automation_Execution_Status_Report "
			+ DateTime.now().toString("dd-MMM-yy") + "_v1";
	private final HSSFWorkbook WORKBOOK = new HSSFWorkbook();
	private final String SHEETNAME = "( Regression Suite) Status Report ";
	private final HSSFSheet SHEET = WORKBOOK.createSheet(SHEETNAME);
	private CellStyle cellStyle = null;
	private HSSFFont fontStyle = null;
	private final HSSFPalette COLORPALETTE = WORKBOOK.getCustomPalette();
	private HSSFCell cell;
	private HSSFRow row;
	private CellRangeAddress cellAddress;
	private CreationHelper creationHelper;
	public static String PDFPATH;
	public static String sp;

	public void generateAutomationExecutionReport() throws Exception {
		PDFPATH = "";
		try {
			CipherDecrypt();
			createRowsAndCells();
			assignNewColorCodeToHSSFPalette();
			createWorkBookHeader();
			createTestExecutionSummaryBlock();
			createReleaseSummaryBlock();
			createWorkBookFooter();
			excelRestricationAndSetup();
			String xlsPath = createExcelReport();
			if (xlsPath == null || xlsPath.isEmpty())
				throw new Exception("Excel Creation Issue");
			PDFPATH = createPdfReport(xlsPath);
			if (PDFPATH == null || PDFPATH.isEmpty())
				throw new Exception(".pdf Creation Issue");
			System.err.print("xls to pdf converted successfully\n");
			convertPDFtoHTML(PDFPATH);
			System.err.print("pdf to Html converted successfully\n");
			convertPdfToImg(PDFPATH);
			System.err.print("pdf to img converted successfully\n");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Issue on generating Automation Execution Report \n" + e.getMessage());
		}
	}

	private void convertPdfToImg(String pdfPath) throws Exception {
		PdfDocument doc = new PdfDocument();
		doc.loadFromFile(pdfPath);
		BufferedImage image;
		String OutputFile = PARENT_REPORT_FOLDER + "\\" + REPORT_NAME_FORMAT + ".png";
		for (int i = 0; i < doc.getPages().getCount(); i++) {
			image = doc.saveAsImage(i);
			File file = new File(String.format(OutputFile, i));
			ImageIO.write(image, "PNG", file);
		}
		doc.close();
		BufferedImage originalImage = ImageIO.read(new File(OutputFile));
		BufferedImage newResizedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newResizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Map<RenderingHints.Key, Object> hints = new HashMap<>();
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.addRenderingHints(hints);
		Rectangle rect1 = new Rectangle(48, 50, originalImage.getWidth(), originalImage.getHeight());
		g.drawImage(originalImage, 0, 0, (int) rect1.getWidth(), (int) rect1.getHeight(), (int) rect1.getX(),
				(int) rect1.getY(), (int) rect1.getX() + (int) rect1.getWidth(),
				(int) rect1.getY() + (int) rect1.getHeight(), null);
		g.dispose();
		String s = OutputFile;
		String fileExtension = s.substring(s.lastIndexOf(".") + 1);
		ImageIO.write(newResizedImage, fileExtension, new File(OutputFile));
	}

	private void convertPDFtoHTML(String pdfPath) throws Exception {
		String inputFile = pdfPath;
		String OutputFile = PARENT_REPORT_FOLDER + "\\" + REPORT_NAME_FORMAT + ".html";
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(inputFile);
		pdf.getConvertOptions().setPdfToHtmlOptions(true);
		pdf.getConvertOptions().setPdfToXpsOptions(true);
		File outFile = new File(OutputFile);
		OutputStream outputStream = new FileOutputStream(outFile);
		pdf.saveToStream(outputStream, com.spire.pdf.FileFormat.HTML);
		pdf.close();
		File input = new File(OutputFile);
		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8", "file:///" + input.getAbsolutePath());
		Elements evalution = doc.select("#main1 >g>g> text");
		for (Element headline : evalution) {
			headline.remove();
		}
		Elements newsHeadlines = doc.select("#main1 > text");
		newsHeadlines.remove();
		Elements myOuterDiv = doc.select("#main1 > g  text[font-size]");
		for (Element element : myOuterDiv) {
			String exatt = element.attr("style");
			element.attr("style", exatt + " white-space: normal;");
		}
		myOuterDiv = doc.select("html>body");
		for (Element element : myOuterDiv) {
			String exatt = element.attr("style");
			element.attr("style", exatt + "; user-select: none;");
		}
		Files.write(Paths.get(new File(OutputFile).getAbsolutePath()), doc.outerHtml().getBytes());
		HtmlSanitizer(doc, OutputFile);
	}

	private void HtmlSanitizer(org.jsoup.nodes.Document doc, String OutputFile) throws Exception {
		Whitelist whitelist = Whitelist.simpleText().removeTags("font", "hr", "ins", "del", "center", "map", "area")
				.removeAttributes("font", "color", "face", "size")
				.removeAttributes("table", "align", "background", "bgcolor", "border", "cellpadding", "cellspacing",
						"width")
				.removeAttributes("tr", "align", "background", "bgcolor", "valign")
				.removeAttributes("th", "align", "background", "bgcolor", "colspan", "headers", "height", "nowrap",
						"rowspan", "scope", "sorted", "valign", "width")
				.removeAttributes("td", "align", "background", "bgcolor", "colspan", "headers", "height", "nowrap",
						"rowspan", "scope", "valign", "width")
				.removeAttributes("map", "name").removeAttributes("area", "shape", "coords", "href", "alt")
				.removeProtocols("area", "href", "http", "https").removeAttributes("img", "usemap")
				.removeAttributes(":all", "class", "style", "id", "dir")
				.removeProtocols("img", "src", "http", "https", "cid", "data")
				.removeProtocols("a", "href", "tel", "sip", "bitcoin", "ethereum", "rtsp");
		Cleaner cleaner = new Cleaner(whitelist);
		@SuppressWarnings("unused")
		boolean ff = cleaner.isValid(doc);
		cleaner.clean(doc);
		ff = cleaner.isValid(doc);
		Files.write(Paths.get(new File(OutputFile).getAbsolutePath()), doc.outerHtml().getBytes());
	}

	private String createPdfReport(String WorkBookPath) throws Exception {
		System.setProperty("illegal-access", "deny");
		com.aspose.cells.Workbook asposeworkbook = new com.aspose.cells.Workbook(WorkBookPath);
		asposeworkbook.parseFormulas(false);
		asposeworkbook.getSettings().setPageOrientationType(0);
		asposeworkbook.getSettings().setAuthor(System.getProperty("user.name"));
		asposeworkbook.getSettings().setAutoRecover(true);
		asposeworkbook.getSettings().setLocale(Locale.ENGLISH);
		asposeworkbook.getSettings().setPrecisionAsDisplayed(true);
		AutoFitterOptions oAutoFitterOptions = new AutoFitterOptions() {
			@Override
			public void setAutoFitMergedCells(boolean value) {
				super.setAutoFitMergedCells(true);
			}

			@Override
			public void setOnlyAuto(boolean value) {
				super.setOnlyAuto(false);
			}
		};
		asposeworkbook.calculateFormula();
		asposeworkbook.getWorksheets().get(0).autoFitRows(oAutoFitterOptions);
		int j11 = Integer.parseInt(asposeworkbook.getWorksheets().get(0).getCells().get("J11").getDisplayStringValue());
		asposeworkbook.getWorksheets().get(0).getCells().get(10, 8).setValue("");
		if (j11 >= 5) {
			asposeworkbook.getWorksheets().get(0).getCells().get(10, 8).setValue("");
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.RATING_5, 4);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(10, 8, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(20);
		} else if (j11 >= 4) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.RATING_5, 3);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(10, 8, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(20);
		} else if (j11 >= 3) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.RATING_5, 2);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(10, 8, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(20);
		} else if (j11 >= 2) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.RATING_5, 1);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(10, 8, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(20);
		} else if (j11 < 2) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.RATING_5, 0);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(10, 8, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(20);
		}
		int u17 = Integer.parseInt(asposeworkbook.getWorksheets().get(0).getCells().get("U19").getDisplayStringValue());
		asposeworkbook.getWorksheets().get(0).getCells().get("U19").setValue("");
		if (u17 >= 3) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.FLAGS_3, 2);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(18, 20, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(12);
		} else if (u17 >= 2) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.FLAGS_3, 1);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(18, 20, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(12);
			;
		} else if (u17 < 2) {
			byte[] imagedata = ConditionalFormattingIcon.getIconImageData(IconSetType.FLAGS_3, 0);
			ByteArrayInputStream stream = new ByteArrayInputStream(imagedata);
			int pictureIndex = asposeworkbook.getWorksheets().get(0).getPictures().add(18, 20, stream);
			com.aspose.cells.Picture picture = asposeworkbook.getWorksheets().get(0).getPictures().get(pictureIndex);
			picture.setLeft(3);
			picture.setTop(12);
		}
		PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
		pdfSaveOptions.setCreatedTime(com.aspose.cells.DateTime.getNow());
		pdfSaveOptions.setCustomPropertiesExport(0);
		pdfSaveOptions.setOnePagePerSheet(true);
		pdfSaveOptions.setCompliance(0);
		pdfSaveOptions.setOutputBlankPageWhenNothingToPrint(false);
		pdfSaveOptions.setPdfCompression(PdfCompressionCore.LZW);
		pdfSaveOptions.setImageResample(1000, 100);
		pdfSaveOptions.setOptimizationType(1);
		String OutputFile = PARENT_REPORT_FOLDER + "\\" + REPORT_NAME_FORMAT + ".pdf";
		asposeworkbook.save(OutputFile, pdfSaveOptions);
		Document pdfDocument = new Document(OutputFile);
		double zoom = .5;
		GoToAction actionzoom = new GoToAction(new XYZExplicitDestination(pdfDocument.getPages().get_Item(1),
				pdfDocument.getPages().get_Item(1).getMediaBox().getWidth(),
				pdfDocument.getPages().get_Item(1).getMediaBox().getHeight(), zoom));
		pdfDocument.setOpenAction(actionzoom);
		DocumentInfo docInfo = new DocumentInfo(pdfDocument);
		docInfo.setAuthor(CipherDecrypt());
		docInfo.setCreationDate(new java.util.Date());
		docInfo.setKeywords("(" + RELEASE_NUMBER + ") Automation_Execution_Status_Report.Pdf, GBK, Profinch");
		docInfo.setModDate(new java.util.Date());
		docInfo.setSubject(REPORT_NAME_FORMAT);
		docInfo.setTitle("GBK Automation Execution Status Report PDF Document Information");
		docInfo.setCreator(System.getProperty("user.name"));
		pdfDocument.save(OutputFile);
		File file = new File(OutputFile);
		PDDocument document = PDDocument.load(file);
		PDPage page = document.getPage(0);
		page.setCropBox(new PDRectangle((float) 0.0, (float) 0.0, (float) 1300, (float) 750.4008));
		document.save(new File(OutputFile));
		document.close();
		return OutputFile;
	}

	private String createExcelReport() throws Exception {
		List<String> folderPaths = new ArrayList<String>();
		folderPaths.add(PARENT_REPORT_FOLDER);
		for (String folderPath : folderPaths) {
			if (!(new File(folderPath)).exists()) {
				Files.createDirectories(new File(folderPath).toPath());
			}
		}
		String TestSummaryName = REPORT_NAME_FORMAT + ".xls";
		String OutputFile = PARENT_REPORT_FOLDER + "\\" + TestSummaryName;
		for (int i = 1; i < 10; i++) {
			File f = new File(OutputFile);
			boolean isexits = f.exists() && !f.isDirectory();
			if (isexits) {
				OutputFile = PARENT_REPORT_FOLDER + "\\" + TestSummaryName + i;
				File f1 = new File(OutputFile);
				Boolean isfileexits = f1.exists() && !f1.isDirectory();
				if (!isfileexits)
					break;
			}
			if (!isexits)
				break;
		}
		FileOutputStream out = new FileOutputStream(new File(OutputFile));
		WORKBOOK.write(out);
		System.out.println("Writesheet.xlsx written successfully");
		Thread.sleep(3000);
		return OutputFile;
	}

	private void excelRestricationAndSetup() {
		SHEET.setDisplayGridlines(false);
		SHEET.protectSheet("dakshina.moorthy@profinch.com");
	}

	private void createWorkBookFooter() throws Exception {
		profinchCopyRightsPanel();
		disclaimerPanel();
		cell24Panel();
		cell01Row5Panel();
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(22);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(17).getCell(19);
		cellStyle.setRightBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
		cellStyle.setBorderRight(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		for (int i = 20; i <= 28; i++) {
			cell = SHEET.getRow(i).getCell(17);
			cellStyle.setRightBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void cell01Row5Panel() {
		cellAddress = new CellRangeAddress(4, 36, 0, 1);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		for (int i = 4; i <= 36; i++) {
			cell = SHEET.getRow(i).getCell(0);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setRightBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cell = SHEET.getRow(37).getCell(1);
		cellStyle = WORKBOOK.createCellStyle();
		cellStyle.setTopBorderColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBorderTop(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void cell24Panel() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int i = 4; i <= 36; i++) {
			cell = SHEET.getRow(i).createCell(24);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void disclaimerPanel() {
		cellAddress = new CellRangeAddress(37, 37, 2, 17);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(37).getCell(2);
		cell.setCellValue("Failed Test Cases are due to Lack of TestData Intergration Synchronization issues.");
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 8);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(37);
		for (int i = 2; i <= 17; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cell.setCellStyle(cellStyle);
		}
		row = SHEET.getRow(38);
		for (int i = 2; i <= 17; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cell.setCellStyle(cellStyle);
		}
		row = SHEET.getRow(37);
		for (int i = 17; i <= 23; i++) {
			cellStyle = WORKBOOK.createCellStyle();
			cell = row.getCell(i);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THICK);
			cellStyle.setTopBorderColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void profinchCopyRightsPanel() {
		cellAddress = new CellRangeAddress(40, 40, 0, 23);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(40).getCell(0);
		cell.setCellValue("Profinch Solutions Pvt. Ltd.");
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 9);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void createReleaseSummaryBlock() throws Exception {
		setReleaseSummaryBlockBG();
		environmentCellTxt();
		releaseSummaryTxtCell();
		testInitiatedByCell();
		osCell();
		bankingPlatformCell();
		automationTypeCell();
		releaseNumberCell();
		releasePhaseCell();
		browserNameCell();
		browserVersionCell();
		executedURLCell();
		environmentCell();
		javaVersionCell();
		hostAddressCell();
		localHostNameCell();
		pMImgCell();
	}

	private void pMImgCell() throws Exception {
		InputStream inputStream = Math.round((PASS_TC * 100) / TOTAL_TC_EXECUTED1) >= 80
				? new FileInputStream("Resources/Report_Images/Approved.png")
				: new FileInputStream("Resources/Report_Images/Rejected.png");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		int pictureIdx = WORKBOOK.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		inputStream.close();
		creationHelper = WORKBOOK.getCreationHelper();
		ClientAnchor anchor = creationHelper.createClientAnchor();
		anchor.setCol1(4);
		anchor.setRow1(30);
		Drawing<?> drawing = SHEET.createDrawingPatriarch();
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize(2.0, 7);
	}

	private void localHostNameCell() throws Exception {
		InetAddress localhost = InetAddress.getLocalHost();
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(27, 27, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(27).getCell(6);
		cell.setCellValue("Local Host");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(27);
		for (int i = 6; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(28, 28, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(28).getCell(6);
		String address = localhost.getHostName();
		cell.setCellValue(address);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(28);
		for (int i = 6; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void hostAddressCell() throws Exception {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(27, 27, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(27).getCell(2);
		cell.setCellValue("URL Host Address");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(27);
		for (int i = 2; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellAddress = new CellRangeAddress(28, 28, 2, 5);
		fontStyle = WORKBOOK.createFont();
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(28).getCell(2);
		// String temp = BaseTestUIConstant.address.toString();
		String temp = "XYZ";

		String sp = temp.substring(temp.indexOf("/") + 1, temp.length());
		String obdxHost = sp;
		cell.setCellValue(obdxHost);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(28);
		for (int i = 2; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void javaVersionCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(24, 24, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(24).getCell(6);
		cell.setCellValue("JRE Version");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(24);
		for (int i = 6; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(25, 25, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(25).getCell(6);
		cell.setCellValue("JavaSE-" + System.getProperty("java.version"));
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(25);
		for (int i = 6; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void environmentCell() {
		sp = "";
		boolean bnlip = ip(EXECUTED_URL) != null;
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(24, 24, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(24).getCell(2);
		cell.setCellValue("URL Platform");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(24);
		for (int i = 2; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(25, 25, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(25).getCell(2);
		sp = bnlip ? "IUT/LocalHost" : EXECUTED_URL.contains("sit") ? "SIT" : "UAT";
		cell.setCellValue(sp);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(25);
		for (int i = 2; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private String ip(String eXECUTED_URL2) {
		java.util.regex.Matcher m = java.util.regex.Pattern.compile("(?<!\\d|\\d\\.)"
				+ "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])" + "(?!\\d|\\.\\d)")
				.matcher(eXECUTED_URL2);
		return m.find() ? m.group() : null;
	}

	private void executedURLCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(20, 20, 2, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(20).getCell(2);
		cell.setCellValue("Test Initiated URL");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(20);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(21, 22, 2, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(21).getCell(2);
		cell.setCellValue(EXECUTED_URL);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(22);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(21).getCell(8);
		cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderRight(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void browserVersionCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(17, 17, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(17).getCell(6);
		cell.setCellValue("Browser Version");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(17);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(18, 18, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(18).getCell(6);
		cell.setCellValue(BROWSER_VERSION);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(18);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void browserNameCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(17, 17, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(17).getCell(2);
		cell.setCellValue("Browser Name");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(17);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(18, 18, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(18).getCell(2);
		cell.setCellValue(BROWSER_NAME);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(18);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void releasePhaseCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(14, 14, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(14).getCell(6);
		cell.setCellValue("Initiated User CIF");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(14);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(15, 15, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(15).getCell(6);
		String suitenameString;
		Set<String> setObject = new HashSet<>();
		setObject.addAll(BaseTest.usrCIF);
		suitenameString = StringUtils.join(setObject, ", ");
		cell.setCellValue(suitenameString.trim());
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(15);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void releaseNumberCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(14, 14, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(14).getCell(2);
		cell.setCellValue("Release Phase");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(14);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(15, 15, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(15).getCell(2);
		cell.setCellValue(RELEASE_NUMBER.trim());
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(15);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void automationTypeCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(11, 11, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(11).getCell(6);
		cell.setCellValue("Automation Type");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(11);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(12, 12, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(12).getCell(6);
		cell.setCellValue(AUTOMATION_TYPE);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(12);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void bankingPlatformCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(11, 11, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(11).getCell(2);
		cell.setCellValue("Banking Platform");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(11);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(12, 12, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(12).getCell(2);
		cell.setCellValue(BANKING_PLATFORM);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(12);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void osCell() {
		cellAddress = new CellRangeAddress(8, 8, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cellAddress = new CellRangeAddress(9, 9, 6, 8);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(8).getCell(6);
		cell.setCellValue("OS");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(8);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(9).getCell(6);
		cell.setCellValue(System.getProperty("os.name"));
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(9);
		for (int i = 6; i < 8; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void testInitiatedByCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(8, 8, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(8).getCell(2);
		cell.setCellValue("Test Initiated By");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(8);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.WHITE.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(9, 9, 2, 5);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(9).getCell(2);
		String User_Name = System.getProperty("user.name");
		cell.setCellValue(User_Name);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(false);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(9);
		for (int i = 2; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void releaseSummaryTxtCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(6, 6, 2, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(6).getCell(2);
		cell.setCellValue("Release Summary");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 20);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void environmentCellTxt() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(5, 5, 7, 8);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(5).getCell(7);
		cell.setCellValue("Environment");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 14);
		fontStyle.setColor(HSSFColorPredefined.GREY_80_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row = SHEET.getRow(5);
		for (int i = 7; i < 9; i++) {
			cell = row.getCell(i);
			cellStyle.setTopBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setRightBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
	}

	private void setReleaseSummaryBlockBG() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		for (int introw = 4; introw <= 36; introw++) {
			row = SHEET.getRow(introw);
			for (int intcol = 2; intcol <= 8; intcol++) {
				cell = row.getCell(intcol);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
		cell = SHEET.getRow(36).getCell(2);
		cell.setCellValue("Disclaimer*");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 8);
		fontStyle.setColor(HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void createTestExecutionSummaryBlock() throws Exception {
		executionSummaryPanelBG();
		executionSummaryTitleCell();
		totalTCContainerCell();
		passTCContainerCell();
		failTCContainerCell();
		skipOrIgnoredTCContainerCell();
		executionStartTimeCell();
		exceutionEndTimeCell();
		totalDurationTimeCell();
		acceptancePassPercentagecriteriaContainerCell();
		achiverdPassPercentageContainerCell();
		achiverdPassPercentageCutoffContainerCell();
		pieChartContainerCell();
	}

	private void pieChartContainerCell() throws Exception {
		Drawing<?> drawing1 = SHEET.createDrawingPatriarch();
		row = SHEET.getRow(20);
		cell = row.getCell(10);
		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
		ClientAnchor my_anchor = factory.createClientAnchor();
		my_anchor.setCol1(10);
		my_anchor.setRow1(20);
		Picture my_picture = drawing1.createPicture(my_anchor, createSummaryPieChart(WORKBOOK));
		my_picture.resize(7, 15);
	}

	@SuppressWarnings({ "unchecked" })
	private int createSummaryPieChart(HSSFWorkbook wORKBOOK2) throws Exception {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
		dataset.setValue("Success", PASS_TC);
		dataset.setValue("Failure", FAIL_TC);
		dataset.setValue("Skipped", SKIP_TC);
		dataset.setValue("Ignored", 0);
		dataset.setValue("Blocked", 0);
		JFreeChart someChart = ChartFactory.createPieChart3D("Test Result Graphical Representation", dataset, true,
				true, false);
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		someChart.setBackgroundPaint(Color.WHITE);
		someChart.getTitle().setBackgroundPaint(trans);
		someChart.getTitle().setVisible(false);
		someChart.getTitle().setBackgroundPaint(new Color(255, 255, 255, 0));
		someChart.getLegend().setFrame(BlockBorder.NONE);
		someChart.getTitle().setVisible(false);
		LegendTitle legendTitle = someChart.getLegend();
		legendTitle.setPosition(org.jfree.chart.ui.RectangleEdge.BOTTOM);
		legendTitle.setItemFont(new java.awt.Font("Roboto, Arial", java.awt.Font.PLAIN, 22));
		legendTitle.setItemPaint(new Color(21, 35, 143));
		legendTitle.setBackgroundPaint(trans);
		PiePlot3D plot = (PiePlot3D) someChart.getPlot();
		plot.setOutlineVisible(false);
		UIManager.put("ToolTip.background", new Color(206, 0, 0));
		UIManager.put("ToolTip.foreground", new Color(255, 255, 255));
		UIManager.put("ToolTip.font", "Calibri");
		plot.setSectionPaint("Success", new Color(50, 205, 50));
		plot.setSectionPaint("Failure", new Color(252, 16, 13));
		plot.setSectionPaint("Skipped", new Color(255, 215, 0));
		plot.setSectionPaint("Ignored", new Color(168, 168, 168));
		plot.setSectionPaint("Blocked", new Color(82, 81, 81));
		plot.setExplodePercent("Success", .25);
		plot.setExplodePercent("Failure", .10);
		plot.setExplodePercent("Skipped", 0.40);
		plot.setExplodePercent("Ignored", 0.40);
		plot.setExplodePercent("Blocked", 0.40);
		plot.setCircular(true);
		plot.setSimpleLabels(true);
		plot.setLabelFont(new Font("Roboto, Arial", Font.BOLD, 24));
		plot.setLabelPaint(Color.WHITE.brighter());
		plot.setLabelBackgroundPaint(null);
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setSectionOutlinesVisible(false);
		plot.setLabelLinksVisible(false);
		plot.setStartAngle(270);
		plot.setForegroundAlpha(0.60f);
		plot.setInteriorGap(0.02);
		plot.setBackgroundAlpha(0.0f);
		plot.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.30, 0.30, 0.30, 0.30));
		plot.setDepthFactor(0.20);
		plot.setIgnoreNullValues(true);
		plot.setIgnoreZeroValues(true);
		plot.setNoDataMessage("No data to display");
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		plot.setOutlineVisible(false);
		plot.setOutlinePaint(Color.red);
		int width = 740;
		int height = 600;
		float quality = 1;
		ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
		ChartUtils.writeChartAsJPEG(chart_out, quality, someChart, width, height);
		int my_picture_id = WORKBOOK.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_PNG);
		chart_out.close();
		return my_picture_id;
	}

	private void achiverdPassPercentageContainerCell() {
		String passper = PASS_PERCENTAGE.replace("%", "").trim();
		int ConvertInt = Integer.parseInt(passper);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(20, 21, 18, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(20).getCell(18);
		cell.setCellValue("Achived Pass Percentage");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 20);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		fontStyle.setBold(true);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (ConvertInt < 80) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		} else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
		}
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(22, 25, 18, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(22).getCell(18);
		cell.setCellFormula("(P11/L11)");
		cell.setCellType(CellType.FORMULA);
		cellStyle.setDataFormat(WORKBOOK.createDataFormat().getFormat("0%"));
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 60);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		if (ConvertInt < 80) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		} else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
		}
		fontStyle.setBold(false);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(26, 26, 18, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(26).getCell(18);
		cell.setCellValue(ConvertInt);
		if (ConvertInt < 80) {
			fontStyle.setColor(HSSFColorPredefined.DARK_RED.getIndex());
		} else {
			fontStyle.setColor(HSSFColorPredefined.GREEN.getIndex());
		}
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (ConvertInt < 80) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		} else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
		}
		SheetConditionalFormatting sheetCF = SHEET.getSheetConditionalFormatting();
		CellRangeAddress[] regions1 = { CellRangeAddress.valueOf("S27") };
		ExtendedColor color = WORKBOOK.getCreationHelper().createExtendedColor();
		color.setARGBHex("FF00B0F0");
		ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule(color);
		DataBarFormatting dbf = rule.getDataBarFormatting();
		dbf.getMinThreshold().setRangeType(RangeType.NUMBER);
		dbf.getMinThreshold().setValue(0d);
		dbf.getMaxThreshold().setRangeType(RangeType.NUMBER);
		dbf.getMaxThreshold().setValue(100d);
		dbf.setIconOnly(true);
		sheetCF.addConditionalFormatting(regions1, rule);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(27, 28, 18, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(27).getCell(18);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (ConvertInt < 80) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		} else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
		}
		cellStyle.setBorderLeft(BorderStyle.NONE);
		cell.setCellStyle(cellStyle);
	}

	private void achiverdPassPercentageCutoffContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(15, 15, 20, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(15).getCell(20);
		cell.setCellValue("Achived Pass Percentage Cut-Off?");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		fontStyle.setBold(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(16, 17, 20, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(16).getCell(20);
		cell.setCellFormula("IF(S23>=80%,\"Yes\",\"No\")");
		cell.setCellType(CellType.FORMULA);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 36);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		fontStyle.setBold(false);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		fontStyle = WORKBOOK.createFont();
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(18, 18, 20, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(18).getCell(20);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_RED.getIndex());
		fontStyle.setBold(false);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cell.setCellFormula("IF(S23>=80%,3,1)");
		cell.setCellType(CellType.FORMULA);
		fontStyle.setFontHeightInPoints((short) 18);
		SheetConditionalFormatting sheetCF = SHEET.getSheetConditionalFormatting();
		CellRangeAddress[] regions = new CellRangeAddress[] { CellRangeAddress.valueOf("U19") };
		ConditionalFormattingRule rule4 = sheetCF.createConditionalFormattingRule(IconSet.GYR_3_FLAGS);
		IconMultiStateFormatting im4 = rule4.getMultiStateFormatting();
		im4.setIconOnly(true);
		im4.getThresholds()[0].setRangeType(RangeType.MIN);
		im4.getThresholds()[1].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[1].setValue(2d);
		im4.getThresholds()[2].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[2].setValue(3d);
		sheetCF.addConditionalFormatting(regions, rule4);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void acceptancePassPercentagecriteriaContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(15, 15, 9, 12);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(15).getCell(9);
		cell.setCellValue("Acceptance Pass Percentage criteria*");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		fontStyle.setBold(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(16, 18, 9, 12);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(16).getCell(9);
		HSSFFont perFont = WORKBOOK.createFont();
		perFont.setFontName("Calibri");
		perFont.setFontHeightInPoints((short) 36);
		perFont.setBold(false);
		perFont.setColor(HSSFColorPredefined.WHITE.getIndex());
		RichTextString richString = new HSSFRichTextString("80%");
		richString.applyFont(0, "80%".length(), perFont);
		cell.setCellValue(richString);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cell.setCellStyle(cellStyle);
	}

	private void totalDurationTimeCell() {
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(12, 12, 20, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(12).getCell(20);
		HSSFFont perFont = WORKBOOK.createFont();
		perFont.setFontName("Calibri");
		perFont.setFontHeightInPoints((short) 14);
		perFont.setBold(false);
		perFont.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		HSSFFont upArrowFont = WORKBOOK.createFont();
		upArrowFont.setFontName("Calibri");
		upArrowFont.setFontHeightInPoints((short) 9);
		upArrowFont.setBold(false);
		upArrowFont.setColor(HSSFColorPredefined.ROYAL_BLUE.getIndex());
		String up = "⏱";
		int length = "Total Duration".length();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		String clock = StringUtils.leftPad(up, 46);
		RichTextString richString = new HSSFRichTextString("Total Duration" + clock);
		richString.applyFont(0, length, perFont);
		richString.applyFont(length + 1, length + 46, upArrowFont);
		cell.setCellValue(richString);
		cell.setCellStyle(cellStyle);
		cellAddress = new CellRangeAddress(13, 13, 20, 23);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(13).getCell(20);
		fontStyle = WORKBOOK.createFont();
		WORKBOOK.getCreationHelper();
		System.out.println("TOTAL_TIME_TAKEN "+TOTAL_TIME_TAKEN);
		cell.setCellValue(TOTAL_TIME_TAKEN);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 10);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.VIOLET.getIndex());
		fontStyle.setBold(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void exceutionEndTimeCell() {
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(12, 12, 15, 17);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(12).getCell(15);
		HSSFFont perFont = WORKBOOK.createFont();
		perFont.setFontName("Calibri");
		perFont.setFontHeightInPoints((short) 14);
		perFont.setBold(false);
		perFont.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		HSSFFont upArrowFont = WORKBOOK.createFont();
		upArrowFont.setFontName("Calibri");
		upArrowFont.setFontHeightInPoints((short) 9);
		upArrowFont.setBold(false);
		upArrowFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
		String up = "⏱";
		int length = "Execution End's at".length();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		String clock = StringUtils.leftPad(up, 11);
		RichTextString richString = new HSSFRichTextString("Execution End's at" + clock);
		richString.applyFont(0, length, perFont);
		richString.applyFont(length + 1, length + 11, upArrowFont);
		cell.setCellValue(richString);
		cell.setCellStyle(cellStyle);
		cellAddress = new CellRangeAddress(13, 13, 15, 17);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		DataFormat format = WORKBOOK.createDataFormat();
		cell = SHEET.getRow(13).getCell(15);
		fontStyle = WORKBOOK.createFont();
		cellStyle.setDataFormat(format.getFormat("dd/mm/yy hh:mm:ss.000 AM/PM"));
		cell.setCellValue(EXECUTION_END_TIME);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 10);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.RED.getIndex());
		fontStyle.setBold(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void executionStartTimeCell() {
		cellStyle = WORKBOOK.createCellStyle();
		cellAddress = new CellRangeAddress(12, 12, 9, 11);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(12).getCell(9);
		HSSFFont perFont = WORKBOOK.createFont();
		perFont.setFontName("Calibri");
		perFont.setFontHeightInPoints((short) 14);
		perFont.setBold(false);
		perFont.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		HSSFFont upArrowFont = WORKBOOK.createFont();
		upArrowFont.setFontName("Calibri");
		upArrowFont.setFontHeightInPoints((short) 9);
		upArrowFont.setBold(false);
		upArrowFont.setColor(HSSFColorPredefined.GREEN.getIndex());
		String up = "⏱";
		int length = "Execution Start's at".length();
		String clock = StringUtils.leftPad(up, 9);
		RichTextString richString = new HSSFRichTextString("Execution Start's at" + clock);
		richString.applyFont(0, length, perFont);
		richString.applyFont(length + 1, length + 9, upArrowFont);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cell.setCellValue(richString);
		cell.setCellStyle(cellStyle);
		cellAddress = new CellRangeAddress(13, 13, 9, 11);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		DataFormat format = WORKBOOK.createDataFormat();
		cell = SHEET.getRow(13).getCell(9);
		fontStyle = WORKBOOK.createFont();
		cellStyle.setDataFormat(format.getFormat("dd/mm/yy hh:mm:ss.000 AM/PM"));
		cell.setCellValue(EXECUTION_START_TIME);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 10);
		fontStyle.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_BLUE.getIndex());
		fontStyle.setBold(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void skipOrIgnoredTCContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			row = SHEET.getRow(introw);
			for (int intcol = 21; intcol <= 23; intcol++) {
				cell = row.getCell(intcol);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(8, 8, 21, 23);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(8).getCell(21);
		cell.setCellValue("Total Test Skipped");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 18);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		fontStyle.setBold(false);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		row = SHEET.getRow(8);
		for (int intcol = 21; intcol <= 23; intcol++) {
			cell = row.getCell(intcol);
			cellStyle.setTopBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cell.setCellStyle(cellStyle);
		}
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(10).getCell(23);
		cell.setCellValue(SKIP_TC);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.GOLD.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			cell = SHEET.getRow(introw).getCell(24);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(9).getCell(21);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(21);
		int intTP = PASS_TC;
		int intTF = FAIL_TC;
		int intTS = SKIP_TC;
		boolean isflag = false;
		if (intTS == 0 && (intTP != 0 || intTF != 0)) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u003A';
			int length = SKIP_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(SKIP_PERCENTAGE + " " + String.valueOf(right));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
			isflag = true;
		}
		if (intTS > intTP && intTS > intTF) {
			if (isflag)
				return;
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
			HSSFFont upArrowFont = WORKBOOK.createFont();
			upArrowFont.setFontName("Webdings");
			upArrowFont.setFontHeightInPoints((short) 12);
			upArrowFont.setBold(true);
			upArrowFont.setColor(HSSFColorPredefined.SEA_GREEN.getIndex());
			char up = '\u0035';
			int length = SKIP_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(SKIP_PERCENTAGE + " " + String.valueOf(up));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, upArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
			isflag = true;
		} else if (intTS < intTP || intTS < intTF) {
			if (isflag)
				return;
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
			HSSFFont downArrowFont = WORKBOOK.createFont();
			downArrowFont.setFontName("Webdings");
			downArrowFont.setFontHeightInPoints((short) 12);
			downArrowFont.setBold(true);
			downArrowFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			char down = '\u0036';
			int length = SKIP_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(SKIP_PERCENTAGE + " " + String.valueOf(down));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, downArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
			isflag = true;
		} else if (intTS == intTP && intTS == intTF) {
			if (isflag)
				return;
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = SKIP_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					SKIP_PERCENTAGE + " " + String.valueOf(right) + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 3, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
			isflag = true;
		} else if (intTS == intTP || intTS == intTF) {
			if (isflag)
				return;
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = SKIP_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					SKIP_PERCENTAGE + " " + String.valueOf(right) + " " + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 4, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
			isflag = true;
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(22);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void failTCContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			row = SHEET.getRow(introw);
			for (int intcol = 17; intcol <= 19; intcol++) {
				cell = row.getCell(intcol);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(8, 8, 17, 19);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(8).getCell(17);
		cell.setCellValue("Total Test Failed");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 18);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		fontStyle.setBold(false);
		row = SHEET.getRow(8);
		for (int intcol = 17; intcol <= 19; intcol++) {
			cell = row.getCell(intcol);
			cellStyle.setTopBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cell.setCellStyle(cellStyle);
		}
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(10).getCell(19);
		cell.setCellValue(FAIL_TC);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.RED.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			cell = SHEET.getRow(introw).getCell(20);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(9).getCell(17);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(17);
		int intTP = PASS_TC;
		int intTF = FAIL_TC;
		int intTS = SKIP_TC;
		if (intTF > intTP && intTF > intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			HSSFFont upArrowFont = WORKBOOK.createFont();
			upArrowFont.setFontName("Webdings");
			upArrowFont.setFontHeightInPoints((short) 12);
			upArrowFont.setBold(true);
			upArrowFont.setColor(HSSFColorPredefined.SEA_GREEN.getIndex());
			char up = '\u0035';
			int length = FAIL_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(FAIL_PERCENTAGE + " " + String.valueOf(up));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, upArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTF < intTP || intTF < intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			HSSFFont downArrowFont = WORKBOOK.createFont();
			downArrowFont.setFontName("Webdings");
			downArrowFont.setFontHeightInPoints((short) 12);
			downArrowFont.setBold(true);
			downArrowFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			char down = '\u0036';
			int length = FAIL_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(FAIL_PERCENTAGE + " " + String.valueOf(down));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, downArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTF == intTP && intTF == intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = FAIL_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					FAIL_PERCENTAGE + " " + String.valueOf(right) + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 3, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTF == intTP || intTF == intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = FAIL_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					FAIL_PERCENTAGE + " " + String.valueOf(right) + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 3, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(18);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void passTCContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			row = SHEET.getRow(introw);
			for (int intcol = 13; intcol <= 15; intcol++) {
				cell = row.getCell(intcol);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(8, 8, 13, 15);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(8).getCell(13);
		cell.setCellValue("Total Test Passed");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 18);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		fontStyle.setBold(false);
		row = SHEET.getRow(8);
		for (int intcol = 13; intcol <= 15; intcol++) {
			cell = row.getCell(intcol);
			cellStyle.setTopBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cell.setCellStyle(cellStyle);
		}
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(10).getCell(15);
		cell.setCellValue(PASS_TC);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.DARK_GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			cell = SHEET.getRow(introw).getCell(16);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(9).getCell(13);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(10).getCell(13);
		int intTP = PASS_TC;
		int intTF = FAIL_TC;
		int intTS = SKIP_TC;
		if (intTP > intTF && intTP > intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.GREEN.getIndex());
			HSSFFont upArrowFont = WORKBOOK.createFont();
			upArrowFont.setFontName("Webdings");
			upArrowFont.setFontHeightInPoints((short) 12);
			upArrowFont.setBold(true);
			upArrowFont.setColor(HSSFColorPredefined.SEA_GREEN.getIndex());
			char up = '\u0035';
			int length = PASS_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(PASS_PERCENTAGE + " " + String.valueOf(up));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, upArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTP < intTF || intTP < intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.GREEN.getIndex());
			HSSFFont downArrowFont = WORKBOOK.createFont();
			downArrowFont.setFontName("Webdings");
			downArrowFont.setFontHeightInPoints((short) 12);
			downArrowFont.setBold(true);
			downArrowFont.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			char down = '\u0036';
			int length = PASS_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(PASS_PERCENTAGE + " " + String.valueOf(down));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 2, downArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTP == intTF && intTP == intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.GREEN.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = PASS_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					PASS_PERCENTAGE + " " + String.valueOf(right) + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 3, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		} else if (intTP == intTF || intTP == intTS) {
			HSSFFont perFont = WORKBOOK.createFont();
			perFont.setFontName("Calibri");
			perFont.setFontHeightInPoints((short) 14);
			perFont.setBold(true);
			perFont.setColor(HSSFColorPredefined.GREEN.getIndex());
			HSSFFont equalArrowFont = WORKBOOK.createFont();
			equalArrowFont.setFontName("Webdings");
			equalArrowFont.setFontHeightInPoints((short) 12);
			equalArrowFont.setBold(true);
			equalArrowFont.setColor(HSSFColorPredefined.GOLD.getIndex());
			char right = '\u0033';
			char left = '\u0034';
			int length = PASS_PERCENTAGE.length();
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
			RichTextString richString = new HSSFRichTextString(
					PASS_PERCENTAGE + " " + String.valueOf(right) + String.valueOf(left));
			richString.applyFont(0, length, perFont);
			richString.applyFont(length + 1, length + 3, equalArrowFont);
			cell.setCellValue(richString);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THIN);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(18);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(14);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void totalTCContainerCell() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			row = SHEET.getRow(introw);
			for (int intcol = 9; intcol <= 11; intcol++) {
				cell = row.getCell(intcol);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cellAddress = new CellRangeAddress(8, 8, 9, 11);
		SHEET.addMergedRegion(cellAddress);
		cell = SHEET.getRow(8).getCell(9);
		cell.setCellValue("Total Tests");
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 18);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		fontStyle.setBold(false);
		row = SHEET.getRow(8);
		for (int intcol = 9; intcol <= 11; intcol++) {
			cell = row.getCell(intcol);
			cellStyle.setTopBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderTop(BorderStyle.THICK);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			cell.setCellStyle(cellStyle);
		}
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = SHEET.getRow(10).getCell(11);
		cell.setCellValue(TOTAL_TC_EXECUTED);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColorPredefined.ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		for (int introw = 8; introw <= 10; introw++) {
			cell = SHEET.getRow(introw).getCell(12);
			cellStyle.setLeftBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(9).getCell(9);
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cell.setCellStyle(cellStyle);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(9);
		cell.setCellFormula("IF(L11<10,1,IF(L11<30,2,IF(L11<50,3,IF(L11<80,4,5))))");
		cell.setCellType(CellType.FORMULA);
		cellStyle.setLeftBorderColor(HSSFColorPredefined.BLUE_GREY.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		SheetConditionalFormatting sheetCF = SHEET.getSheetConditionalFormatting();
		CellRangeAddress[] regions = new CellRangeAddress[] { CellRangeAddress.valueOf("J11") };
		ConditionalFormattingRule rule4 = sheetCF.createConditionalFormattingRule(IconSet.RATINGS_5);
		IconMultiStateFormatting im4 = rule4.getMultiStateFormatting();
		im4.setIconOnly(true);
		im4.getThresholds()[0].setRangeType(RangeType.MIN);
		im4.getThresholds()[1].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[1].setValue(2d);
		im4.getThresholds()[2].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[2].setValue(3d);
		im4.getThresholds()[3].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[3].setValue(4d);
		im4.getThresholds()[4].setRangeType(RangeType.NUMBER);
		im4.getThresholds()[4].setValue(5d);
		sheetCF.addConditionalFormatting(regions, rule4);
		cellStyle = WORKBOOK.createCellStyle();
		cell = SHEET.getRow(10).getCell(10);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
	}

	private void executionSummaryTitleCell() {
		cellAddress = new CellRangeAddress(6, 6, 9, 23);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = row.getSheet().getRow(6).getCell(9);
		cell.setCellValue("Test Execution Summary");
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 24);
		fontStyle.setBold(true);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
	}

	private void executionSummaryPanelBG() {
		cellStyle = WORKBOOK.createCellStyle();
		for (int esRow = 4; esRow <= 36; esRow++) {
			row = SHEET.getRow(esRow);
			for (int esCell = 9; esCell <= 23; esCell++) {
				cell = row.getCell(esCell);
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
				cell.setCellStyle(cellStyle);
			}
		}
	}

	private void createWorkBookHeader() throws Exception {
		cellAddress = new CellRangeAddress(3, 3, 0, 18);
		SHEET.addMergedRegion(cellAddress);
		cellStyle = WORKBOOK.createCellStyle();
		fontStyle = WORKBOOK.createFont();
		cell = row.getSheet().getRow(3).getCell(0);
		cell.setCellValue(REPORT_NAME);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
		fontStyle.setFontName("Calibri");
		fontStyle.setFontHeightInPoints((short) 26);
		fontStyle.setBold(true);
		fontStyle.setColor(HSSFColorPredefined.DARK_BLUE.getIndex());
		cellStyle.setFont(fontStyle);
		cell.setCellStyle(cellStyle);
		row.getSheet().getRow(3).setHeight((short) 495);
		InputStream inputStream = new FileInputStream("Resources/Report_Images/Profinch_Logo.png");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		int pictureIdx = WORKBOOK.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		inputStream.close();
		creationHelper = WORKBOOK.getCreationHelper();
		ClientAnchor anchor = creationHelper.createClientAnchor();
		anchor.setCol1(21);
		anchor.setRow1(2);
		Drawing<?> drawing = SHEET.createDrawingPatriarch();
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize(3.0, 2);
		row = SHEET.getRow(3);
		for (int i = 0; i <= 23; i++) {
			cell = row.getCell(i);
			cellStyle.setBottomBorderColor(HSSFColorPredefined.DARK_RED.getIndex());
			cellStyle.setBorderBottom(BorderStyle.THICK);
			cell.setCellStyle(cellStyle);
		}
	}

	private void assignNewColorCodeToHSSFPalette() {
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.DARK_BLUE.getIndex(), (byte) 21, (byte) 35, (byte) 143);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.DARK_RED.getIndex(), (byte) 206, (byte) 0, (byte) 0);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.LIGHT_BLUE.getIndex(), (byte) 7, (byte) 115, (byte) 187);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.RED.getIndex(), (byte) 236, (byte) 32, (byte) 40);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.VIOLET.getIndex(), (byte) 146, (byte) 42, (byte) 142);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.ORANGE.getIndex(), (byte) 237, (byte) 125, (byte) 49);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.GREY_25_PERCENT.getIndex(), (byte) 242, (byte) 242,
				(byte) 242);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.GREY_40_PERCENT.getIndex(), (byte) 217, (byte) 217,
				(byte) 217);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.GREY_50_PERCENT.getIndex(), (byte) 166, (byte) 166,
				(byte) 166);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.GREY_80_PERCENT.getIndex(), (byte) 32, (byte) 36, (byte) 41);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.BLUE_GREY.getIndex(), (byte) 147, (byte) 149, (byte) 152);
		COLORPALETTE.setColorAtIndex(HSSFColorPredefined.ROSE.getIndex(), (byte) 255, (byte) 255, (byte) 244);
	}

	public String CipherDecrypt() throws Exception {
		@SuppressWarnings("unused")
		Signature sign = Signature.getInstance("SHA256withRSA");
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(2048);
		KeyPair pair = keyPairGen.generateKeyPair();
		PublicKey publicKey = pair.getPublic();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] input = "Dakshina Moorthy - (90)978 9998 021".getBytes();
		cipher.update(input);
		byte[] cipherText = cipher.doFinal();
		cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
		byte[] decipheredText = cipher.doFinal(cipherText);
		return new String(decipheredText);
	}

	private void createRowsAndCells() {
		for (int intRow = 0; intRow <= 500; intRow++) {
			row = SHEET.createRow(intRow);
			for (int intCell = 0; intCell <= 250; intCell++) {
				cell = row.createCell(intCell);
			}
		}
	}

}
