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

//		Debug.log("HistogramData: ");
//		for (int i = 0; i < histogramData.length; i++) {
//			Debug.log(histogramData[i] + " ");
//			if ((i % 40 == 0) && (i > 0)) {
//				Debug.log("");
//			}
//		}

		return histogramData;
	}
	
	/**
	 * TODO [Richard]
	 * 
	 * @param greyScaleData
	 * @param histogramData
	 * @return
	 */
	public static double getEntropyData(int[][] greyScaleData, int[] histogramData) {
		
		return 0.0;
	}
}
