package core.graphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;

import java.util.ArrayList;

/**
 * This class generates a Canvas based on GreyScaleValues from EvaluationDataSet and EvaluationDataSetEntry's
 * TODO: Make average data double?
 * TODO: Make different sizes possible!
 * @author Richard Riediger
 */
public class Histogram {
	private static int V = 3;		// multiplier for histogram

	/**
	 * Main, only for testing!
	 * @param args	no args required
	 */
	public static void main(String[] args) {
		get(new EvaluationDataSet());
	}

	/**
	 * Returns Canvas based on average set data
	 * TODO: Actually use the uncommented code
	 * @param	set
	 * @return	canvas
	 */
	public static Canvas get(EvaluationDataSet set) {
		ArrayList<int[][]> greyScaleValues = new ArrayList<int[][]>();

//		for (EvaluationDataSetEntry entry : set.getEntries()) {
//			greyScaleValues.add(entry.getGreyScaleValues());
//		}
		int[][] data1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 1, 1, 3, 3 } };
		int[][] data2 = { { 2, 2, 4, 4 }, { 6, 6, 8, 8 }, { 2, 4, 6, 8 } };
		int[][] data3 = { { 1, 1, 3, 3 }, { 5, 5, 7, 7 }, { 3, 1, 1, 3 } };
		greyScaleValues.add(data1);
		greyScaleValues.add(data2);
		greyScaleValues.add(data3);

		int[][] greyScaleValuesNORMALIZED = normalize(greyScaleValues); // TODO: unterschiedliche Groessen

		return generateCanvas(greyScaleValuesNORMALIZED);
	}

	/**
	 * Returns Canvas based on average set data
	 * @param	entry
	 * @return	canvas
	 */
	public static Canvas get(EvaluationDataSetEntry entry) {
		return generateCanvas(entry.getGreyScaleValues());
	}

	/**
	 * Calculates the average of all greyScaleValues
	 * @param	greyScaleValues				an ArrayList of all greyScaleValues 2d arrays
	 * @return	greyScaleValuesNORMALIZED	average 2d int array
	 */
	private static int[][] normalize(ArrayList<int[][]> greyScaleValues){
		int[][] greyScaleValuesNORMALIZED = createNewArrayOfSize(greyScaleValues.get(0));

		System.out.println("Addition of all greyScaleValues:");
		int i;
		for(i = 0; i < greyScaleValues.size(); i++){
			greyScaleValuesNORMALIZED = add2DArrays(greyScaleValuesNORMALIZED, greyScaleValues.get(i));
		}
		print2dArray(greyScaleValuesNORMALIZED);

		System.out.println("Average greyScaleValues:");
		greyScaleValuesNORMALIZED = calculateAverage2dArray(greyScaleValuesNORMALIZED, i);

		return greyScaleValuesNORMALIZED;
	}

	/**
	 * creates a new 2d array of the same size as the given one
	 * @param 	arr1
	 * @return	arr2
	 */
	private static int[][] createNewArrayOfSize(int[][] arr1){
		int x = arr1.length;
		int y = arr1[0].length;
		int[][] arr2 = new int[x][y];
		return arr2;
	}

	/**
	 * prints a 2d array to console
	 * @param arr
	 */
	private static void print2dArray(int[][] arr){
		for(int j = 0; j < arr.length; j++){
			for(int k = 0; k < arr[j].length; k++){
				System.out.print(" " + arr[j][k]);
			}
			System.out.println();
		}
	}

	/**
	 * calculates the average values of an array and prints it to console
	 * @param arr1
	 * @param i
	 * @return
	 */
	private static int[][] calculateAverage2dArray(int[][] arr1, int i){
		for(int j = 0; j < arr1.length; j++){
			for(int k = 0; k < arr1[j].length; k++){
				arr1[j][k] = arr1[j][k] / i;
				System.out.print(" " + arr1[j][k]);
			}
			System.out.println();
		}
		return arr1;
	}

	/**
	 * Adds two equally sized 2d arrays to each other
	 * @param 	arr1
	 * @param 	arr2
	 * @return	sum
	 */
	private static int[][] add2DArrays(int[][] arr1, int[][] arr2){
		int[][] sum = new int[arr1.length][arr1[0].length];
		for(int i = 0; i < arr1.length; i++){
			for(int j = 0; j < arr1[i].length; j++){
				arr1[i][j] += arr2[i][j];
			}
		}
		return sum;
	}

	/**
	 * Generates a histogram of a greyScaleValues 2d array
	 * @param data
	 * @return
	 */
	private static Canvas generateCanvas(int[][] data) {
		int width = 300;
		int height = 300;
		Canvas canvas = new Canvas(width, height);

		int[] histogramData = generateHistogram(data);

		drawHistogram(canvas, histogramData);

		return canvas;
	}

	/**
	 * Generates the histograms as arrays and stores them in a new ArrayList
	 * @param	data			ArrayList that consists of all images
	 * @return	int array of size 100 for every greyscale
	 */
	private static int[] generateHistogram(int[][] data){
		HistogramData histogramData = new HistogramData(data);
		histogramData.generate();
		histogramData.printHistogramData();

		return histogramData.getHistogramData();
	}

	/**
	 * Draws one histogram to canvas using V as a modifier to increase the size
	 * @param	canvas		the canvas to draw the histogram on
	 * @param	histogram	the histogram (int array)
	 */
	private static void drawHistogram(Canvas canvas, int[] histogram){
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		graphicsContext2D.setFill(Color.GREY);
		graphicsContext2D.fillRect(0, 0, 300, 300);

		for(int h = 0; h < histogram.length; h++){
			graphicsContext2D.setFill(Color.BLACK);
			graphicsContext2D.fillRect(h*V,0, 1,histogram[h]*V);	// multiply with V for visibility
		}
	}
}