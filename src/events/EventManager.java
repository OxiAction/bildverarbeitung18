package events;

import java.util.ArrayList;

/**
 * simple event manager (observer) :)
 * 
 * @author Michael Schreiber
 *
 */
public class EventManager {
	private static ArrayList<EventAbstract> events = new ArrayList<EventAbstract>();
	
	/**
	 * add new event
	 * 
	 * @param event
	 */
	public static void add(EventAbstract event) {
		events.add(event);
	}
	
	/**
	 * remove ALL events by eventName
	 * 
	 * @param eventName
	 */
	public static void remove(String eventName) {
		for (int i = 0; i < events.size(); ++i) {
			EventAbstract currEvent = events.get(i);
			if (currEvent.getName() == eventName) {
				events.remove(currEvent);
				remove(eventName);
			}
		}
	}
	
	/**
	 * remove certain event object 
	 * 
	 * @param event
	 */
	public static void remove(EventAbstract event) {
		for (int i = 0; i < events.size(); ++i) {
			EventAbstract currEvent = events.get(i);
			if (currEvent.equals(event)) {
				events.remove(currEvent);
			}
		}
	}
	
	/**
	 * dispatch event
	 * 
	 * @param event
	 */
	public static void dispatch(EventAbstract event) {
		dispatch(event, null);
	}
	
	/**
	 * dispatch event with extraData object
	 * 
	 * @param event
	 * @param extraData
	 */
	public static void dispatch(EventAbstract event, Object extraData) {
		for (int i = 0; i < events.size(); ++i) {
			EventAbstract currEvent = events.get(i);
			if (currEvent.getName() == event.getName()) {
				currEvent.dispatch(extraData);
			}
		}
	}
}
