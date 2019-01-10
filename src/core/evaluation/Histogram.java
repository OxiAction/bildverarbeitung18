package core.evaluation;

import utils.Debug;
import utils.Utils;

/**
 * Generates histograms based on grey scale data.
 * The histogram can be absolute or relative and have varying sizes.
 */
public class Histogram {
	public static final String TYPE_GREY_SCALE = "histogram_grey_scale";
	public static final String TYPE_ENTROPY = "histogram_entropy";
	public static final String TYPE_VARIANCE = "histogram_variance";
	
	protected static final int MAX_SIZE = 256;
	protected static final int DEF_SIZE = 256;
	protected static final int DEF_SIZE_DOUBLE_TO_INT = 32;
	protected static final boolean USE_REL_HISTOGRAM = false;
	protected static final int NR_OF_REL_VALS = 10000;

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
			generateRelativeHistogram(greyScaleData, histogramData);
		}

		return histogramData;
	}

//	public static void main(String[] args) {
//		double[][] localEntropies = {{-7, 1.0, 5.0}, {-6.0, 7.0, 8.0}};
//		int[] x = getIntHistogramForDoubleValues(localEntropies, 4);
//		System.out.println("Local entropies: ");
//		for(int i = 0; i < x.length; i++){
//			System.out.println("x[" + i +"]: " + x[i]);
//		}
//	}

	/**
	 * Takes a two dimensional double array (e.g. of local entropies / variances) and generates an int histogram
	 * of given size that "tries to" represent the double values as good as possible.
	 *
	 * @param doubleValues		the double values to be represented (e.g. local entropies / variances)
	 * @param size				the size (power of two with maximum MAX_SIZE)
	 * @return the histogram
	 */
	public static int[] getIntHistogramForDoubleValues(double[][] doubleValues, int size) {
		// check if size is valid (power of two with maximum MAX_SIZE)
		// see: https://graphics.stanford.edu/~seander/bithacks.html#DetermineIfPowerOf2
		if (!(size > 0 && size <= MAX_SIZE && ((size & (size - 1)) == 0))) {
			Debug.log("Invalid histogram size, please use 256, 128, 64, 32, 16, 8, 4 or 2.");
			Debug.log("Using default size " + DEF_SIZE_DOUBLE_TO_INT + ".");
			size = DEF_SIZE_DOUBLE_TO_INT;
		}

		int[] intHistogramForDoubleValues = new int[size];
		int i;
		for (i = 0; i < size; ++i) {
			intHistogramForDoubleValues[i] = 0;
		}

		//int scaleFactor = MAX_SIZE / size;	// seems to be not needed anymore

		// now try to generate a perfect histogram for the different entropies
		// 1) first find min and max values
		double min = doubleValues[0][0], max = doubleValues[0][0];
		for (i = 0; i < doubleValues.length; i++){
			for (int j = 0; j < doubleValues[i].length; ++j) {
				if(doubleValues[i][j] < min){
					min = doubleValues[i][j];
				}
				if(doubleValues[i][j] > max){
					max = doubleValues[i][j];
				}
			}
		}
		// 2) now generate an array with "size" (def. 32) values between min and max
		double[] allowedValues = new double[size];
		for (i = 0; i < allowedValues.length; i++){
			allowedValues[i] = min + (max-min) / (size-1) * i;
//			System.out.println("allowedValues[" + i + "]: " + allowedValues[i]);
		}
//		System.out.println("min: " + min);
//		System.out.println("max: " + max);

		// 3) now go through all entropy values that we have and assign each one to the closest value
		//    in our allowedEntropyValues array
		// 4) then generate the histogram with the closest index
		for (i = 0; i < doubleValues.length; ++i) {
			for (int j = 0; j < doubleValues[i].length; ++j) {
				// here we do 3)
				int index = getNearestAllowedValue(allowedValues, doubleValues[i][j]);
				// here 4)
				intHistogramForDoubleValues[index /* /scaleFactor */] += 1;
			}
		}

		return intHistogramForDoubleValues;
	}

	/**
	 * For given array and a value this method will find the array element that is closest to the value
	 *
	 * @param elements	the array where we want to find the closest element
	 * @param value		the value
	 * @return		the index of this value
	 */
	protected static int getNearestAllowedValue(double[] elements, double value){
		double difference = Math.abs(elements[0] - value);

		int i = 0;
		for(i = 1; i < elements.length; i++){
			if(Math.abs(elements[i] - value) < difference){
				difference = Math.abs(elements[i] - value);
			}
			else{
				i = i - 1;
//				System.out.println("allowed value: " + elements[i]);
				break;
			}
		}

		// special case
		if(i == elements.length){
			i = i - 1;
		}

//		System.out.println("allowed value: " + elements[i]);
//		System.out.println("Returning i: " + i);
		return i;
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