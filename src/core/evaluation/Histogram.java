package core.evaluation;

/**
 * TODO description
 */
public class Histogram {
	/**
	 * Takes a two dimensional greyScale value array and generates a histogram with size 256 out of it.
	 * 
	 * @param 	greyScaleData	the greyScale values of an image, an 2D int array
	 * @return	the histogram
	 */
	public static int[] get(int[][] greyScaleData) {
		return get(greyScaleData, 256);
	}
	
	/**
	 * Takes a two dimensional greyScale value array and generates a histogram out of it.
	 * 
	 * TODO implement scalable histogram. Use parameter size for this
	 * 
	 * @param 	greyScaleData	the greyScale values of an image, an 2D int array
	 * @param 	size			the scaled size (e.g. 16 32 64 128 256)
	 * @return	the histogram
	 */
	public static int[] get(int[][] greyScaleData, int size) {
		int[] histogramData = new int[size];
		for (int o = 0; o < size; o++){
			histogramData[o] = 0;
		}
		
		for (int i = 0; i < greyScaleData.length; i++) {
			for (int j = 0; j < greyScaleData[i].length; j++) {
				histogramData[greyScaleData[i][j]] += 1;
			}
		}

		return histogramData;
	}
}