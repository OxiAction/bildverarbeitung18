package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import events.*;
import utils.*;

/**
 * View: 
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabNewScan implements ViewInterface {

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
		
		TextArea textArea = new TextArea();
		textArea.setText(Translation.fetch("text_new_scan"));
		textArea.setWrapText(true);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(textArea);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		Tab tab = new Tab(Translation.fetch("new_scan"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		tab.setOnCloseRequest(new EventHandler<Event>() {
		    @Override
		    public void handle(Event e) {
		    	EventManager.dispatch(new EventTabNewScanClosed());
		    }
		});
	}
}