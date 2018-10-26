package core.evaluations;

import java.util.HashMap;

import utils.Debug;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class Evaluation {
	
	/**
	 * Returns evaluated data based on incoming image data
	 * TODO implement
	 */
	public static String get(HashMap<?, ?> data) {
		String imagePath = (String) data.get("image_path");
		String sourceFolder = (String) data.get("source_folder");
		String kFactor = (String) data.get("k_factor");
		String heuristic = (String) data.get("heuristic");
		
		Debug.log("-> scan settings:");
		Debug.hashMap(data);
		
		return "RESULTS...";
	}
}