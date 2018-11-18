package events;

import java.util.ArrayList;

/**
 * Simple event manager (publish / subscribe pattern).
 */
public class EventManager {
	private static ArrayList<EventAbstract> events = new ArrayList<EventAbstract>();
	
	/**
	 * Add new event.
	 * 
	 * @param event
	 */
	public static void add(EventAbstract event) {
		events.add(event);
	}
	
	/**
	 * Remove ALL events by eventName.
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
	 * Remove certain event object.
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
	 * Dispatch event.
	 * 
	 * @param event
	 */
	public static void dispatch(EventAbstract event) {
		dispatch(event, null);
	}
	
	/**
	 * Dispatch event with extraData object.
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
