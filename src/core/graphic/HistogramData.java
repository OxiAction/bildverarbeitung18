package core.graphic;

import utils.Debug;

public class HistogramData {
    private int[][] image;

    // 100 shades of grey
    private int[] histogramData = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/**
	 * Empty Constructor
	 */
	public HistogramData() {
	}

	/**
	 * Constructor that initializes image greyscale data
	 * 
	 * @param image
	 */
	public HistogramData(int[][] image) {
		this.image = image;
	}

	/**
	 * Getter for HistogramData
	 * 
	 * @return histogramData int array of 100 grey values
	 */
	public int[] getHistogramData() {
		return this.histogramData;
	}

	/**
	 * Generates the histogram data out of the image and stores it to histogramData
	 */
	public void generate() {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				this.histogramData[image[i][j]] += 1;
			}
		}
	}

	/**
	 * Prints the histogram data to console in three lines
	 */
	public void printHistogramData() {
		Debug.log("HistogramData: ");
		for (int i = 0; i < this.histogramData.length; i++) {
			Debug.log(this.histogramData[i] + " ");
			if ((i % 40 == 0) && (i > 0)) {
				Debug.log("");
			}
		}
	}
}
