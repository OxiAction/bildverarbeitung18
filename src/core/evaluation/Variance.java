package core.evaluation;

/**
 * Class that calculates the variance of an image
 * Only been tested with dummy values yet.
 * @author Richard Riediger
 */
public class Variance {
	/**
	 * Testing variance methods
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

	/**
	 * Calculates the variance of an full image.
	 * @param 	greyScaleData		the grey scale data of the image
	 * @param 	histogramData		the histogram data of the image
	 * @return 	the variance value
	 */
	public static double get(int[][] greyScaleData, int[] histogramData) {
		double meanGreyScale = getMeanGreyScaleValue(greyScaleData, histogramData);
		double numberOfGreyScaleValues = getNumberOfGreyScaleValues(greyScaleData);
		double variance = 0.0;

		for(int j = 0; j < histogramData.length; j++){
			for(int k = 0; k < histogramData[j]; k++){
				variance += Math.pow((j - meanGreyScale), 2);
			}
		}
		//System.out.println("variance: " + variance / (numberOfGreyScaleValues - 1));
		return variance / (numberOfGreyScaleValues - 1);
	}

	/**
	 * Calculates the mean greyscale value of an image based on its greyscale values and histogram
	 * @param 	greyScaleData
	 * @param 	histogramData
	 * @return	the mean greyscale value
	 */
	protected static double getMeanGreyScaleValue(int[][] greyScaleData, int[] histogramData){
		double meanGreyScale = 0.0;

		for(int i = 0; i < histogramData.length; i++){
			meanGreyScale += i * histogramData[i];
		}
		//System.out.println("meanGreyScale: " + meanGreyScale / getNumberOfGreyScaleValues(greyScaleData));
		return meanGreyScale / getNumberOfGreyScaleValues(greyScaleData);
	}

	/**
	 * Calculates the number of all greyscale values of an image
	 * @param 	greyScaleData
	 * @return	the number of greyscale values
	 */
	protected static int getNumberOfGreyScaleValues(int[][] greyScaleData){
		int length = greyScaleData.length;
		int width = greyScaleData[0].length;
		//System.out.println("numberOfGreyScaleValues: " + length * width);
		return length * width;
	}
}