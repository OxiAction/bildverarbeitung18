package core.evaluation;

/**
 * TODO description
 */
public class Metric {
	public static final String NAME_CORRELATION = "Correlation";
	public static final String NAME_CHI_SQUARE = "Chi-Square";
	public static final String NAME_INTERSECTION = "Intersection";
	public static final String NAME_BHATTACHARYY_DISTANCE = "Bhattacharyya Distance";

	protected static final String[] NAMES = { NAME_CORRELATION, NAME_CHI_SQUARE, NAME_INTERSECTION, NAME_BHATTACHARYY_DISTANCE };

	/**
	 * Returns value of two histograms, based on correlation metric.
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static double getCorrelationData(int[] h1, int[] h2) {
		double n = 0, d1 = 0, d2 = 0;
		double h1avg = getAverage(h1);
		double h2avg = getAverage(h2);

		for (int i = 0; i < h1.length; ++i) {
			n += (h1[i] - h1avg) * (h2[i] - h2avg);
			d1 += Math.pow((h1[i] - h1avg), 2);
			d2 += Math.pow((h2[i] - h2avg), 2);
		}
		return (n / Math.sqrt(d1 * d2));
	}

	/**
	 * Returns value of two histograms, based on chi-square metric.
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static double getChiSquareData(int[] h1, int[] h2) {
		double d = 0;
		for (int i = 0; i < h1.length; ++i) {
			// see https://stackoverflow.com/questions/21058192/implementing-chi-square-to-find-distance-between-2-sift-feature
			
			int div = h1[i]; // + h2[i];
			
			// prevent division by zero leading to NaN
			if (div == 0) {
				continue;
			}
			d += (h1[i] - h2[i]) * (h1[i] - h2[i]) / div;
		}
		return d;
	}

	/**
	 * Returns value of two histograms, based on intersection metric.
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static double getIntersectionData(int[] h1, int[] h2) {
		double d = 0;
		for (int i = 0; i < h1.length; ++i) {
			d += Math.min(h1[i], h2[i]);
		}
		return d;
	}

	/**
	 * Returns value of two histograms, based on bhattacharyya distance metric.
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static double getBhattacharyyaDistanceData(int[] h1, int[] h2) {
		double s = 0, q;
		for (int i = 0; i < h1.length; ++i) {
			s += Math.sqrt(h1[i] * h2[i]);
		}

		q = 1 / (Math.sqrt(getAverage(h1) * getAverage(h2) * Math.pow(h1.length, 2)));

		double r = q * s;
		// prevent negative root
		if (r > 1) {
			return 0;
		}
		return Math.sqrt(1 - r);
	}

	/**
	 * Get metric data by metric name.
	 * 
	 * @param name
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static double getDataByName(String name, int[] h1, int[] h2) {
		switch (name) {
		case NAME_CORRELATION:
			return getCorrelationData(h1, h2);
		case NAME_CHI_SQUARE:
			return getChiSquareData(h1, h2);
		case NAME_INTERSECTION:
			return getIntersectionData(h1, h2);
		case NAME_BHATTACHARYY_DISTANCE:
			return getBhattacharyyaDistanceData(h1, h2);
		default:
			throw new Error("Unknwon metric Name: " + name);
		}
	}

	/**
	 * Returns all available metric names as array.
	 * 
	 * @return
	 */
	public static String[] getNames() {
		return NAMES;
	}

	/**
	 * Calculates the average of histogram.
	 * 
	 * @param histogram
	 * @return
	 */
	protected static double getAverage(int[] histogram) {
		int avg = 0;
		for (int i = 0; i < histogram.length; ++i) {
			avg += histogram[i];
		}
		return (avg / histogram.length);

	}
}
