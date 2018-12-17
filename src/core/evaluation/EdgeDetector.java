package core.evaluation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that detects edges of an image
 * Based on below author, changed a bit
 * @author TheMorpheus407
 */
public class EdgeDetector {
	protected static final int THRESHOLD_1 = 255;
	protected static final int THRESHOLD_2 = 0;
	protected static final int MAX_VAL = 255;
	protected static final int MIN_VAL = 0;

	// wie 1. Sobel Operator
	protected static final int[][] OPERATOR_1 = {
			{-1,0,1},
			{-2,0,2},
			{-1,0,1}
	};
	// wie 2. Sobel Operator, aber gespiegelt um x-Achse
	protected static final int[][] OPERATOR_2 = {
			{1,2,1},
			{0,0,0},
			{-1,-2,-1}
	};

	/**
	 * Main zum Testen
	 * @param args
	 */
	public static void main(String[] args) {
		detectEdgesFrom(new File("C:\\Users\\Vacou\\Desktop\\test_bilder\\clown.jpg"),
				true);
	}

	/**
	 * Detects edges from an image file of given path
	 * Writes the output file to the same folder as the input file with name "EDGES_<input file>"
	 * @param image
	 * @param generateOutput
	 * @return	the output file
	 */
	public static File detectEdgesFrom(File image, boolean generateOutput){
		BufferedImage img = null;
		try {
			img = ImageIO.read(image);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		int width    = img.getWidth();
		int height   = img.getHeight();

		double[][] lum = luminance(img);

		for(int x = 1; x < width -1 ; x++){
			for(int y = 1; y < height -1; y++)
			{
				int grayx = 0;
				int grayy = 0;
				for(int i = -1; i < 2; i++)
				{
					for(int j = -1; j < 2; j++)
					{
						grayx += (int) Math.round(lum[x+i][y+j] * OPERATOR_1[1+i][1+j]);
						grayy += (int) Math.round(lum[x+i][y+j] * OPERATOR_2[1+i][1+j]);
					}
				}
				int gray = cutEdges((int)Math.sqrt(grayx * grayx + grayy * grayy));
				img.setRGB(x, y, new Color(gray,gray,gray).getRGB());
			}
		}

		File f = null;
		try{
			f = new File(image.getParent() + "\\" + "EDGES_" + image.getName());
			if(generateOutput) {
				ImageIO.write(img, "jpg", f);
			}
		}catch(IOException e){
			System.out.println(e);
		}
		return f;
	}

	/**
	 * Apply the two thresholds to the image to detect the edges
	 * @param alt
	 * @return
	 */
	private static int cutEdges(int alt){
		if (alt > THRESHOLD_1)
			return MAX_VAL;
		else if (alt < THRESHOLD_2)
			return MIN_VAL;
		else
			return alt;
	}

	/**
	 * Calculate the luminance of an image
	 * @param img 	the image
	 * @return		the luminance
	 */
	private static double[][] luminance(BufferedImage img)
	{
		double[][] ret = new double[img.getWidth()][img.getHeight()];
		for(int x = 0; x < img.getWidth(); x++)
		{
			for(int y = 0; y < img.getHeight(); y++)
			{
				Color pixel = new Color(img.getRGB(x, y));
				ret[x][y] = 0.299*pixel.getRed() + 0.587*pixel.getGreen() + 0.114*pixel.getBlue();
			}
		}
		return ret;
	}
}
