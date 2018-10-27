package core.graphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;

import java.util.ArrayList;

/**
 * TODO description
 * @author Richard Riediger
 */
public class Histogram {
	private static int V = 3;		// multiplier for histogram

	public static void main(String[] args) {
		get(null);
	}

	/**
	 * Returns Canvas based on set data
	 * TODO implement
	 * @param set
	 * @return
	 */
	public static Canvas get(EvaluationDataSet data) {
		int width = 300;
		int height = 300;
		// test image greyscale datas
		int[][] data1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 1, 1, 3, 3 } };
		int[][] data2 = { { 2, 2, 4, 4 }, { 6, 6, 8, 8 }, { 2, 4, 6, 8 } };
		int[][] data3 = { { 1, 1, 3, 3 }, { 5, 5, 7, 7 }, { 3, 1, 1, 3 } };

		ArrayList<int[][]> allData = new ArrayList<int[][]>();
		allData.add(data1);
		allData.add(data2);
		allData.add(data3);

		ArrayList<int[]> allHistograms = generateHistograms(allData);
		printAllHistograms(allHistograms);

		Canvas canvas = new Canvas(width, height);
		drawHistogram(canvas, allHistograms.get(0));

		// iterate over data entries...
//		for (EvaluationDataSetEntry setEntry : set.getEntries()) {
//			System.out.println(setEntry);
//		}
		return canvas;
	}

	/*
	 * Generates the histograms as arrays and stores them in a new ArrayList
	 * @param	allData			ArrayList that consists of all images
	 * @return	allHistograms	ArrayList that consists of all histograms (int arrays)
	 */
	private static ArrayList<int[]> generateHistograms(ArrayList<int[][]> allData){
		ArrayList<int[]> allHistograms = new ArrayList<int[]>();
		for(int i = 0; i < allData.size(); i++){
			HistogramData histogramData = new HistogramData(allData.get(i));
			histogramData.generate();
			allHistograms.add(histogramData.getHistogramData());
		}
		return allHistograms;
	}

	/*
	 * Draws one histogram to canvas using V as the modifier to increase the size
	 * @param	Canvas		the canvas to draw the histogram on
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

	/*
	 * Prints all histograms to console
	 * @param	allHistograms	ArrayList that consists of all histograms (int arrays)
	 */
	private static void printAllHistograms(ArrayList<int[]> allHistograms){
		System.out.println("Printing all histograms...");
		for(int i = 0; i < allHistograms.size(); i++){
			System.out.println("Histogram 1: ");
			for(int j = 0; j < allHistograms.get(i).length; j++){
				System.out.print(allHistograms.get(i)[j]);
			}
			System.out.println();
		}
	}
}