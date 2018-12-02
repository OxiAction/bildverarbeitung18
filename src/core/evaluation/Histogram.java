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
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @returnthe histogram
	 */
	public static int[] get(int[][] greyScaleData) {
		return get(greyScaleData, DEF_SIZE);
	}

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram out of it.
	 * 
	 * TODO implement scalable histogram. Use parameter size for this
	 * 
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @param size				the scaled size (e.g. 16 32 64 128 256)
	 * @return the histogram
	 */
	public static int[] get(int[][] greyScaleData, int size) {
		if (sizeValid(size) == false) {
			Debug.log("Invalid histogram size, please use 256, 128, 64, 32, 16, 8, 4 or 2.");
			Debug.log("Using default size 256...");
			size = DEF_SIZE;
		}

		int[] histogramData = new int[size];
		for (int o = 0; o < size; o++) {
			histogramData[o] = 0;
		}

		int scaleFactor = MAX_SIZE / size;

		for (int i = 0; i < greyScaleData.length; i++) {
			for (int j = 0; j < greyScaleData[i].length; j++) {
				histogramData[greyScaleData[i][j] / scaleFactor] += 1;
			}
		}

		return histogramData;
	}

	/**
	 * Checks if input size is of form 2^x with maximum 256.
	 * @param size
	 * @return
	 */
	private static boolean sizeValid(int size) {
		// see: https://graphics.stanford.edu/~seander/bithacks.html#DetermineIfPowerOf2
		return size > 0 && size <= MAX_SIZE && ((size & (size - 1)) == 0);
	}
}