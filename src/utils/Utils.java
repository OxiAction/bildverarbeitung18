package utils;

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
	 */
	public static int[][] stringToIntArray(String data) {
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		
		String[] parts1 = data.split("\\|");
		String[] parts2 = null;
		x = parts1.length;
		for (i = 0; i < parts1.length; ++i) {
			parts2 = parts1[i].split("(?<=\\G...)");
			y = parts2.length;
			break;
		}
		
		int[][] result = new int[x][y];
		
		for (i = 0; i < parts1.length; ++i) {
			parts2 = parts1[i].split("(?<=\\G...)");
			for (j = 0; j < parts2.length; ++j) {
				result[i][j] = Integer.parseInt(parts2[j]);
			}
		}
		
		return result;
	}
}