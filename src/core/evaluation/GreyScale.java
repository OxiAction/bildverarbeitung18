package core.evaluation;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreyScale {
	/**
	 * Load an image, based on its absolute file path and fetches / returns its grey scale data.
	 * 
	 * @param absoluteFilePath	the absolute file path of an image
	 * @return the result
	 * @throws IOException
	 */
	public static int[][] get(String absoluteFilePath) throws IOException {
		BufferedImage image;

		try {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			image = ImageIO.read(new File(absoluteFilePath));

			int height = image.getHeight();
			int width = image.getWidth();
			int[][] result = new int[width][height];
			Raster raster = image.getData();

			for (int col = 0; col < height; col++) {
				for (int row = 0; row < width; row++) {
					result[row][col] = raster.getSample(row, col, 0);
				}
			}

			return result;
		} catch (IOException e) {
			throw new IOException("IOException: " + e);
		}
	}
}
