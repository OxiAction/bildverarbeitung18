package core.evaluation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
			int kFactor = Integer.parseInt(set.getKFactor());
			Hashtable<EvaluationDataSetEntry, Double> kNearest = new Hashtable<EvaluationDataSetEntry, Double>();
			int count = 0;
			// INNER FOR LOOP (compare all others with current entry):			
			for (EvaluationDataSetEntry innerEntry : setEntries) {
				if(entry == innerEntry)
					continue;
				
				int[] innerHistogramData = innerEntry.getHistogramData();
				
			
				// compare histogramData of two entries (set.getMetricName() is the name which was choosen in the new scan metric name dropdown):
				//Metric.getDataByName(set.getMetricName(), histogramData, histogramData_OF_ENTRY_IN_INNER_FOR_LOOP);
				double distance = Metric.getDataByName(set.getMetricName(), histogramData, innerHistogramData);
				
				
				//adds a distance to the k-nearest hashtable:
				//first we check if we already have k-elements
				//when there are already k-elements in the hashtable, we replace the largest with the new one (if it is smaller)
				if(count < kFactor) {
					kNearest.put(innerEntry, distance);
					count++;
				}else {
					//finding the largest element in the hashtable
					Object maxKey=null;
					Double maxValue = Double.MIN_VALUE; 
					for(Map.Entry<EvaluationDataSetEntry,Double> mapEntry : kNearest.entrySet()) {
					     if(mapEntry.getValue() > maxValue) {
					         maxValue = mapEntry.getValue();
					         maxKey = mapEntry.getKey();
					     }  
					}
					//replacing the largest element with the actual one (if it is smaller)
					if(distance < maxValue) {
						kNearest.remove(maxKey);
						kNearest.put(innerEntry, distance);
					}
				}
				
				
				// add id as a kNearest to (OUTER FOR LOOP) entry (in case it is actually k-nearest):
				//entry.addKNearestByID(id_OF_ENTRY_IN_INNER_FOR_LOOP);
				for(Map.Entry<EvaluationDataSetEntry,Double> mapEntry : kNearest.entrySet())
					entry.addKNearestByID(mapEntry.getKey().getID());
				
			// END INNER FOR LOOP

			}
			
		}
		
		// return the set, filled with all the entries and values:
		return set;
	}
}