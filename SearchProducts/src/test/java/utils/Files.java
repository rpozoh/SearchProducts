package utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Files {
	
	public static String getInputFile(String fileName) {		
		String inputFile = Constants.CURRENT_DIRECTORY + "/Datapool/" + fileName;
		return inputFile;
	}
	
	public static File createFile(String fileName) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		File file = new File(Constants.CURRENT_DIRECTORY + "/Datapool/" + fileName + currentDate + ".csv");
		return file;
	}
}