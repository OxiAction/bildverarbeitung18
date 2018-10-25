package core;

import java.util.HashMap;

import utils.Debug;

/**
 * TODO implement
 * 
 * @author 
 *
 */
public class ImageEvaluation {
	
	public static String getResults(HashMap<?, ?> data) {
		String imagePath = (String) data.get("image_path");
		String sourceFolder = (String) data.get("source_folder");
		String kFactor = (String) data.get("k_factor");
		String heuristic = (String) data.get("heuristic");
		
		Debug.log("-> scan settings:");
		Debug.hashMap(data);
		
		return "RESULTS...";
	}
}