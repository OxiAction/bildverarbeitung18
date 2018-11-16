package utils;

import java.util.HashMap;

/**
 * Collection of utilities
 *
 * @author Michael Schreiber
 *
 */
public class Utils {
	
	/**
	 * converts a int[][] array to string
	 * e.g.: { { 1, 2 } , { 3, 4 } } => 12|34
	 * 
	 * @param text	the text
	 */
	public static String intArrayToString(int[][] data) {
		String result = "";
		
		for (int i = 0; i < data.length; ++i) {
			if (i > 0) {
				// use "|" as delimiter
				result += "|";
			}
			
			for (int j = 0; j < data[i].length; ++j) {
				// converts for example 12 to 012 - or 1 to 001
				result += String.format("%03d", data[i][j]);
			}
		}
		
		return result;
	}
	
	/**
	 * converts a string to int[][] array
	 * e.g.: 12|34 => { { 1, 2 } , { 3, 4 } }
	 * 
	 * @param data		the string
	 * @return int[][]	the array
	 * @throws Exception 
	 */
	public static int[][] stringToIntArray(String data) throws Exception {
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		
		String[] parts1 = data.split("\\|");
		String[] parts2 = null;
		x = parts1.length;
		
		if (parts1.length == 0) {
			throw new Exception("The data String is invalid.");
		}
		
		parts2 = parts1[0].split("(?<=\\G...)");
		y = parts2.length;
		
		int[][] result = new int[x][y];
		
		for (i = 0; i < parts1.length; ++i) {
			parts2 = parts1[i].split("(?<=\\G...)");
			for (j = 0; j < parts2.length; ++j) {
				result[i][j] = Integer.parseInt(parts2[j]);
			}
		}
		
		return result;
	}
	
	public static HashMap<String, String> getAbsoluteFilePathInfos(String absoluteFilePath) {
		HashMap<String, String> infos = new HashMap<String, String>();
		
		if (absoluteFilePath.indexOf('.') != -1) {
			infos.put("fileExtension", absoluteFilePath.substring(absoluteFilePath.lastIndexOf('.'), absoluteFilePath.length()));
		}
		
		if (absoluteFilePath.lastIndexOf('/') > 0) {
			infos.put("fileFolderPath", absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf('/')));
		}
		
		if (absoluteFilePath.indexOf('.') != -1) {
			if (absoluteFilePath.indexOf('/') != -1) {
				infos.put("fileName", absoluteFilePath.substring(absoluteFilePath.lastIndexOf('/') + 1, absoluteFilePath.lastIndexOf('.')));
			} else {
				infos.put("fileName", absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf('.')));
			}
		}
		
		return infos;
	}
}