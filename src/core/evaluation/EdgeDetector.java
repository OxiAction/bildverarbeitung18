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
	public static void main(String[] args) {
		detectEdgesFrom("C:\\Users\\Vacou\\Desktop\\test_bilder\\sub1\\sub1_1","img_sub1_1.jpg");
	}

	/**
	 * Detects edges from an image file of given path
	 * Writes the output file to the same folder as the input file with name "EDGES_<input file>"
	 * @param path
	 * @return	the output file
	 */
	public static File detectEdgesFrom(String path, String fileName){
		int[][] filter1 = {
				{-1,0,1},
				{-2,0,2},
				{-1,0,1}
		};
		int[][] filter2 = {
				{1,2,1},
				{0,0,0},
				{-1,-2,-1}
		};

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path + "\\" + fileName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		int width    = img.getWidth();
		int height   = img.getHeight();

		double[][] lum = luminanz(img);

		for(int x = 1; x < width -1 ; x++){
			for(int y = 1; y < height -1; y++)
			{
				int grayx = 0;
				int grayy = 0;
				for(int i = -1; i < 2; i++)
				{
					for(int j = -1; j < 2; j++)
					{
						grayx += (int) Math.round(lum[x+i][y+j] * filter1[1+i][1+j]);
						grayy += (int) Math.round(lum[x+i][y+j] * filter2[1+i][1+j]);
					}
				}
				int gray = cutEdges((int)Math.sqrt(grayx * grayx + grayy * grayy));
				img.setRGB(x, y, new Color(gray,gray,gray).getRGB());
			}
		}

		File f = null;
		try{
			f = new File(path + "\\" + "EDGES_" + fileName);
			ImageIO.write(img, "jpg", f);
		}catch(IOException e){
			System.out.println(e);
		}
		return f;
	}

	/**
	 *
	 * @param alt
	 * @return
	 */
	private static int cutEdges(int alt){
		if (alt > 255)
			return 255;
		else if (alt < 0)
			return 0;
		else
			return alt;
	}

	private static double[][] luminanz(BufferedImage img)
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
