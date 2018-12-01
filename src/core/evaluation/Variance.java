package core.evaluation;


import static utils.Utils.getMeanGreyScaleValue;
import static utils.Utils.getNumberOfGreyScaleValues;

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
	 * @param 	usingLocalVariance  tells us if we are only using part of an image
	 * @return 	the variance value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData, boolean usingLocalVariance) {
		double meanGreyScale = getMeanGreyScaleValue(greyScaleData, histogramData);
		double numberOfGreyScaleValues = getNumberOfGreyScaleValues(greyScaleData);
		double variance = 0.0;
		if(!usingLocalVariance){
			System.out.println("Calculating Variance...");
		}
		else{
			System.out.println("Calculating local Variance...");
		}

		for(int j = 0; j < histogramData.length; j++){
			for(int k = 0; k < histogramData[j]; k++){
				variance += Math.pow((j - meanGreyScale), 2);
			}
		}
		//System.out.println("variance: " + variance / (numberOfGreyScaleValues - 1));
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