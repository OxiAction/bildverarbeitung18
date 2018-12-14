package core.graphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Debug;
import utils.Utils;
import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;

import java.util.ArrayList;

/**
 * This class generates a Canvas based on GreyScaleValues from EvaluationDataSet and EvaluationDataSetEntry's.
 * TODO: Make average data double?
 * TODO: Make different sizes possible!
 */
public class Histogram {
	private static int V = 2; // multiplier for histogram
	private static int H = 256; // number of histogram values
	private static int LINEWIDTH = 1;

	/**
	 * Returns Canvas based on whole set.
	 * TODO: Split this to multiple methods
	 * 
	 * @param set
	 * @return canvas
	 */
	public static Canvas get(EvaluationDataSet set) {
		ArrayList<int[][]> greyScaleValues = new ArrayList<int[][]>();

		for (EvaluationDataSetEntry entry : set.getEntries()) {
			int[][] greyScaleData = entry.getGreyScaleData();
			if (greyScaleData != null) {
				greyScaleValues.add(greyScaleData);
			}
		}
		
		if (greyScaleValues.size() == 0) {
			Debug.log("Histogram @ get: could not generate a Canvas because of missing grey scale data(s)!");
			return null;
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
		for (i = 0; i < greyScaleValues.size(); ++i) {
			greyScaleValues1D.add(core.evaluation.Histogram.get(greyScaleValues.get(i)));
			//Debug.log("Generated Histogram: " + i);
			//print1dArray(greyScaleValues1D.get(i));
		}

		int[] additionOfAll1DArrays = new int[greyScaleValues1D.get(0).length];
		for (int j = 0; j < greyScaleValues1D.size(); ++j) {
			additionOfAll1DArrays = Utils.addIntArrays1D(additionOfAll1DArrays, greyScaleValues1D.get(j));
		}

		//Debug.log("Addition of all 1D arrays: ");
		//for (int k = 0; k < additionOfAll1DArrays.length; ++k) {
		//	Debug.log(additionOfAll1DArrays[k] + " ");
		//}

		//Debug.log("\nAverage of all 1D arrays: ");
		for (int k = 0; k < additionOfAll1DArrays.length; ++k) {
			additionOfAll1DArrays[k] = additionOfAll1DArrays[k] / i;
			//Debug.log(additionOfAll1DArrays[k] + " ");
		}

		return generateNewCanvas(additionOfAll1DArrays);
	}

	/**
	 * Returns Canvas based on set entry.
	 * 
	 * @param entry
	 * @return canvas
	 */
	public static Canvas get(EvaluationDataSetEntry entry) {
		return generateCanvas(entry.getGreyScaleData());
	}

	/**
	 * Calculates the average of all greyScaleValues.
	 * 
	 * @param greyScaleValues an ArrayList of all greyScaleValues 2d arrays
	 * @return greyScaleValuesNORMALIZED average 2d int array
	 */
	@SuppressWarnings("unused")
	private static int[][] normalize(ArrayList<int[][]> greyScaleValues) {
		int[][] greyScaleValuesNORMALIZED = Utils.createNewIntArray2DofSize(greyScaleValues.get(0));

		int i;
		for (i = 0; i < greyScaleValues.size(); ++i) {
			greyScaleValuesNORMALIZED = Utils.addIntArrays2D(greyScaleValuesNORMALIZED, greyScaleValues.get(i));
		}
		//Debug.log("Addition of all greyScaleValues:");
		//Utils.printIntArray2D(greyScaleValuesNORMALIZED);

		//Debug.log("Average greyScaleValues:");
		greyScaleValuesNORMALIZED = Utils.calculateAverageIntArray2D(greyScaleValuesNORMALIZED, i);

		return greyScaleValuesNORMALIZED;
	}

	/**
	 * Generates a histogram of a greyScaleValues 2d array.
	 * 
	 * @param data
	 * @return
	 */
	private static Canvas generateCanvas(int[][] data) {
		int width = H * V;
		int height = H * V;
		Canvas canvas = new Canvas(width, height);

		int[] histogramData = core.evaluation.Histogram.get(data);

		drawHistogram(canvas, histogramData);

		return canvas;
	}

	/**
	 * Generates a histogram of a greyScaleValues 2d array.
	 * 
	 * @param data
	 * @return
	 */
	private static Canvas generateNewCanvas(int[] data) {
		int width = H * V + 100;
		int height = H * V + 100;
		Canvas canvas = new Canvas(width, height);

		drawHistogram(canvas, data);

		return canvas;
	}

	/**
	 * Draws one histogram to canvas using V as a modifier to increase the size.
	 * TODO: ! BUG - (at least) x-range is too low for 256 values!
	 * TODO: Improve code TODO: Implement automatic scaling of the diagram with modifier V
	 * 
	 * @param canvas the canvas to draw the histogram on
	 * @param histogram the histogram (int array)
	 */
	private static void drawHistogram(Canvas canvas, int[] histogram) {
		double width = canvas.getWidth();
		double height = canvas.getHeight();
		int c1 = 0, c2 = 0;
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(Color.GREY);
		graphicsContext2D.fillRect(0, 0, width, height);

		graphicsContext2D.setLineWidth(LINEWIDTH);
		graphicsContext2D.setStroke(Color.BLACK);
		graphicsContext2D.strokeLine(0 + 15, height - 25, width - 15, height - 25); // X axis
		graphicsContext2D.strokeLine(25, 0 + 15, 25, height - 15); // Y axis

		graphicsContext2D.strokeLine(width - 15, height - 25, width - 15 - 5, height - 25 + 5); // X axis arrow 1
		graphicsContext2D.strokeLine(width - 15, height - 25, width - 15 - 5, height - 25 - 5); // X axis arrow 2
		graphicsContext2D.strokeLine(25, 0 + 15, 25 + 5, 25 - 5); // Y axis arrow 1
		graphicsContext2D.strokeLine(25, 0 + 15, 25 - 5, 25 - 5); // Y axis arrow 2

		graphicsContext2D.setLineWidth(LINEWIDTH / 2);
		graphicsContext2D.setStroke(Color.BLACK);
		for (int i = 25; i < width - 35; i += 5 * V) {
			graphicsContext2D.strokeLine(i, height - 25 - 3, i, height - 25 + 3); // X axis divide lines
			if (c1 == 0) {
				graphicsContext2D.strokeText("" + c1, i, height - 5, 15);
			}
			c1 = c1 + 10;
			if (c1 % 50 == 0) {
				graphicsContext2D.strokeText("" + c1, i, height - 5, 15); // X axis numbers
			}
		}
		for (int i = (int)width - 35; i > 25; i -= 5 * V) {
			graphicsContext2D.strokeLine(25 - 3, i, 25 + 3, i); // Y axis divide lines
			c2 = c2 + 10;
			if (c2 % 50 == 0) {
				graphicsContext2D.strokeText("" + c2, 5, i, 15); // Y axis numbers
			}
		}

		// histogram lines
		for (int h = 0; h < histogram.length; ++h) {
			graphicsContext2D.setLineWidth(LINEWIDTH / 2);
			graphicsContext2D.setStroke(Color.DARKBLUE);
			graphicsContext2D.strokeLine(25 + h * V, height - 25, 25 + h * V, height - 25 - histogram[h] * V);
		}
	}
}