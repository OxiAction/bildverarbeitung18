package core.evaluation;

import utils.Debug;

/**
 * TODO description
 */
public class Histogram {
	protected static final int MAX_SIZE = 256;
	protected static final int DEF_SIZE = 256;

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram with DEF_SIZE.
	 * 
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @return the histogram
	 */
	public static int[] get(int[][] greyScaleData) {
		return get(greyScaleData, DEF_SIZE);
	}

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram.
	 * 
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @param size				the scaled size (power of two with maximum MAX_SIZE)
	 * @return the histogram
	 */
	public static int[] get(int[][] greyScaleData, int size) {
		// check if size is valid (power of two with maximum MAX_SIZE)
		// see: https://graphics.stanford.edu/~seander/bithacks.html#DetermineIfPowerOf2
		if (!(size > 0 && size <= MAX_SIZE && ((size & (size - 1)) == 0))) {
			Debug.log("Invalid histogram size, please use 256, 128, 64, 32, 16, 8, 4 or 2.");
			Debug.log("Using default size " + DEF_SIZE + ".");
			size = DEF_SIZE;
		}

		int[] histogramData = new int[size];
		int i;
		for (i = 0; i < size; ++i) {
			histogramData[i] = 0;
		}

		int scaleFactor = MAX_SIZE / size;

		for (i = 0; i < greyScaleData.length; ++i) {
			for (int j = 0; j < greyScaleData[i].length; ++j) {
				histogramData[greyScaleData[i][j] / scaleFactor] += 1;
			}
		}

		return histogramData;
	}
}