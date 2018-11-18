package views;

/**
 * Every view must implement this.
 */
public interface ViewInterface {
	/**
	 * Initialize the view -> show its ui.
	 * 
	 * @param borderPane
	 * @param extraData		stores extra configuration data (if required)
	 */
	public void init(Object container, Object extraData) throws Exception;
}