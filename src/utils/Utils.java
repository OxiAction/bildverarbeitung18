package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Collection of utilities.
 */
public class Utils {
	
	/**
	 * Converts an ArrayList<Integer> to String ("|" is the delimeter).
	 * E.g.: ArrayList<Integer> with entries 1, 2, 4 => "1, 2, 4"
	 * 
	 * @param data
	 * @return
	 */
	public static String intArrayListToString(ArrayList<Integer> data) {
		// sanity check
		if (data == null || data.size() == 0) {
			return "";
		}
		
		StringBuffer result = new StringBuffer();
		
		for (Integer integer : data) {
			if (result.length() > 0) {
				result.append(", ");
			}
			result.append(integer);
		}

		return result.toString();
	}
	
	/**
	 * Converts a String ("|" is the delimeter) to ArrayList<Integer>.
	 * E.g.: "1, 2, 4" => ArrayList<Integer> with entries 1, 2, 4
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<Integer> stringToIntArrayList(String data) {
		String[] integers = data.split(", ");
		// sanity check
		if (integers.length == 0) {
			return null;
		}
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < integers.length; ++i) {
			result.add(Integer.parseInt(integers[i]));
		}
		
		return result;
	}
	

	/**
	 * Converts an int[] array to String.
	 * E.g.: { 1, 999 } => "1, 999"
	 * 
	 * @param data
	 * @return
	 */
	public static String intArray1DToString(int[] data) {
		// sanity check
		if (data == null || data.length == 0) {
			return "";
		}
		
		StringBuffer result = new StringBuffer();

		for (int i = 1; i < data.length; i++) {
			if (result.length() > 0) {
				result.append(", ");
			}
			result.append(data[i]);
		}

		return result.toString();
	}
	
	/**
	 * Converts a String to int[] array.
	 * E.g.: "1, 999" => { 1, 999 }
	 * 
	 * @param data
	 * @return
	 */
	public static int[] stringToIntArray1D(String data) {
		String[] integers = data.split(", ");
		// sanity check
		if (integers.length == 0) {
			return null;
		}
		
		int[] result = new int[integers.length];
		for (int i = 0; i < integers.length; i++) {
			result[i] = Integer.parseInt(integers[i]);
		}

		return result;
	}
	
	/**
	 * Converts an int[][] array to String.
	 * Every Integer will be saved in three digit form.
	 * This means, the value of valid Integers must be between 0 and 999.
	 * E.g.: { { 1, 999 } , { 3, 40 } } => "001999|003040"
	 * 
	 * @param data
	 * @return
	 */
	public static String intArray2DToString(int[][] data) {
		// sanity check
		if (data == null || data.length == 0) {
			return "";
		}
		
		String result = "";
		
		for (int i = 0; i < data.length; ++i) {
			if (i > 0) {
				// use "|" as delimiter
				result += "|";
			}
			
			for (int j = 0; j < data[i].length; ++j) {
				// converts for example 12 to 012 - or 1 to 001
				int value = data[i][j];
				if (value < 10) {
					result += "00" + String.valueOf(value);
				} else if (value < 100) {
					result += "0" + String.valueOf(value);
				} else {
					result += String.valueOf(value);
				}
				// this approach is elegant but super slow:
				//result += String.format("%03d", data[i][j]);
			}
		}
		
		return result;
	}
	
	/**
	 * Converts a String to an int[][] array.
	 * Every Integer (in the String) has to be in three digit form.
	 * This means, the value of valid Integers must be between 0 and 999.
	 * E.g.: "001999|003040" => { { 1, 999 } , { 3, 40 } }
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int[][] stringToIntArray2D(String data) throws Exception {
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		
		String[] parts1 = data.split("\\|");
		// sanity check
		if (parts1.length == 0) {
			return null;
		}
		
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
	
	/**
	 * Returns a HashMap with the following keys (keys are null if it could not get the required information):
	 * - fileExtension
	 * - fileFolderPath
	 * - fileName
	 * 
	 * @param absoluteFilePath
	 * @return
	 */
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
	
	/**
	 * Prints an 1d int array to console.
	 * 
	 * @param arr
	 */
	public static void printIntArray1D(int[] arr) {
		for (int j = 0; j < arr.length; j++) {
			Debug.log(arr[j] + " ");
		}
		Debug.log("");
	}

	/**
	 * prints an 2d int array to console.
	 * 
	 * @param arr
	 */
	public static void printIntArray2D(int[][] arr) {
		for (int j = 0; j < arr.length; j++) {
			for (int k = 0; k < arr[j].length; k++) {
				Debug.log(" " + arr[j][k]);
			}
			Debug.log("");
		}
	}
	
	/**
	 * Adds two equally sized 1d int arrays to each other.
	 * 
	 * @param arr1
	 * @param arr2
	 * @return sum
	 */
	public static int[] addIntArrays1D(int[] arr1, int[] arr2) {
		int[] sum = new int[arr1.length];
		for (int i = 0; i < arr1.length; i++) {
			sum[i] = arr1[i] + arr2[i];
		}
		return sum;
	}

	/**
	 * Adds two equally sized 2d int arrays to each other.
	 * 
	 * @param arr1
	 * @param arr2
	 * @return sum
	 */
	public static int[][] addIntArrays2D(int[][] arr1, int[][] arr2) {
		int[][] sum = new int[arr1.length][arr1[0].length];
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				sum[i][j] = arr1[i][j] + arr2[i][j];
			}
		}
		return sum;
	}
	
	/**
	 * Creates new 2d int array of the same size as the given one.
	 * 
	 * @param arr1
	 * @return arr2
	 */
	public static int[][] createNewIntArray2DofSize(int[][] arr1) {
		int x = arr1.length;
		int y = arr1[0].length;
		int[][] arr2 = new int[x][y];
		return arr2;
	}

	/**
	 * Calculates the average values of an 2d int array.
	 * 
	 * @param arr1
	 * @param i
	 * @return
	 */
	public static int[][] calculateAverageIntArray2D(int[][] arr1, int i) {
		for (int j = 0; j < arr1.length; j++) {
			for (int k = 0; k < arr1[j].length; k++) {
				arr1[j][k] = arr1[j][k] / i;
			}
		}
		return arr1;
	}
}