package org.mc.mf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileExecuter {
	private static String reportFileName = "/output-4.txt";
	private static String outputPath;
	private static String fileHeader;
	private static String fileTailer;
	private static String prevRepHeader = "";
	static String filePath;
	
	public static void main(String... args) {
		if(args.length == 0){
			System.out.println("Please provide full input file path!");
			filePath = "C:/My learning/My Java learning/code/code/input-3.txt"; 
		} else {			
			filePath = args[0];
		}
		System.out.println("Start reading file from " + filePath);
		List<String> list = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createFileHeaderTailer(list.get(0));
		writeFileHeader(fileHeader);
		int lineNo = 1;
		for(String strLine : list) {
			if(!strLine.isEmpty()) {
				if(strLine.length() < 199) {
					System.out.println("The line no " + lineNo + " is not having proper length!");
					System.out.println("Program stopped. Partial output generated. Please check file!");
					break;
				} else {
					processLine(strLine); // process line
				}
			} else { // skipped the line but continue...
				System.out.println("The empty line is skipped!");
			}
			lineNo++;
		}
		writeFileHeader(fileTailer);
		System.out.println("File output generated successfully on "+outputPath);
		
	}
	
	private static void createFileHeaderTailer(String str) {
		String s = str.substring(180, 184);		
		fileHeader = "FHDR0" + s + " " + getCurrentTime();
		fileTailer = "FTLR" + " " + getCurrentTime();
		System.out.println("File header and Tailer created fileHeader: "+fileHeader + "fileTailer: "+fileTailer);
	}
	

	private static void processLine(String line) {
		String firstChunck = line.substring(0, 47).trim();
		RepDetail repDetail = new RepDetail();
		StringBuilder repHeader = createRepHeader(firstChunck.substring(firstChunck.lastIndexOf(" ")+1));
		if(!prevRepHeader.equals(repHeader.toString())) {
			writeReportHeader(repHeader.toString());
			prevRepHeader = repHeader.toString();
		} 
		String secondChunck = line.substring(47, 156).trim();
		createRepFirstAndSecondColumn(secondChunck, repDetail);
		String thirdChunck = line.substring(155).trim();
		createRepThirdAndFourthColumn(thirdChunck, repDetail);
		writeReportDetail(repDetail);
	}
	
	private static StringBuilder createRepHeader(String repId) {
		StringBuilder stBldr = new StringBuilder("RHDR");
		stBldr.append(repId);
		stBldr.append("\t");
		stBldr.append(getCurrentTime());
		return stBldr;
	}

	private static void createRepFirstAndSecondColumn(String line, RepDetail repDetail) {
		String[] tokens = line.split(" ");  //StringUtils
		int counter = 0;
		StringBuilder strBldr = new StringBuilder();
		StringBuilder strBldr2 = new StringBuilder();
		for (String token : tokens) {
			if(counter == 0) {
				strBldr.append(token);
				strBldr.append("-");
			}
			if(counter == 1) {
				strBldr.append(token);
			}
			if(counter == 2) {
				strBldr.append("_");
			}
			if(counter == 3) {
				strBldr.append(token);
			}
				
			if(counter > 3) { 
				strBldr2.append(token);
				strBldr2.append(" ");
			}
			counter++;
		}
		repDetail.setFirstCol(strBldr.toString());
		repDetail.setSecondCol(strBldr2.toString().trim());
	}
	
	private static void createRepThirdAndFourthColumn(String line, RepDetail repDetail2) {
		String[] tokens = line.split(" ");
		int counter = 0;
		StringBuilder strBldr = new StringBuilder();
		StringBuilder strBldr2 = new StringBuilder();
		for (String token : tokens) {
			if(counter == 0) {// || counter == 1 || counter == 3 || counter == 4 || counter == 6) {
				strBldr.append("00000000000");
				strBldr.append(token);
			}
			if(counter == 1) {
				strBldr.append("0000000000");
				strBldr.append(token);
			}
			if(counter == 2) {
				strBldr.append("0000000000000");
				strBldr.append(token);
			}
			if(counter == 3 || counter == 4 || counter == 6) {
				strBldr.append(token);
			}
			if(counter == 7) {
				strBldr2.append(token);
			}
			if(counter == 3) {
				repDetail2.setFirstCol(repDetail2.getFirstCol().replace("_", token));
			}
			counter++;
		}
		repDetail2.setThirdCol(strBldr.toString());
		repDetail2.setFourthCol(strBldr2.toString());
	}
		
	static void writeFileHeader(String str) {
		int indx = filePath.lastIndexOf("/");
		outputPath = filePath.substring(0, indx)+reportFileName;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true))) {
			writer.write(str);
			writer.write("\n");
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	static void writeReportHeader(String repHeader) {
		int indx = filePath.lastIndexOf("/");
		String outputPath = filePath.substring(0, indx)+reportFileName;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true))) {
			writer.write(repHeader);
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	static void writeReportDetail(RepDetail rd) {
		int indx = filePath.lastIndexOf("/");
		String outputPath = filePath.substring(0, indx)+reportFileName;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true))) {
			writer.write(rd.toString());
			writer.write("\t");
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static String getCurrentTime() {
		LocalDate today = LocalDate.now();
		int dd = today.getDayOfMonth();
		int mm = today.getMonthValue();
		int yyyy = today.getYear();
		LocalTime time = LocalTime.now();
		int hr = time.getHour();
		int min = time.getMinute();
		int sec = time.getSecond();
		return mm+""+dd+yyyy+"."+hr+":"+min+":"+sec;
	}
}
