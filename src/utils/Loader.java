package utils;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Loader for JavaFX.
 */
public class Loader {
	protected static Pane pane = null;
	protected static StackPane stackPane = null;
	
	/**
	 * Set the pane, in which the loader will be shown.
	 * 
	 * @param pane
	 */
	public static void setPane(Pane pane) {
		Loader.pane = pane;
		
		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.setMaxHeight(50);
		progressIndicator.setMaxWidth(50);

        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: rgba(160,160,160,0.7)");
        stackPane.getChildren().add(progressIndicator);
	}
	
	/**
	 * Show the loader.
	 */
	public static void show() {
		if (pane == null) {
			return;
		}
		
        pane.getChildren().add(stackPane);
	}
	
	/**
	 * Hide the loader.
	 */
	public static void hide() {
		if (pane == null) {
			return;
		}
		
        pane.getChildren().remove(stackPane);
	}
}