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
				result += data[i][j];
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
		int width = 0;
		
		// determine req. array size first
		for (i = 0; i < data.length(); ++i) {
			if (data.charAt(i) == '|') {
				width = 0;
				++j;
			} else {
				++width;
			}
		}
		
		// init array
		int[][] result = new int[j + 1][width];
		
		// reset vars and loop again - filling the array
		j = 0;
		width = 0;
		for (i = 0; i < data.length(); ++i) {
			if (data.charAt(i) == '|') {
				++j;
				width = 0;
			} else {
				result[j][width] = Character.getNumericValue(data.charAt(i));
				++width;
			}
		}
		
		return result;
	}
}