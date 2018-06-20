package com.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadPdf {

	public static void main(String[] args) throws IOException {

		try {

			PDDocument document = PDDocument
					.load(new File(
							"C:\\Users\\Mahendra\\Desktop\\Reports\\301\\OLD\\30201707907808.pdf"));

			// System.out.println();
			document.getClass();
			System.out.println("------------------------------------");

			if (!document.isEncrypted()) {

				TSPSC ts = new TSPSC();

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);
				// System.out.println("Text:" + pdfFileInText);

				// split by whitespace
				String lines[] = pdfFileInText.split("\\r?\\n");

				String halltcketNo = null;
				String tspscId = null;
				String candidateName = null;
				String DOB = null;
				String gender = null;
				String preference = null;

				int count = 1;

				String str = null;

				for (String textFromPage : lines) {
					
					if (StringUtils.contains(textFromPage, "Tspsc Id : ")) {
						System.out.println("TSPSC ID :-"+StringUtils.substring(textFromPage,textFromPage.indexOf("Tspsc Id :")+ "Tspsc Id : ".length(),textFromPage
								.indexOf("Tspsc Id : ")+ "Tspsc Id : ".length()+ 12));					
					ts.setTSPSCID(StringUtils.substring(textFromPage,textFromPage.indexOf("Tspsc Id :")+ "Tspsc Id : ".length(),textFromPage
							.indexOf("Tspsc Id : ")+ "Tspsc Id : ".length()+ 12));
					}

					if (StringUtils.contains(textFromPage, "Candidate Name  : ")) {
						System.out.println("Name :-"+ StringUtils.substring(textFromPage,textFromPage.indexOf("Candidate Name  : ")
																+ "Candidate Name  : ".length()));
						candidateName = StringUtils.substring(textFromPage,textFromPage.indexOf("Candidate Name  : ")+ "Candidate Name  : ".length());
					
					ts.setName(candidateName);
					}
						
					if (StringUtils.contains(textFromPage,"Hallticket Number : ")) {
						System.out.println("HALL :-"+ StringUtils.substring(textFromPage,textFromPage.indexOf("Hallticket Number : ")+ "Hallticket Number :".length()));
						halltcketNo = StringUtils.substring(textFromPage,textFromPage.indexOf("Hallticket Number : ")+ "Hallticket Number : ".length(),10);
						ts.setHALL(StringUtils.substring(textFromPage,textFromPage.indexOf("Hallticket Number : ")+ "Hallticket Number :".length()));

					}
					
					if (StringUtils.contains(textFromPage, "Date of Birth  : ")) {

						System.out.println("Birth :-"+ StringUtils.substring(textFromPage,textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  :".length(),textFromPage
														.indexOf("Date of Birth  : ")+ "Date of Birth  : ".length()+ 10));
						DOB = StringUtils.substring(textFromPage,textFromPage.indexOf("Date of Birth  : ")+ "Date of Birth  : ".length()+10);
						ts.setBirth(StringUtils.substring(textFromPage,textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  :".length(),textFromPage
								.indexOf("Date of Birth  : ")+ "Date of Birth  : ".length()+ 10));
					}
					
					if (StringUtils.contains(textFromPage, "Gender  : ")) {
						System.out.println("Gender :-"+ StringUtils.substring(textFromPage,textFromPage.indexOf("Gender :")+ "Gender : ".length(),textFromPage
								.indexOf("Gender  : ")+ "Gender  : ".length()+ 1));
						gender=StringUtils.substring(textFromPage,textFromPage.indexOf("Gender :")+ "Gender : ".length(),textFromPage
								.indexOf("Gender  : ")+ "Gender  : ".length()+ 1);
						ts.setGender(gender);				
					}
					
					
				}	
                System.out.println("================================\n\n");
				System.out.println(ts);
				System.out.println("================================");
				
				String excelFilePath="web.xls";
				FileOutputStream fos=new FileOutputStream(excelFilePath);
				
				
				
				
				
				
				
				HSSFWorkbook workbook=new HSSFWorkbook();
				
				List<TSPSC> lst=new ArrayList<TSPSC>();
				lst.add(ts);
				
				HSSFSheet sheet=workbook.createSheet("Grid Values");
				
				setHeaders(sheet);
				setRows(sheet,ts);
				
				 workbook.write(fos);
								
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	public static void setHeaders(HSSFSheet sheet)
	{
		System.out.println("header.....");
		HSSFRow row=sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("HallTicket");
		row.createCell(3).setCellValue("Gender");
		row.createCell(4).setCellValue("DOB");
		row.createCell(5).setCellValue("web_options");
		
		
	}
	public static void setRows(HSSFSheet sheet,TSPSC ts)
	{
		int rowid=1;
		
		System.out.println("row data.....");
			HSSFRow row=sheet.createRow(1);
			row.createCell(0).setCellValue(ts.getTSPSCID());
			row.createCell(1).setCellValue(ts.getName());
			row.createCell(2).setCellValue(ts.getHALL());
			row.createCell(3).setCellValue(ts.getGender());
			row.createCell(4).setCellValue(ts.getBirth());	
		
	}

}