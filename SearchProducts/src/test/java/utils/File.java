package utils;

public class File {
	
	public static String getInputFile(String fileName) {		
		String inputFile = Constants.CURRENT_DIRECTORY + "/Datapool/" + fileName;
		return inputFile;
	}
}