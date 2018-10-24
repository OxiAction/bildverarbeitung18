package views;

import javafx.event.*;
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
public class ViewMain implements ViewInterface {

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
		
		// top
		new ViewToolbar().init(container, extraData);
		
		// center
		new ViewTabs().init(container, extraData);
		
		// bottom
		new ViewProgressBar().init(container, extraData);
	}
}
