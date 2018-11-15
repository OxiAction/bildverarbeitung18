package views;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import utils.*;

/**
 * Progress bar view (loading state)
 * 
 * @author Michael Schreiber
 *
 */
public class ViewProgressBar implements ViewInterface {

	/**
	 * initialize / show components
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof BorderPane)) {
			throw new Exception("container doesnt seem to be of type BorderPane!");
		}
		
		// setup
		
		BorderPane borderPane = (BorderPane) container;
		
		FlowPane flowpane = new FlowPane();
		flowpane.setOrientation(Orientation.HORIZONTAL);
		flowpane.setHgap(10);
		
		Label label = new Label();
		label.setText(Translation.fetch("loading") + ":");
		label.setPadding(new Insets(5, 5, 5, 5));
		flowpane.getChildren().add(label);
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setProgress(1.00F);
		flowpane.getChildren().add(progressBar);
		
		borderPane.setBottom(flowpane);
	}
}
