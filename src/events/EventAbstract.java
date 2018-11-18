package events;

/**
 * Abstract event object.
 */
public class EventAbstract {
	private String name = "DEFAULT";
	
	/**
	 * Dispatch with extraData object.
	 * Override this method to implement your dispatch logic!
	 * 
	 * @param extraData
	 */
	public void dispatch(Object extraData) {
		// override me
	}
	
	/**
	 * Set name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get name.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
