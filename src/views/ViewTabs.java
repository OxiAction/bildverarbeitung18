package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import events.*;
import utils.*;
import core.parsers.*;

/**
 * Container for the tabs 
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabs implements ViewInterface {

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
		
		BorderPane borderPane = (BorderPane) container;	
		
		TabPane tabPane = new TabPane();
		
		new ViewTabWelcome().init(tabPane, null);
		
		borderPane.setCenter(tabPane);
		
		// events
		
		EventButtonNewScanClicked eventButtonNewScan = new EventButtonNewScanClicked() {
			@Override
			public void dispatch(Object extraData) {
				try {
					new ViewTabNewScan().init(tabPane, extraData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		EventManager.add(eventButtonNewScan);
		
		EventButtonConfigClicked eventButtonConfig = new EventButtonConfigClicked() {
			@Override
			public void dispatch(Object extraData) {
				try {
					new ViewTabConfig().init(tabPane, extraData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		EventManager.add(eventButtonConfig);
		
		EventButtonStartScanClicked eventButtonStartScanClicked = new EventButtonStartScanClicked() {
			@Override
			public void dispatch(Object extraData) {
				try {
					new ViewTabScanResults().init(tabPane, extraData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		EventManager.add(eventButtonStartScanClicked);
	}
}
