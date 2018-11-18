package views;

import javafx.scene.layout.*;

/**
 * Setup main regions for the views
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
	}
}
