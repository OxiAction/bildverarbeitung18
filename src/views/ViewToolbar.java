package views;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import events.*;
import utils.*;

/**
 * Toolbar with different button.
 */
public class ViewToolbar implements ViewInterface {

	/**
	 * Initialize / show components.
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof BorderPane)) {
			throw new Exception("container doesnt seem to be of type BorderPane!");
		}
		
	// new scan
		
		Button buttonNewScan = new Button(Translation.fetch("new_scan"));
		buttonNewScan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Debug.log("-> new scan");
				
				EventManager.dispatch(new EventButtonNewScanClicked());
			}
		});
		
		EventButtonNewScanClicked eventButtonNewScan = new EventButtonNewScanClicked() {
			@Override
			public void dispatch(Object extraData) {
				buttonNewScan.setDisable(true);
			}
		};
		EventManager.add(eventButtonNewScan);
		
		EventTabNewScanClosed eventTabNewScanClosed = new EventTabNewScanClosed() {
			@Override
			public void dispatch(Object extraData) {
				buttonNewScan.setDisable(false);
			}
		};
		EventManager.add(eventTabNewScanClosed);
		
	// scan results overview
		
		Button buttonScanResultsOverview = new Button(Translation.fetch("scan_results_overview"));
		buttonScanResultsOverview.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Debug.log("-> scan results overview");
				
				EventManager.dispatch(new EventButtonScanResultsOverviewClicked());
			}
		});
		
		EventButtonScanResultsOverviewClicked eventButtonScanResultsOverview = new EventButtonScanResultsOverviewClicked() {
			@Override
			public void dispatch(Object extraData) {
				buttonScanResultsOverview.setDisable(true);
			}
		};
		EventManager.add(eventButtonScanResultsOverview);
		
		EventTabScanResultsOverviewClosed eventTabScanResultsOverviewClosed = new EventTabScanResultsOverviewClosed() {
			@Override
			public void dispatch(Object extraData) {
				buttonScanResultsOverview.setDisable(false);
			}
		};
		EventManager.add(eventTabScanResultsOverviewClosed);
		
	// config
		
		Button buttonConfig = new Button(Translation.fetch("config"));
		buttonConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Debug.log("-> config");
				
				EventManager.dispatch(new EventButtonConfigClicked());
			}
		});
		
		EventButtonConfigClicked eventButtonConfig = new EventButtonConfigClicked() {
			@Override
			public void dispatch(Object extraData) {
				buttonConfig.setDisable(true);
			}
		};
		EventManager.add(eventButtonConfig);
		
		EventTabConfigClosed eventTabConfigClosed = new EventTabConfigClosed() {
			@Override
			public void dispatch(Object extraData) {
				buttonConfig.setDisable(false);
			}
		};
		EventManager.add(eventTabConfigClosed);
		
	// setup
	
		BorderPane borderPane = (BorderPane) container;
		ToolBar toolBar = new ToolBar(buttonNewScan, buttonScanResultsOverview, buttonConfig);
		borderPane.setTop(toolBar);
	}
}
