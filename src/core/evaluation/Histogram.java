package core.evaluation;

import utils.Debug;
import utils.Utils;

/**
 * Generates histograms based on grey scale data.
 * The histogram can be absolute or relative and have varying sizes.
 */
public class Histogram {
	protected static final int MAX_SIZE = 256;
	protected static final int DEF_SIZE = 256;
	protected static final boolean USE_REL_HISTOGRAM = false;
	protected static final int NR_OF_REL_VALS = 100;

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram with DEF_SIZE.
	 * 
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @return the histogram
	 */
	public static int[] get(int[][] greyScaleData) {
		return get(greyScaleData, DEF_SIZE, USE_REL_HISTOGRAM);
	}

	/**
	 * Takes a two dimensional greyScale value array and generates a histogram.
	 * 
	 * @param greyScaleData		the greyScale values of an image, an 2D int array
	 * @param size				the scaled size (power of two with maximum MAX_SIZE)
	 * @param relative			use rounded percentages instead of absolute numbers
	 * @return the histogram
	 */
	public static int[] get(int[][] greyScaleData, int size, boolean relative) {
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

		if(relative) {
			//generateRelativeHistogram(greyScaleData, histogramData);
		}

		return histogramData;
	}

	/**
	 * Converts a Histogram from absolute values to a Histogram with approximate relative values,
	 *
	 * @param greyScaleData	the greyScaleData is needed for getting the absolute number of values
	 * @param histogramData	the Histogram to be converted
	 */
	protected static void generateRelativeHistogram(int[][] greyScaleData, int[] histogramData){
		int nrOfVals = Utils.getNumberOfGreyScaleValues(greyScaleData);
		double relativeHistogramValue;
		for (int i = 0; i < histogramData.length; i++) {
			relativeHistogramValue = ((double) histogramData[i] / (double) nrOfVals) * NR_OF_REL_VALS;
			histogramData[i] = (int) Math.round(relativeHistogramValue);
		}
	}

//	/**
//	 * Testing method for relative histogram
//	 */
//	public static void main(String[] args) {
//		int[][] testArray1 = {{1,2,3},{3,4,6}};
//		get(testArray1, 256, true);
//	}
}