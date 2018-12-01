package core.evaluation;

import utils.Debug;

/**
 * TODO description
 */
public class Histogram {
	protected static final int MAX_SIZE = 256;
	protected static final int DEF_SIZE = 256;

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram with size 256 out of it.
	 * 
	 * @param 	greyScaleData	the greyScale values of an image, an 2D int array
	 * @return	the histogram
	 */
	public static int[] get(int[][] greyScaleData) {
		return get(greyScaleData, DEF_SIZE);
	}

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram out of it.
	 * 
	 * TODO implement scalable histogram. Use parameter size for this
	 * 
	 * @param 	greyScaleData	the greyScale values of an image, an 2D int array
	 * @param 	size			the scaled size (e.g. 16 32 64 128 256)
	 * @return	the histogram
	 */
	public static int[] get(int[][] greyScaleData, int size) {
		if(sizeValid(size) == false){
			Debug.log("Invalid histogram size, please use 256, 128, 64, 32, 16, 8, 4 or 2.");
			Debug.log("Using default size 256...");
			size = DEF_SIZE;
		}

		int[] histogramData = new int[size];
		for (int o = 0; o < size; o++){
			histogramData[o] = 0;
		}

		int scaleFactor = MAX_SIZE/size;

		for (int i = 0; i < greyScaleData.length; i++) {
			for (int j = 0; j < greyScaleData[i].length; j++) {
				histogramData[greyScaleData[i][j] / scaleFactor] += 1;
			}
		}

		return histogramData;
	}

	/**
	 * Checks if input size is of form 2^x with maximum 256
	 * @param size
	 * @return
	 */
	private static boolean sizeValid(int size){
		if(size == 256 || size == 128 || size == 64 || size == 32 || size == 16 || size == 8 || size == 4 || size == 2){
			return true;
		}
		return false;
	}

//	/**
//	 * Testing scalability of histogram.data with dummy values
//	 */
//	public static void main(String[] args) {
//		int[][] greyScaleData = {{5, 6, 7}, {1, 2, 3}};
//		int[] histogramData = get(greyScaleData, 64);
//		System.out.println("histogramdata: ");
//		for(int i = 0; i < histogramData.length; i++){
//			System.out.print(histogramData[i] + " ");
//		}
//		System.out.println("\nHistogramData.length(): " + histogramData.length);
//	}
}