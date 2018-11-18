package core.evaluation;

import utils.Debug;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.imageio.ImageIO;


public class ImageReader {
	// 256 shades of grey
	private static int[] histogramData = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public static int [][] getGreyScaleData(String absoluteFilePath) throws IOException {
		BufferedImage image;
		
		try{
			image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			image = ImageIO.read(new File(absoluteFilePath));
			
			int height = image.getHeight();
			int width = image.getWidth();
			int[][] result = new int [width][height];
			Raster raster = image.getData();
			
			for(int col = 0; col < height; col++) {
				for(int row = 0; row < width; row++) {
					result[row][col] = raster.getSample(row,col,0);
				}	
			}
			
			return result;	
		} catch(IOException e) {
			throw new IOException("IOException: " + e);
		}
	}

	/**
	 * TODO [Richard] test if this works
	 * Takes a two dimensional greyScale value array and generates a histogram out of it
	 * Also outputs the histogram data with Debug.log
	 * 
	 * @param 	greyScaleData	the greyScale values of an image, an 2D int array
	 * @return	the histogram, an 1D int array
	 */
	public static int[] getHistogramData(int[][] greyScaleData) {
		for (int i = 0; i < greyScaleData.length; i++) {
			for (int j = 0; j < greyScaleData[i].length; j++) {
				histogramData[greyScaleData[i][j]] += 1;
			}
		}

		Debug.log("HistogramData: ");
		for (int i = 0; i < histogramData.length; i++) {
			Debug.log(histogramData[i] + " ");
			if ((i % 40 == 0) && (i > 0)) {
				Debug.log("");
			}
		}

		return histogramData;
	}

//	Main for testing getEntropyData
//	public static void main(String[] args) {
//		int[] x = {1,1,1,1,1,5};
//		double e = getEntropyData(null,x);
//		System.out.println("Entropy: " + e);
//	}

	/**
	 * Calculates the entropy of an full image with the formula E = Sum( p(g) * (-log(p(g)) )
	 * TODO: don't need greyScaleData currently, but might need it for local entropy / part of an image
	 * @param greyScaleData
	 * @param histogramData
	 * @return
	 */
	public static double getEntropyData(int[][] greyScaleData, int[] histogramData) {
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
