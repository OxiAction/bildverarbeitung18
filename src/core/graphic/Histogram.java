package core.graphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class Histogram {

	/**
	 * Returns Canvas based on set data
	 * TODO implement
	 * 
	 * @param set
	 * @return
	 */
	public static Canvas get(EvaluationDataSet set) {
		double width = 300;
		double height = 300;
		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		
		graphicsContext2D.setFill(Color.RED);
		graphicsContext2D.fillRect(0, 0, 30, 30);
		
		// iterate over set entries...
		for (EvaluationDataSetEntry setEntry : set.getEntries()) {
			System.out.println(setEntry);
		}
		
		return canvas;
	}
}