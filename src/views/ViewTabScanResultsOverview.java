package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import events.*;
import utils.*;
import core.data.*;
import core.evaluation.*;

/**
 * Tab content for the "scan results overview" section of the App.
 */
public class ViewTabScanResultsOverview implements ViewInterface {

	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;

	/**
	 * Initialize / show components.
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof TabPane)) {
			throw new Exception("container doesnt seem to be of type TabPane!");
		}
		
		List<Text> texts = new ArrayList<Text>();
		
		TabPane tabPane = (TabPane) container;	
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
	// box
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
	// intro text
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_scan_results_overview"));
		vBox.getChildren().add(text);
		// collect all texts
		texts.add(text);
		
	// accordion
		
		Accordion accordion = new Accordion ();
		// get data
		ArrayList<EvaluationDataSet> sets = Data.load();
		for (EvaluationDataSet set : sets) {
			Debug.log("-> read sets from data:\n" + set.toString() + "\n");
			
		// box
			
			VBox  vBoxTitledPane = new VBox();
			vBoxTitledPane.setSpacing(10);
			vBoxTitledPane.setPadding(new Insets(10, 10, 10, 10));
			
		// intro text (details about the scan settings)
			
			Text textTitledPane = new Text();
			textTitledPane.setWrappingWidth(300);
			textTitledPane.setText(set.toString());
			vBoxTitledPane.getChildren().add(textTitledPane);
			// collect all texts
			texts.add(textTitledPane);
			
		// show scan results
			
			Button buttonShowScanResult = new Button(Translation.fetch("button_show_scan_result"));
			vBoxTitledPane.getChildren().add(buttonShowScanResult);
			
			buttonShowScanResult.setOnAction(
					new EventHandler<ActionEvent>() {
					    @Override
					    public void handle(final ActionEvent e) {
					    	set.save = false;
					    	EventManager.dispatch(new EventButtonStartScanClicked(), set);
					    }
					});
			
		// titled pane
			
			TitledPane titledPane = new TitledPane(set.getName() + " " + set.getTimestamp().toString(), vBoxTitledPane);
			
			accordion.getPanes().add(titledPane);
		}
		vBox.getChildren().add(accordion);
		
	// setup
		
		scrollPane.setContent(vBox);
		
		Tab tab = new Tab(Translation.fetch("scan_results_overview"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		tab.setOnCloseRequest(new EventHandler<Event>() {
		    @Override
		    public void handle(Event e) {
		    	EventManager.dispatch(new EventTabScanResultsOverviewClosed());
		    }
		});
		
		// @TODO just a quick fix for resizing Text Nodes...
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				for (Text text : texts) {
					text.setWrappingWidth(newBounds.getWidth() - 25);
				}
			}  
		});
	}
}
