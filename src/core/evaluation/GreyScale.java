package core.evaluation;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * TODO description
 */
public class GreyScale {
	/**
	 * Fetches / returns grey scale data of an image.
	 * 
	 * @param bufferedImage
	 * @return the result
	 */
	public static int[][] get(BufferedImage bufferedImage) {
		return get(bufferedImage, null);
	}
	
	/**
	 * Fetches / returns grey scale data (using crop data) of an image.
	 * 
	 * @param bufferedImage
	 * @param cropData
	 * @return
	 */
	public static int[][] get(BufferedImage bufferedImage, CropData cropData) {
		int x = 0;
		int width = bufferedImage.getWidth();
		int y = 0;
		int height = bufferedImage.getHeight();
		
		if (cropData != null) {
			x = cropData.x;
			width = cropData.width;
			y = cropData.y;
			height = cropData.height;
		}
		
		int[][] result = new int[width][height];
		Raster raster = bufferedImage.getData();

		for (x = 0; x < width; ++x) {
			for (y = 0; y < height; ++y) {
				result[x][y] = raster.getSample(x, y, 0);
			}
		}

		return result;
	}
}
