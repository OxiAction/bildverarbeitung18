package events;

/**
 * abstract event object
 * 
 * @author Michael Schreiber
 *
 */
public class EventAbstract {
	private String name = "DEFAULT";
	
	/**
	 * dispatch with extraData object
	 * override this method to implement your dispatch logic!
	 * 
	 * @param extraData
	 */
	public void dispatch(Object extraData) {
		// override me
	}
	
	/**
	 * set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get name
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
