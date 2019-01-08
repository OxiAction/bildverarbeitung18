package core.evaluation;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * TODO description
 */
public class GreyScale {
	/**
	 * Load an image, based on its absolute file path and fetches / returns its grey scale data.
	 * 
	 * @param absoluteFilePath	the absolute file path of an image
	 * @return the result
	 * @throws IOException
	 */
	public static int[][] get(String absoluteFilePath) throws IOException {
		return get(absoluteFilePath, null);
	}
	
	/**
	 * Load an image, based on its absolute file path and fetches / returns its grey scale data (using crop data).
	 * 
	 * @param absoluteFilePath
	 * @param cropData
	 * @return
	 * @throws IOException
	 */
	public static int[][] get(String absoluteFilePath, CropData cropData) throws IOException {
		BufferedImage image;

		try {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			image = ImageIO.read(new File(absoluteFilePath));
			
			int x = 0;
			int width = image.getWidth();
			int y = 0;
			int height = image.getHeight();
			
			if (cropData != null) {
				x = cropData.x;
				width = cropData.width;
				y = cropData.y;
				height = cropData.height;
			}
			
			int[][] result = new int[width][height];
			Raster raster = image.getData();

			for (x = 0; x < width; ++x) {
				for (y = 0; y < height; ++y) {
					result[x][y] = raster.getSample(x, y, 0);
				}
			}

			return result;
		} catch (IOException e) {
			throw new IOException("IOException: " + e);
		}
	}
}
