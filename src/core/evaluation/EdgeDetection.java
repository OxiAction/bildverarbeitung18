package core.evaluation;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EdgeDetection {
	
	public static CropData getCropData(BufferedImage bufferedImage, EdgeDetectionConfig edgeDetectionConfig) {
		// general iterators
		int x = 0;
		int y = 0;
		
		// black RGB
		final int black = -16777216;
		
		// original image sizes
		final int orgWidth = bufferedImage.getWidth();
		final int orgHeight = bufferedImage.getHeight();
		
		// x-axis sobel
		final int[][] sobelX = { 
				{ -1, 0, 1 }, 
				{ -2, 0, 2 }, 
				{ -1, 0, 1 } 
			};
		// y-axis sobel
		final int[][] sobelY = { 
				{ 1, 2, 1 }, 
				{ 0, 0, 0 }, 
				{ -1, -2, -1 } 
			};
		
		// we manipulate these values to generate the output image
		int outputWidth = orgWidth;
		int outputHeight = orgHeight;
		int outputStartX = 0;
		int outputStartY = 0;
		
		// black / white tresholds
		int threshold1 = edgeDetectionConfig.threshold1;
		int threshold2 = edgeDetectionConfig.threshold2;
		// position of the finger -> 1 = horizontal, 2 = vertical
		int fingerPosition = edgeDetectionConfig.fingerPosition;
		// fixed (cropping) size (origin -> middle)
		int fixedSizeFromMiddle = edgeDetectionConfig.fixedSizeFromMiddle;
		// find adjacent lines (origin -> middle)
		boolean findAdjacentLinesFromMiddle = edgeDetectionConfig.findAdjacentLinesFromMiddle;
		// edge offset - used for ALL calculations
		int offsetX = edgeDetectionConfig.offsetX;
		int offsetY = edgeDetectionConfig.offsetY;
		// clear top
		boolean clearTop = edgeDetectionConfig.clearTop;
		// clear bottom
		boolean clearBottom = edgeDetectionConfig.clearBottom;

		int[][] edgeRGBData = new int[orgWidth][orgHeight];
		int[][] orgRGBData = getRGB(orgWidth, orgHeight, bufferedImage);

		// image processing
		double[][] luminanceData = getLuminance(orgWidth, orgHeight, orgRGBData);

		for (x = 0; x < orgWidth; ++x) {
			for (y = 0; y < orgHeight; ++y) {

				if (x > 0 && x < orgWidth - 1 && y > 0 && y < orgHeight - 1) {
					int grayX = 0;
					int grayY = 0;
					
					for (int i = -1; i < 2; ++i) {
						for (int j = -1; j < 2; j++) {
							grayX += Math.round(luminanceData[x + i][y + j] * sobelX[1 + i][1 + j]);
							grayY += Math.round(luminanceData[x + i][y + j] * sobelY[1 + i][1 + j]);
						}
					}
					
					int gray = (int) Math.sqrt(grayX * grayX + grayY * grayY);
					
					// threshold
					if (gray > threshold1) {
						gray = 255;
					} else if (gray < threshold2) {
						gray = 0;
					}
					
					int color = new Color(gray, gray, gray).getRGB();
					edgeRGBData[x][y] = color;
				} else {
					edgeRGBData[x][y] = black;
				}

			}
		}

		// detect adjacent lines
		if (findAdjacentLinesFromMiddle) {
			// horizontal
			if (fingerPosition == 1) {
				int startY = orgHeight / 2;
				int value;
				int up = Integer.MAX_VALUE;
				int down = Integer.MIN_VALUE;

				for (x = offsetX; x < orgWidth - 2 * offsetX; ++x) {
					// up
					for (y = startY; y > offsetY; --y) {
						value = edgeRGBData[x][y];

						if (value > black) {
							up = Math.min(up, y);
							break;
						}
					}

					// down
					for (y = startY; y < orgHeight - offsetY; ++y) {
						value = edgeRGBData[x][y];

						if (value > black) {
							down = Math.max(down, y);
							break;
						}
					}
				}

				outputStartY = up;
				outputHeight = down - up;
			}
			// vertical
			else {
				int startX = orgWidth / 2;
				int value;
				int left = Integer.MAX_VALUE;
				int right = Integer.MIN_VALUE;

				for (y = offsetY; y < orgHeight - 2 * offsetY; ++y) {
					// left
					for (x = startX; x > offsetX; --x) {
						value = edgeRGBData[x][y];
						
						if (value > black) {
							left = Math.min(left, x);
							break;
						}
					}

					// right
					for (x = startX; x < orgWidth - offsetX; ++x) {
						value = edgeRGBData[x][y];

						if (value > black) {
							right = Math.max(right, x);
							break;
						}
					}
				}

				outputStartX = left;
				outputWidth = right - left;
			}
		}

		if (fixedSizeFromMiddle > 0) {
			int halfFixedSizeFromMiddle = fixedSizeFromMiddle / 2;
			
			// horizontal
			if (fingerPosition == 1) {
				outputStartY = (orgHeight / 2) - halfFixedSizeFromMiddle;
				outputHeight -= outputStartY + (orgHeight / 2) - halfFixedSizeFromMiddle;
			}
			// vertical
			else {
				outputStartX = (orgWidth / 2) - halfFixedSizeFromMiddle;
				outputWidth -= outputStartX + (orgWidth / 2) - halfFixedSizeFromMiddle;
			}
		}

		if (clearTop) {
			int endY = Integer.MAX_VALUE;
			
			// left to right
			for (x = outputStartX + offsetX; x < outputWidth + outputStartX - 2 * offsetX; ++x) {
				// top down search
				for (y = outputStartY + offsetY; y < outputHeight + outputStartY - 2 * offsetY; ++y) {
					if (edgeRGBData[x][y] > black) {
						endY = Math.min(endY, y);
						break;
					}
				}
			}

			outputStartY += endY;
			outputHeight -= endY;
		}

		if (clearBottom) {
			int endY = 0;
			
			// left to right
			for (x = outputStartX + offsetX; x < outputWidth + outputStartX - 2 * offsetX; ++x) {
				// bottom up search
				for (y = outputHeight + outputStartY - 2 * offsetY; y > outputStartY + offsetY; --y) {
					if (edgeRGBData[x][y] > black) {
						endY = Math.max(endY, y);
						break;
					}
				}
			}

			outputHeight -= (outputHeight - endY);
		}
		
		return new CropData(outputStartX, outputWidth, outputStartY, outputHeight);
	}
	
	/**
	 * Gets RGB values of an image.
	 * 
	 * @param width
	 * @param height
	 * @param bufferedImage
	 * @return
	 */
	public static int[][] getRGB(int width, int height, BufferedImage bufferedImage) {
		int[][] result = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				result[x][y] = bufferedImage.getRGB(x, y);
			}
		}

		return result;
	}

	/**
	 * Calculate the luminance of an image.
	 * 
	 * @param width
	 * @param height
	 * @param rgb
	 * @return
	 */
	public static double[][] getLuminance(int width, int height, int[][] rgb) {
		double[][] result = new double[width][height];

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				Color pixel = new Color(rgb[x][y]);
				result[x][y] = 0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue();
			}
		}
		return result;
	}
}
