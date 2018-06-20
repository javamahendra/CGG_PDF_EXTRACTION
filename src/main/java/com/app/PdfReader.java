package com.app;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class PdfReader {
	static List<String[]>allPDFData=new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		//copyFile();
		allPDFData=new ArrayList<>();
		readExcel();
		//writePDfDataIntoExcel();
        //readPDF();
    }

	private static void readPDF(String pdfFilePath) throws Exception {
		/*try {

        	PdfReader reader = new PdfReader("C:\\Projects\\SRS_I&PR_1.0.pdf");
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
            System.out.println(textFromPage);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
		


		StringBuilder preferenceString=new StringBuilder();
		int count=0;
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
		

            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);

				// split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                String halltcketNo=null;
            	String tspscId=null;
            	String candidateName=null;
            	String DOB = null;
            	String gender=null;
            	String preference=null;
                for (String textFromPage : lines) {
                   
                   if(StringUtils.contains(textFromPage, "Hallticket Number : ")) {
                   	  System.out.println("HALL :-"+StringUtils.substring(textFromPage, textFromPage.indexOf("Hallticket Number : ")+"Hallticket Number : ".length(),textFromPage.indexOf("Hallticket Number : ")+"Hallticket Number : ".length()+11));
                   	halltcketNo=StringUtils.substring(textFromPage, textFromPage.indexOf("Hallticket Number : ")+"Hallticket Number : ".length(),textFromPage.indexOf("Hallticket Number : ")+"Hallticket Number : ".length()+11);
                   	  
                   }
                   if(StringUtils.contains(textFromPage, "Tspsc Id : ")) {
                   	System.out.println("TSPSC ID :-"+StringUtils.substring(textFromPage, textFromPage.indexOf("Tspsc Id : ")+"Tspsc Id : ".length(),textFromPage.indexOf(" Hallticket")));
                   	tspscId=StringUtils.substring(textFromPage, textFromPage.indexOf("Tspsc Id : ")+"Tspsc Id : ".length(),textFromPage.indexOf(" Hallticket"));
                 }
                 
                   if(StringUtils.contains(textFromPage, "Candidate Name  : ")) {
                   	System.out.println("Name :-"+StringUtils.substring(textFromPage, textFromPage.indexOf("Candidate Name  : ")+"Candidate Name  : ".length()));
                   	candidateName=StringUtils.substring(textFromPage, textFromPage.indexOf("Candidate Name  : ")+"Candidate Name  : ".length());
                 }
                 
                   if(StringUtils.contains(textFromPage, "Date of Birth  : ")) {
                 	  System.out.println("Birth :-"+StringUtils.substring(textFromPage, textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  : ".length(),textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  : ".length()+10));
                 	  DOB=StringUtils.substring(textFromPage, textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  : ".length(),textFromPage.indexOf("Date of Birth  : ")+"Date of Birth  : ".length()+10);
                 	  if(DOB.contains("-")) {
                 		 String[] dateValues=StringUtils.splitByWholeSeparator(DOB, "-");
                 		 
                 		 if(dateValues[0].length()==4) {
                 			 
                 			DOB=dateValues[2]+"-"+dateValues[1]+"-"+dateValues[0];
                 		 }
                 		 else {
                 			DOB=dateValues[0]+"-"+dateValues[1]+"-"+dateValues[2];
                 		 }
                 	  }else {
                 		  
                 		 String[] dateValues=StringUtils.splitByWholeSeparator(DOB, "/");
                 		 
                 		 if(dateValues[0].length()==4) {
                 			DOB=dateValues[2]+"-"+dateValues[1]+"-"+dateValues[0];
                 		 }
                 		 else {
                 			DOB=dateValues[0]+"-"+dateValues[1]+"-"+dateValues[2];
                 		 }
                 	  }
                 	
             		 
                 	  
                 }
                   if(StringUtils.contains(textFromPage, "Gender  : ")) {
                  	  System.out.println("SEX :-"+StringUtils.substring(textFromPage, StringUtils.ordinalIndexOf(textFromPage, " ", 3),StringUtils.ordinalIndexOf(textFromPage, " ", 4)));
                  	  gender=StringUtils.substring(textFromPage, StringUtils.ordinalIndexOf(textFromPage, " ", 3),StringUtils.ordinalIndexOf(textFromPage, " ", 4));
                  }
                  if(StringUtils.equalsIgnoreCase(textFromPage, "Pre No: Preference Name:")) {
                    	count=count+1;
                    	continue;
                    }
                    if(StringUtils.contains(textFromPage, "Signature")) {
                    	count=0;
                    }
                    if(count==1) {
                    	
                    	preferenceString.append(StringUtils.splitByWholeSeparator(textFromPage, " ")[1]+",");
                    	
                    }
                   
                    
                    
                }
                String newPreferenceString = org.springframework.util.StringUtils.trimTrailingCharacter(preferenceString.toString(), ',');
                preference=newPreferenceString;
                		String fileName=StringUtils.substring(pdfFilePath, pdfFilePath.lastIndexOf(File.separator)+1);
                System.out.println("preference :-"+newPreferenceString);
                String[] eachPDFData={halltcketNo, tspscId,candidateName,DOB,gender,preference,fileName};
                allPDFData.add(eachPDFData);
            }

        }
		
	}
	
	private static void readExcel() throws Exception{
		String file_name="E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/VAS_LATEST_REPORT.xls";
		FileInputStream excelFile = new FileInputStream(new File(file_name));
		Workbook workbook = new HSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = datatypeSheet.iterator();
		rowIterator.next();
		int i=0;
		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();
			String cellValue = celltype(currentRow.getCell(8));
			
			i=i+1;
			System.out.println(i+". "+cellValue);
			copyFile(cellValue);
		}
		
		File copiedfileDir=new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/DestinationFiles");
		File[] listOfFiles = copiedfileDir.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	System.out.println(file.getCanonicalPath());
		       readPDF(file.getCanonicalPath());
		    }
		}
		writePDfDataIntoExcel();
		
	}
	
	
	public static void copyFile(String fileName){
		File trgDir = new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/DestinationFiles");
		File spell1Dir = new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/3CANDIDATES\\"+fileName);
		File spell1RDir = new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/OLD\\"+fileName);
		File spell2Dir = new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/LATEST\\"+fileName);
		//spell1Dir=new File(spell1Dir+File.separator+fileName);
		if(spell1Dir.exists()){
		try {
			FileUtils.copyFileToDirectory(spell1Dir, trgDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if(spell1RDir.exists()){

			try {
				FileUtils.copyFileToDirectory(spell1RDir, trgDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{

			try {
				FileUtils.copyFileToDirectory(spell2Dir, trgDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	

	
	public static String celltype(Cell currentCell) {
		String cellValue = "";
		if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cellValue = String.valueOf(((Double) currentCell.getNumericCellValue()).intValue());
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = currentCell.getStringCellValue();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = String.valueOf(currentCell.getBooleanCellValue());
		}
		/*
		 * else if (currentCell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
		 * cellValue=String.valueOf(currentCell.getBooleanCellValue()); }
		 */
		return cellValue;

	}
	
	public static void writePDfDataIntoExcel() throws Exception{
	      HSSFWorkbook workbook = new HSSFWorkbook();
	      
	      HSSFSheet spreadsheet = workbook.createSheet( " Employee Info ");

	      HSSFRow row;

	      //This data needs to be written (Object[])
	      Map < String, Object[] > empinfo = new TreeMap < String, Object[] >();
	      int keyValue=0;
	      for(String[] eachRow:allPDFData){
	    	  keyValue=keyValue+1;
	    	  empinfo.put( ""+keyValue, eachRow );
	      }
	     /* String abc[]={"EMP ID", "EMP NAME", "DESIGNATION" };
	      empinfo.put( "1", abc );
	      
	      empinfo.put( "2", new Object[] {
	         "tp01", "Gopal", "Technical Manager" });
	      
	      empinfo.put( "3", new Object[] {
	         "tp02", "Manisha", "Proof Reader" });
	      
	      empinfo.put( "4", new Object[] {
	         "tp03", "Masthan", "Technical Writer" });
	      
	      empinfo.put( "5", new Object[] {
	         "tp04", "Satish", "Technical Writer" });
	      
	      empinfo.put( "6", new Object[] {
	         "tp05", "Krishna", "Technical Writer" });*/

	      //Iterate over data and write to sheet
	      Set < String > keyid = empinfo.keySet();
	      int rowid = 0;
	      
	      for (String key : keyid) {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = empinfo.get(key);
	         int cellid = 0;
	         
	         for (Object obj : objectArr){
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	      //Write the workbook in file system
	      FileOutputStream out = new FileOutputStream(
	         new File("E:/CGG_NEER_DATA - 2/Notification-30/Old and Latest/NOTIFICATION_30_maths.xls"));
	      
	      workbook.write(out);
	      out.close();
	      System.out.println("Writesheet1_maths.xlsx written successfully");
	   }
}

