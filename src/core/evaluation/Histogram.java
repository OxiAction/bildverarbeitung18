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

	protected static final String[] TYPES = { TYPE_GREY_SCALE, TYPE_ENTROPY, TYPE_VARIANCE };

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

		if (relative) {
			generateRelativeHistogram(greyScaleData, histogramData);
		}

		return histogramData;
	}

	/**
	 * Takes a two dimensional double array (e.g. of local entropies / variances) and generates an int histogram
	 * of given size that "tries to" represent the double values as good as possible.
	 * 
	 * @param doubleValues
	 * @param size
	 * @param type
	 * @param sliceX
	 * @param sliceY
	 * @return
	 */
	public static int[] getIntHistogramForDoubleValues(double[][] doubleValues, int size, String type, int sliceX, int sliceY) {
		// check if size is valid (power of two with maximum MAX_SIZE)
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

		// now try to generate a perfect histogram for the different entropies
		// 1) first get min and max values
		double min, max;
		if (type.equals(Histogram.TYPE_ENTROPY)) {
			min = 0;
			max = 8;
		} else if (type.equals(Histogram.TYPE_VARIANCE)) {
			if (sliceX == 4 && sliceY == 4) {
				//min = 1.986029372515949;	// 5%-95%% (result: 14%)
				//max = 9082.468083259373;
				//min = 2.999892274383688E-4; // 2.5%-97.5%% (result: 14%-26%)
				//max = 11840.080689736023;
				min = 21.378638265041502; // 10%-90% (result: 22%-32%)
				max = 6792.04574964503;
				//min = 325.296819604574;		// 20-80% (result: 4%))
				//max = 4544.297120470115;
				//min = 100.78968320383647;		// 15-85% (result: 22-25%)
				//max = 5546.6457249204905;
			} else if (sliceX == 10 && sliceY == 10) {
				//min = 0.0;						// 5%-95%% (result: <80)
				//max = 1912411.4115113223;
				min = 2.379072109440856; // 10%-90% (result: 83-86%)
				max = 1098258.5777420376;
				//min = 280.96180477694037;		// 15-85% (result: <83)
				//max = 745976.322822115;
			} else if (sliceX == 20 && sliceY == 20) {
				//min = 0.0;						// 5%-95%% (result: 89%)
				//max = 3.926542259883886E7;
				min = 12.521160256272704; // 10%-90% (result: 86% (aber bei 128 bins: 89%))
				max = 2.359251175568138E7;
				//min = 5646.846325511509;		// 15-85% (result: worst)
				//max = 1.6063655037549574E7;
			} else {
				min = 0;
				max = 7000000;
			}
		} else {
			throw new IllegalArgumentException("Please choose \"Entropy\" or \"Variance\" as type parameter!");
		}
		// 2) now generate an array with "size" (def. 32) values between min and max
		double[] allowedValues = new double[size];

		size--;
		double diff = max - min;
		for (i = 0; i < allowedValues.length; i++) {
			allowedValues[i] = min + diff / size * i;
		}

		// 3) now go through all entropy values that we have and assign each one to the closest value
		//    in our allowedEntropyValues array
		// 4) then generate the histogram with the closest index
		int index = 0;
		for (i = 0; i < doubleValues.length; ++i) {
			for (int j = 0; j < doubleValues[i].length; ++j) {
				// here we do 3)
				index = getNearestAllowedValue(allowedValues, doubleValues[i][j]);
				// here 4)
				intHistogramForDoubleValues[index] += 1;
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
	protected static int getNearestAllowedValue(double[] elements, double value) {
		double difference = Math.abs(elements[0] - value);

		int i = 0;
		for (i = 1; i < elements.length; i++) {
			if (Math.abs(elements[i] - value) < difference) {
				difference = Math.abs(elements[i] - value);
			} else {
				i = i - 1;
				break;
			}
		}

		// special case
		if (i == elements.length) {
			i = i - 1;
		}

		return i;
	}

	/**
	 * Converts a Histogram from absolute values to a Histogram with approximate relative values,
	 *
	 * @param greyScaleData	the greyScaleData is needed for getting the absolute number of values
	 * @param histogramData	the Histogram to be converted
	 */
	protected static void generateRelativeHistogram(int[][] greyScaleData, int[] histogramData) {
		int nrOfVals = Utils.getNumberOfGreyScaleValues(greyScaleData);
		double relativeHistogramValue;
		for (int i = 0; i < histogramData.length; i++) {
			relativeHistogramValue = ((double) histogramData[i] / (double) nrOfVals) * NR_OF_REL_VALS;
			histogramData[i] = (int) Math.round(relativeHistogramValue);
		}
	}

	/**
	 * Returns all available histogram types as array.
	 * 
	 * @return
	 */
	public static String[] getTypes() {
		return TYPES;
	}
}