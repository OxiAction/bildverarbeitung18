package core.evaluation;

import utils.Utils;

/**
 * Class that calculates the variance of an image.
 */
public class Variance {

	/**
	 * Calculates the variance of an image with the formula V = 1/(n-1) * (Sum1-n(xi - xmean^2)).
	 *
	 * @param greyScaleData		the grey scale data of the image
	 * @param histogramData		the histogram data of the image
	 * @return the variance value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData) {
		double meanGreyScale = Utils.getMeanGreyScaleValue(greyScaleData, histogramData);
		double numberOfGreyScaleValues = Utils.getNumberOfGreyScaleValues(greyScaleData);
		double variance = 0.0;

		for (int j = 0; j < histogramData.length; ++j) {
			for (int k = 0; k < histogramData[j]; ++k) {
				variance += Math.pow((j - meanGreyScale), 2);
			}
		}

		return variance / (numberOfGreyScaleValues - 1);
	}
}