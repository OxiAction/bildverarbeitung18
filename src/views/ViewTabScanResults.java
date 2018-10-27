package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import events.*;
import utils.*;
import core.data.*;
import core.evaluation.*;
import core.graphic.*;

/**
 * Tab content for the "scan results" section of the App
 * 
 * @author 
 *
 */
public class ViewTabScanResults implements ViewInterface {

	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;

	/**
	 * initialize / show components
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof TabPane)) {
			throw new Exception("container doesnt seem to be of type TabPane!");
		}
		
		TabPane tabPane = (TabPane) container;	
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
	// TODO implement -> evaluate data
		
		EvaluationDataSet data;
		
		// verify data
		if (!(extraData instanceof EvaluationDataSet)) {
			throw new Exception("extraData doesnt seem to be of type EvaluationDataSet!");
		} else {
			data = (EvaluationDataSet) extraData;
		}
		
		// globally fire an event to let everybody know that we ENTER "loading" state
		EventManager.dispatch(new EventLoadingStarted(), null);
		
		// evaluation
		data = Evaluation.get(data);
		
		// histogram
		Canvas histogram = Histogram.get(data);
		vBox.getChildren().add(histogram);
		
		// store data
		Data.save(data);
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText("Test");
		vBox.getChildren().add(text);
		
		// TODO use Histogram.draw(...) to draw a Canvas
		
		// globally fire an event to let everybody know that we LEAVE "loading" state
		EventManager.dispatch(new EventLoadingFinished(), null);
		
	// setup
		
		scrollPane.setContent(vBox);
		
		Tab tab = new Tab(Translation.fetch("scan_results"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}  
		});
	}
}
