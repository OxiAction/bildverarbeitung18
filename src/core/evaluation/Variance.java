package core.evaluation;

import utils.Debug;
import utils.Utils;

/**
 * Class that calculates the variance of an image
 * Only been tested with dummy values yet.
 * @author Richard Riediger
 */
public class Variance {
	/**
	 * Calculates the variance of an image with the formula V = 1/(n-1) * (Sum1-n(xi - xmean^2)).
	 *
	 * @param 	greyScaleData		the grey scale data of the image
	 * @param 	histogramData		the histogram data of the image
	 * @return 	the variance value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData) {
		return get(greyScaleData, histogramData, false);
	}
	
	/**
	 * Calculates the variance of an image with the formula V = 1/(n-1) * (Sum1-n(xi - xmean^2)).
	 *
	 * @param 	greyScaleData		the grey scale data of the image
	 * @param 	histogramData		the histogram data of the image
	 * @param 	usingLocalVariance  boolean - tells us if we are only using part of an image
	 * @return 	the variance value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData, boolean usingLocalVariance) {
		double meanGreyScale = Utils.getMeanGreyScaleValue(greyScaleData, histogramData);
		double numberOfGreyScaleValues = Utils.getNumberOfGreyScaleValues(greyScaleData);
		double variance = 0.0;
		if (!usingLocalVariance) {
			Debug.log("Calculating Variance...");
		} else {
			Debug.log("Calculating local Variance...");
		}

		for (int j = 0; j < histogramData.length; j++) {
			for (int k = 0; k < histogramData[j]; k++) {
				variance += Math.pow((j - meanGreyScale), 2);
			}
		}
		//Debug.log("variance: " + variance / (numberOfGreyScaleValues - 1));
		return variance / (numberOfGreyScaleValues - 1);
	}

	/**
	 * Testing variance methods
	 *
	 * @param args
	 */
	//	public static void main(String[] args) {
	//		int[][] greyScaleData = {{5, 6, 7}, {1, 2, 3}};
	//		int[] histogramData = Histogram.get(greyScaleData);
	//		System.out.println("histogramdata: ");
	//		for(int i = 0; i < histogramData.length; i++){
	//			System.out.print(histogramData[i] + " ");
	//		}
	//		System.out.println();
	//		get(greyScaleData, histogramData);
	//	}
}