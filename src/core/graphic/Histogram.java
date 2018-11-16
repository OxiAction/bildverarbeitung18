package core.graphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;

import java.util.ArrayList;

/**
 * This class generates a Canvas based on GreyScaleValues from EvaluationDataSet and EvaluationDataSetEntry's TODO: Make average data double? TODO:
 * Make different sizes possible!
 * 
 * @author Richard Riediger
 */
public class Histogram {
	private static int V = 2; // multiplier for histogram

	/**
	 * Main, only for testing!
	 * 
	 * @param args no args required
	 */
	public static void main(String[] args) {
		get(new EvaluationDataSet(null, null, null, null, null));
	}

	/**
	 * Returns Canvas based on average set data TODO: Split this to multiple methods
	 * 
	 * @param set
	 * @return canvas
	 */
	public static Canvas get(EvaluationDataSet set) {
		ArrayList<int[][]> greyScaleValues = new ArrayList<int[][]>();

		for (EvaluationDataSetEntry entry : set.getEntries()) {
			greyScaleValues.add(entry.getGreyScaleValues());
		}
		// test data
		//		int[][] data1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 1, 1, 3, 3 } };
		//		int[][] data2 = { { 2, 2, 4, 4 }, { 6, 6, 8, 8 }, { 2, 4, 6, 8 } };
		//		int[][] data3 = { { 1, 1, 3, 3 }, { 5, 5, 7, 7 }, { 3, 1, 1, 3 } };
		//		greyScaleValues.add(data1);
		//		greyScaleValues.add(data2);
		//		greyScaleValues.add(data3);

		ArrayList<int[]> greyScaleValues1D = new ArrayList<int[]>();
		int i;
		for (i = 0; i < greyScaleValues.size(); i++) {
			greyScaleValues1D.add(generateHistogram(greyScaleValues.get(i)));
			System.out.println("Generated Histogram: " + i);
			print1dArray(greyScaleValues1D.get(i));
		}

		int[] additionOfAll1DArrays = new int[greyScaleValues1D.get(0).length];
		for (int j = 0; j < greyScaleValues1D.size(); j++) {
			additionOfAll1DArrays = add1DArrays(additionOfAll1DArrays, greyScaleValues1D.get(j));
		}

		System.out.println("Addition of all 1D arrays: ");
		for (int k = 0; k < additionOfAll1DArrays.length; k++) {
			System.out.print(additionOfAll1DArrays[k] + " ");
		}

		System.out.println("\nAverage of all 1D arrays: ");
		for (int k = 0; k < additionOfAll1DArrays.length; k++) {
			additionOfAll1DArrays[k] = additionOfAll1DArrays[k] / i;
			System.out.print(additionOfAll1DArrays[k] + " ");
		}

		return generateNewCanvas(additionOfAll1DArrays);
	}

	/**
	 * Returns Canvas based on average set data
	 * 
	 * @param entry
	 * @return canvas
	 */
	public static Canvas get(EvaluationDataSetEntry entry) {
		return generateCanvas(entry.getGreyScaleValues());
	}

	/**
	 * Calculates the average of all greyScaleValues
	 * 
	 * @param greyScaleValues an ArrayList of all greyScaleValues 2d arrays
	 * @return greyScaleValuesNORMALIZED average 2d int array
	 */
	@SuppressWarnings("unused")
	private static int[][] normalize(ArrayList<int[][]> greyScaleValues) {
		int[][] greyScaleValuesNORMALIZED = createNewArrayOfSize(greyScaleValues.get(0));

		int i;
		for (i = 0; i < greyScaleValues.size(); i++) {
			greyScaleValuesNORMALIZED = add2DArrays(greyScaleValuesNORMALIZED, greyScaleValues.get(i));
		}
		System.out.println("Addition of all greyScaleValues:");
		print2dArray(greyScaleValuesNORMALIZED);

		System.out.println("Average greyScaleValues:");
		greyScaleValuesNORMALIZED = calculateAverage2dArray(greyScaleValuesNORMALIZED, i);

		return greyScaleValuesNORMALIZED;
	}

	/**
	 * creates a new 2d array of the same size as the given one
	 * 
	 * @param arr1
	 * @return arr2
	 */
	private static int[][] createNewArrayOfSize(int[][] arr1) {
		int x = arr1.length;
		int y = arr1[0].length;
		int[][] arr2 = new int[x][y];
		return arr2;
	}

	/**
	 * prints a 2d array to console
	 * 
	 * @param arr
	 */
	private static void print1dArray(int[] arr) {
		for (int j = 0; j < arr.length; j++) {
			System.out.print(arr[j] + " ");
		}
		System.out.println();
	}

	/**
	 * prints a 2d array to console
	 * 
	 * @param arr
	 */
	private static void print2dArray(int[][] arr) {
		for (int j = 0; j < arr.length; j++) {
			for (int k = 0; k < arr[j].length; k++) {
				System.out.print(" " + arr[j][k]);
			}
			System.out.println();
		}
	}

	/**
	 * calculates the average values of an array and prints it to console
	 * 
	 * @param arr1
	 * @param i
	 * @return
	 */
	private static int[][] calculateAverage2dArray(int[][] arr1, int i) {
		for (int j = 0; j < arr1.length; j++) {
			for (int k = 0; k < arr1[j].length; k++) {
				arr1[j][k] = arr1[j][k] / i;
				System.out.print(" " + arr1[j][k]);
			}
			System.out.println();
		}
		return arr1;
	}

	/**
	 * Adds two equally sized 1d arrays to each other
	 * 
	 * @param arr1
	 * @param arr2
	 * @return sum
	 */
	private static int[] add1DArrays(int[] arr1, int[] arr2) {
		int[] sum = new int[arr1.length];
		for (int i = 0; i < arr1.length; i++) {
			sum[i] = arr1[i] + arr2[i];
		}
		return sum;
	}

	/**
	 * Adds two equally sized 2d arrays to each other
	 * 
	 * @param arr1
	 * @param arr2
	 * @return sum
	 */
	private static int[][] add2DArrays(int[][] arr1, int[][] arr2) {
		int[][] sum = new int[arr1.length][arr1[0].length];
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				sum[i][j] = arr1[i][j] + arr2[i][j];
			}
		}
		return sum;
	}

	/**
	 * Generates a histogram of a greyScaleValues 2d array
	 * 
	 * @param data
	 * @return
	 */
	private static Canvas generateCanvas(int[][] data) {
		int width = 562;
		int height = 562;
		Canvas canvas = new Canvas(width, height);

		int[] histogramData = generateHistogram(data);

		drawHistogram(canvas, histogramData);

		return canvas;
	}

	/**
	 * Generates a histogram of a greyScaleValues 2d array
	 * 
	 * @param data
	 * @return
	 */
	private static Canvas generateNewCanvas(int[] data) {
		int width = 562;
		int height = 562;
		Canvas canvas = new Canvas(width, height);

		drawHistogram(canvas, data);

		return canvas;
	}

	/**
	 * Generates the histograms as arrays and stores them in a new ArrayList
	 * 
	 * @param data ArrayList that consists of all images
	 * @return int array of size 100 for every greyscale
	 */
	private static int[] generateHistogram(int[][] data) {
		HistogramData histogramData = new HistogramData(data);
		histogramData.generate();
		//		histogramData.printHistogramData();

		return histogramData.getHistogramData();
	}

	/**
	 * Draws one histogram to canvas using V as a modifier to increase the size TODO: Implement automatic scaling of the diagram with modifier V TODO:
	 * Improve code
	 * 
	 * @param canvas the canvas to draw the histogram on
	 * @param histogram the histogram (int array)
	 */
	private static void drawHistogram(Canvas canvas, int[] histogram) {
		int c1 = 0, c2 = 0;
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(Color.GREY);
		graphicsContext2D.fillRect(0, 0, 562, 562);

		graphicsContext2D.setLineWidth(1);
		graphicsContext2D.setStroke(Color.BLACK);
		graphicsContext2D.strokeLine(0 + 15, 537, 562 - 15, 537); // X axis
		graphicsContext2D.strokeLine(25, 0 + 15, 25, 562 - 15); // Y axis

		graphicsContext2D.strokeLine(562 - 15, 537, 562 - 15 - 5, 537 + 5); // X axis arrow 1
		graphicsContext2D.strokeLine(562 - 15, 537, 562 - 15 - 5, 537 - 5); // X axis arrow 2
		graphicsContext2D.strokeLine(25, 0 + 15, 25 + 5, 25 - 5); // Y axis arrow 1
		graphicsContext2D.strokeLine(25, 0 + 15, 25 - 5, 25 - 5); // Y axis arrow 2

		graphicsContext2D.setLineWidth(0.5);
		graphicsContext2D.setStroke(Color.BLACK);
		for (int i = 25; i < 527; i += 10) {
			graphicsContext2D.strokeLine(i, 537 - 3, i, 537 + 3); // X axis divide lines
			if (c1 == 0) {
				graphicsContext2D.strokeText("" + c1, i, 557, 15);
			}
			c1 = c1 + 10;
			if (c1 % 50 == 0) {
				graphicsContext2D.strokeText("" + c1, i, 557, 15); // X axis numbers
			}
		}
		for (int i = 527; i > 25; i -= 10) {
			graphicsContext2D.strokeLine(25 - 3, i, 25 + 3, i); // Y axis divide lines
			c2 = c2 + 10;
			if (c2 % 50 == 0) {
				graphicsContext2D.strokeText("" + c2, 5, i, 15); // Y axis numbers
			}
		}

		// histogram lines
		for (int h = 0; h < histogram.length; h++) {
			graphicsContext2D.setLineWidth(0.5);
			graphicsContext2D.setStroke(Color.DARKBLUE);
			graphicsContext2D.strokeLine(25 + h * V, 537, 25 + h * V, 537 - histogram[h] * V);
		}
	}
}