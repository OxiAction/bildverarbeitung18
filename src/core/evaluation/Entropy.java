package core.evaluation;

import static utils.Utils.getNumberOfGreyScaleValues;

/**
 * Class that calculates the entropy of an image
 * @author Richard Riediger
 */
public class Entropy {
	/**
	 * Calculates the entropy of an image with the formula E = Sum( p(g) * (-log(p(g)) ).
	 *
	 * @param greyScaleData		the grey scale data of the image
	 * @param histogramData		the histogram data of the image
	 * @return the entropy value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData, boolean usingLocalEntropy) {
		double numberOfGreyScaleValues = getNumberOfGreyScaleValues(greyScaleData);

		if(!usingLocalEntropy){
			System.out.println("Calculating Entropy...");
		}
		else{
			System.out.println("Calculating local Entropy...");
		}

		// calculate probabilities of each greyScaleValue
		// also get Information value with these probabilities with log2
		// then get the values needed for calculating the entropy
		// then add these values to entropy result
		double[] entropyValues = new double[histogramData.length];
		double probabilityOfHistogramData = 0;
		double entropy = 0;
		for (int j = 0; j < histogramData.length; j++) {
			probabilityOfHistogramData = histogramData[j] / numberOfGreyScaleValues;
			entropyValues[j] = (-1) * (Math.log(probabilityOfHistogramData) / Math.log(2)) * probabilityOfHistogramData;
			if (entropyValues[j] > 0) {
				entropy += entropyValues[j];
			}
		}
		return entropy;
	}
}
