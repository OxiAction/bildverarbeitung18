package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Collection of utilities.
 */
public class Utils {

	public static String mostFrequent(ArrayList<String> arr) {

		// Insert all elements in hash 
		Map<String, Integer> hp = new HashMap<String, Integer>();

		for (int i = 0; i < arr.size(); i++) {
			String key = arr.get(i);
			if (hp.containsKey(key)) {
				int freq = hp.get(key);
				freq++;
				hp.put(key, freq);
			} else {
				hp.put(key, 1);
			}
		}

		// find max frequency. 
		int max_count = 0;
		String res = null;

		for (Entry<String, Integer> val : hp.entrySet()) {
			if (max_count < val.getValue()) {
				res = val.getKey();
				max_count = val.getValue();
			}
		}

		return res;
	}

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

		for (Integer value : data) {
			if (result.length() > 0) {
				result.append(", ");
			}
			result.append(value);
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
		String[] values = data.split(", ");
		// sanity check
		if (values.length == 0) {
			return null;
		}

		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < values.length; ++i) {
			result.add(Integer.parseInt(values[i]));
		}

		return result;
	}

	/**
	 * Converts an ArrayList<String> to String ("|" is the delimeter).
	 * E.g.: ArrayList<String> with entries 1, 2, 4 => "1, 2, 4"
	 * 
	 * @param data
	 * @return
	 */
	public static String stringArrayListToString(ArrayList<String> data) {
		// sanity check
		if (data == null || data.size() == 0) {
			return "";
		}

		StringBuffer result = new StringBuffer();

		for (String value : data) {
			if (result.length() > 0) {
				result.append(", ");
			}
			result.append(value);
		}

		return result.toString();
	}

	/**
	 * Converts a String ("|" is the delimeter) to ArrayList<String>.
	 * E.g.: "1, 2, 4" => ArrayList<String> with entries 1, 2, 4
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<String> stringToStringArrayList(String data) {
		String[] values = data.split(", ");
		// sanity check
		if (values.length == 0) {
			return null;
		}

		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			result.add(values[i]);
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
	 * Converts an double[][] array to String.
	 * E.g.: { { 1.1, 9.9 } , { 3.4, 4.5 } } => "1.1-9.9|3.4-4.5"
	 * 
	 * @param data
	 * @return
	 */
	public static String doubleArray2DToString(double[][] data) {
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
				if (j > 0) {
					result += "-";
				}
				result += String.valueOf(data[i][j]);
			}
		}

		return result;
	}

	/**
	 * Converts a String to an double[][] array.
	 * E.g.: "1.1-9.9|3.4-4.5" => { { 1.1, 9.9 } , { 3.4, 4.5 } }
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static double[][] stringToDoubleArray2D(String data) throws Exception {
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

		parts2 = parts1[0].split("-");
		y = parts2.length;

		double[][] result = new double[x][y];

		for (i = 0; i < parts1.length; ++i) {
			parts2 = parts1[i].split("-");
			for (j = 0; j < parts2.length; ++j) {
				result[i][j] = Double.parseDouble(parts2[j]);
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
		String line = "";
		for (int k = 0; k < arr.length; ++k) {
			line += "\t" + arr[k] + " ";
		}
		Debug.log(line + "\n");
	}

	/**
	 * prints an 2d int array to console.
	 * 
	 * @param arr
	 */
	public static void printIntArray2D(int[][] arr) {
		for (int j = 0; j < arr.length; ++j) {
			String line = "";
			for (int k = 0; k < arr[j].length; ++k) {
				line += "\t" + arr[j][k] + " ";
			}
			Debug.log(line + "\n");
		}
	}

	/**
	 * prints an 4d int array to console.
	 * 
	 * @param arr
	 */
	public static void printIntArray4D(int[][][][] arr) {
		for (int j = 0; j < arr.length; ++j) {
			if (j > 0) {
				Debug.log("---");
			}

			for (int k = 0; k < arr[j].length; ++k) {
				String line = "";
				for (int j2 = 0; j2 < arr[j][k].length; ++j2) {
					if (j2 > 0) {
						line += "\n";
					}
					for (int k2 = 0; k2 < arr[j][k][j2].length; ++k2) {

						line += "\t" + arr[j][k][j2][k2] + " ";
					}

				}
				Debug.log(line + "\n");
			}

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
		for (int i = 0; i < arr1.length; ++i) {
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
		for (int i = 0; i < arr1.length; ++i) {
			for (int j = 0; j < arr1[i].length; ++j) {
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
		for (int j = 0; j < arr1.length; ++j) {
			for (int k = 0; k < arr1[j].length; ++k) {
				arr1[j][k] = arr1[j][k] / i;
			}
		}
		return arr1;
	}

	/**
	 * Divides an 2d int array into an 4d chunks arrays.
	 * 
	 * @param source	source 2d int array
	 * @param sliceX	slice on x-axis
	 * @param sliceY	slice on y-axis
	 * @return
	 */
	public static int[][][][] getChunksFromIntArray2D(int[][] source, int sliceX, int sliceY) {
		if (source == null || source.length == 0) {
			return null;
		}
		//Debug.log("[source Array]:");
		//printIntArray2D(source);

		int height = source.length;
		int divideHeight = height / sliceY; // will be rounded down
		if (height % sliceY != 0) {
			Debug.log("Warning: last array row will be ignored (regarding y-dimension of the source array)!");
		}
		height = divideHeight * sliceY;

		int width = source[0].length;
		int divideWidth = width / sliceX; // will be rounded down
		if (width % sliceX != 0) {
			Debug.log("Warning: last array column will be ignored (regarding x-dimension of the source array)!");
		}
		width = divideWidth * sliceX;

		Debug.log("result dimensions [chunk y][chunk x][source y][source x]: " + "[" + sliceY + "][" + sliceX + "][" + divideHeight + "][" + divideWidth + "]");

		int[][][][] result = new int[sliceY][sliceX][divideHeight][divideWidth];

		int chunkY = 0;
		int chunkX = 0;

		int y = 0;
		int x;

		for (int h = 0; h < height; ++h) {
			chunkX = 0;
			//Debug.log("-X rest chunkX to " + chunkX);

			x = 0;
			for (int w = 0; w < width; ++w) {
				//Debug.log("=> set [" + chunkY + "][" + chunkX + "][" + x + "][" + y + "] to " + source[h][w]);
				result[chunkY][chunkX][y][x] = source[h][w];

				if ((w + 1) % divideWidth == 0) {
					x = 0;
					//Debug.log("-X reset x to " + x);
					chunkX++;
					//Debug.log("->> inc chunkX to " + chunkX);
				} else {
					x++;
					//Debug.log("->> inc x to " + x);
				}
			}

			if ((h + 1) % divideHeight == 0) {
				y = 0;
				chunkY++;
				//Debug.log("->> inc chunkY to " + chunkY);
			} else {
				y++;
				//Debug.log("->> inc y to " + y);
			}
		}

		//Debug.log("[result Array]:");
		//printIntArray2D(result[0][0]);
		//printIntArray4D(result);

		return result;
	}

	/**
	 * Calculates the mean greyscale value of an image based on its greyscale values and histogram.
	 *
	 * @param 	greyScaleData
	 * @param 	histogramData
	 * @return	the mean greyscale value
	 */
	public static double getMeanGreyScaleValue(int[][] greyScaleData, int[] histogramData) {
		double meanGreyScale = 0.0;

		for (int i = 0; i < histogramData.length; ++i) {
			meanGreyScale += i * histogramData[i];
		}
		//System.out.println("meanGreyScale: " + meanGreyScale / getNumberOfGreyScaleValues(greyScaleData));
		return meanGreyScale / getNumberOfGreyScaleValues(greyScaleData);
	}

	/**
	 * Calculates the number of all greyscale values of an image.
	 *
	 * @param 	greyScaleData
	 * @return	the number of greyscale values
	 */
	public static int getNumberOfGreyScaleValues(int[][] greyScaleData) {
		int length = greyScaleData.length;
		int width = greyScaleData[0].length;
		//System.out.println("numberOfGreyScaleValues: " + length * width);
		return length * width;
	}

	/**
	 * Get the number of all histogram entries
	 *
	 * @param histogram
	 * @return
	 */
	public static int getNumberOfHistogramValues(int[] histogram) {
		int numberOfHistogramValues = 0;
		for (int i = 0; i < histogram.length; i++) {
			numberOfHistogramValues += histogram[i];
		}
		return numberOfHistogramValues;
	}
}