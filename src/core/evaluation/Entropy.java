package core.evaluation;

/**
 * TODO description
 */
public class Entropy {
	/**
	 * Calculates the entropy of an full image with the formula E = Sum( p(g) * (-log(p(g)) ).
	 * TODO: don't need greyScaleData currently, but might need it for local entropy / part of an image
	 * 
	 * @param greyScaleData		the grey scale data of the image
	 * @param histogramData		the histogram data of the image
	 * @return the entropy value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData) {
		// get number of greyscale values, could also use greyScaleData x and y size and use x*y?
		double numberOfGreyScaleValues = 0;
		for(int i = 0; i < histogramData.length; i++){
			numberOfGreyScaleValues += histogramData[i];
		}

		// calculate probabilities of each greyScaleValue
		// also get Information value with these probabilities with log2
		// then get the values needed for calculating the entropy
		// then add these values to entropy result
		double[] entropyValues = new double[histogramData.length];
		double probabilityOfHistogramData = 0;
		double entropy = 0;
		for(int j = 0; j < histogramData.length; j++){
			probabilityOfHistogramData = histogramData[j] / numberOfGreyScaleValues;
			entropyValues[j] = (-1) * (Math.log(probabilityOfHistogramData)/Math.log(2)) * probabilityOfHistogramData;
			if(entropyValues[j] > 0) {
				entropy += entropyValues[j];
			}
		}
		return entropy;
	}
}
