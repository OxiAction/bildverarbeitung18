package views;

import javafx.scene.layout.BorderPane;

/**
 * View interface - every View must implement this!
 * 
 * @author Michael Schreiber
 *
 */
public interface ViewInterface {
	/**
	 * initialize the view -> show its ui
	 * 
	 * @param borderPane
	 * @param extraData		stores extra configuration data (if required)
	 */
	public void init(Object container, Object extraData) throws Exception;
}