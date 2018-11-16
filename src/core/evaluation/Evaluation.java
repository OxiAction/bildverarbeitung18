package core.evaluation;

import java.util.ArrayList;
import java.util.List;

import utils.Debug;
import java.io.IOException;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class Evaluation {
	
	/**
	 * Returns evaluated data based on incoming set
	 * 
	 * @throws IOException 
	 */
	public static EvaluationDataSet get(EvaluationDataSet set) throws IOException {
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(set.toString());
		
		String sourceFolder = set.getSourceFolder();
		Debug.log("Set -> sourceFolder: " + sourceFolder);
		
		
		ArrayList<String> paths = PathFinder.getPaths(sourceFolder);
		
		ArrayList<EvaluationThread> evaluationThreads = new ArrayList<EvaluationThread>();
		
		int idCounter = 1;
		for (int j = 0; j < paths.size(); j++) {
			String absoluteFilePath = paths.get(j);
			if (absoluteFilePath != null && absoluteFilePath != "") {
				EvaluationThread evaluationThread = new EvaluationThread(idCounter, absoluteFilePath, set);
				evaluationThreads.add(evaluationThread);
				evaluationThread.start();
				
				idCounter++;
			}
		}
		
		for (EvaluationThread evaluationThread : evaluationThreads) {
			try {
				evaluationThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (EvaluationThread evaluationThread : evaluationThreads) {
			set.addEntry(evaluationThread.getEntry());
		}
		
		// TODO [Markus] leave one out cross validation: compare each entry with all other entries (by metric) and set k-nearest entries ids in entry
		List<EvaluationDataSetEntry> setEntries = set.getEntries();
		for (EvaluationDataSetEntry entry : setEntries) {
			int[] histogramData = entry.getHistogramData();
			int id = entry.getID();
			
			// INNER FOR LOOP (compare all others with current entry):
			
				// ...
				
				// compare histogramData of two entries (set.getMetricName() is the name which was choosen in the new scan metric name dropdown):
				//Metric.getDataByName(set.getMetricName(), histogramData, histogramData_OF_ENTRY_IN_INNER_FOR_LOOP);
				
				// add id as a kNearest to (OUTER FOR LOOP) entry (in case it is actually k-nearest):
				//entry.addKNearestByID(id_OF_ENTRY_IN_INNER_FOR_LOOP);
			
			// END INNER FOR LOOP
		}
		
		// return the set, filled with all the entries and values:
		return set;
	}
}