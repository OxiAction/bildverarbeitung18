package core.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class Histogram {

	/**
	 * Returns Canvas based on data
	 * TODO implement
	 */
	public static Canvas get() {
		double width = 300;
		double height = 300;
		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
		
		graphicsContext2D.setFill(Color.RED);
		graphicsContext2D.fillRect(0, 0, 30, 30);
		
		return canvas;
	}
}