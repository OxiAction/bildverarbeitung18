package core.evaluation;

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
	public static EvaluationDataSet get(HashMap<?, ?> data) {
		String imagePath = (String) data.get("image_path");
		String sourceFolder = (String) data.get("source_folder");
		String kFactor = (String) data.get("k_factor");
		String heuristic = (String) data.get("heuristic");
		
		Debug.log("-> scan settings:");
		Debug.hashMap(data);
		
		EvaluationDataSet results = new EvaluationDataSet("test", imagePath, sourceFolder, kFactor, heuristic);
		
		EvaluationDataSetEntry entry1 = new EvaluationDataSetEntry("fileFolderPath", "fileName", "fileExtension", new int[][] {{8, 8, 8, 10, 10}, {8, 9, 9, 9, 9}});
		
		results.addEntry(entry1);
		
		return results;
	}
}