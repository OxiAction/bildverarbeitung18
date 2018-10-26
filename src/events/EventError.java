package events;

/**
 * 
 * @author Michael Schreiber
 *
 */
public class EventError extends EventAbstract {
	public static final String ERROR_XXX = "XXX.";
	
	public EventError() {
		this.setName("ERROR");
	}
}
