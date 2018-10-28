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
	public static EvaluationDataSet get(EvaluationDataSet data) {
		// get data:
		String name = data.getName();
		String imagePath = data.getImagePath();
		String sourceFolder = data.getSourceFolder();
		String kFactor = data.getKFactor();
		String heuristic = data.getHeuristic();
		
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(data.toString());
		
		
		// create entries:
		EvaluationDataSetEntry entry1 = new EvaluationDataSetEntry("fileFolderPath", "fileName", "fileExtension", new int[][] {{8, 8, 8, 10, 10}, {8, 9, 9, 9, 9}});
		// you can either use the constructor to pass the entry arguments,
		// or use the implemented setter methods e.g.:
		// entry1.setGreyScaleValue(...);
		
		// set entries:
		//data.addEntry(entry1);
		
		// return the set, filled with all the entries and values:
		return data;
	}
}