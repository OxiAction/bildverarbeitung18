package utils;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Loader for JavaFX.
 *
 * @author Michael Schreiber
 *
 */
public class Loader {
	protected static Pane pane = null;
	protected static StackPane stackPane = null;
	
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
	
	public static void show() {
		if (pane == null) {
			return;
		}
		
        pane.getChildren().add(stackPane);
	}
	
	public static void hide() {
		if (pane == null) {
			return;
		}
		
        pane.getChildren().remove(stackPane);
	}
}