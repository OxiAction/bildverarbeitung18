package core.evaluation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Task;
import utils.Debug;

/**
 * The Evaluation class is responsible for processing every entry in a set.
 */
public class Evaluation extends Task<EvaluationDataSet> {
	protected EvaluationDataSet set;
	
	/**
	 * Task for evaluating a set based on incoming set.
	 * Threads (EvaluationThread) are used to speed up the calculation process. 
	 * After all threads have finished their work, we get the distance of the metric (based on histogram datas) for each entry. 
	 * The k-factor (in the set) determines how many "nearest" (which means "similar distance") entries are stored within an entry.
	 * 
	 * @param set
	 */
	public Evaluation(EvaluationDataSet set) {
		this.set = set;
	}
	
	@Override
	protected EvaluationDataSet call() throws Exception {
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(this.set.toString());

		String sourceFolder = this.set.getSourceFolder();

		ArrayList<String> paths = PathFinder.getPaths(sourceFolder);

		ArrayList<EvaluationThread> evaluationThreads = new ArrayList<EvaluationThread>();

		int idCounter = 1;
		for (int j = 0; j < paths.size(); j++) {
			String absoluteFilePath = paths.get(j);
			if (absoluteFilePath != null && absoluteFilePath != "") {
				EvaluationThread evaluationThread = new EvaluationThread(idCounter, absoluteFilePath, this.set);
				evaluationThreads.add(evaluationThread);
				evaluationThread.start();

				idCounter++;
			}
		}

		for (EvaluationThread evaluationThread : evaluationThreads) {
			try {
				evaluationThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// leave one out cross validation:
		// - compare each entry with all other entries (by metric) and set k-nearest entries ids in entry
		List<EvaluationDataSetEntry> setEntries = this.set.getEntries();
		for (EvaluationDataSetEntry entry : setEntries) {
			int[] histogramData = entry.getHistogramData();

			// sanity check
			if (histogramData == null) {
				continue;
			}

			int kFactor = Integer.parseInt(this.set.getKFactor());
			int count = 0;

			// compare all others with current entry	
			for (EvaluationDataSetEntry innerEntry : setEntries) {
				if (entry == innerEntry) {
					continue;
				}
				
				Hashtable<EvaluationDataSetEntry, Double> kNearest = new Hashtable<EvaluationDataSetEntry, Double>();

				int[] innerHistogramData = innerEntry.getHistogramData();

				// compare histogramData of two entries by metric (which is choosen by metric name)
				double distance = Metric.getDataByName(this.set.getMetricName(), histogramData, innerHistogramData);

				// adds a distance to the k-nearest hashtable:
				// - first we check if we already have k-elements
				// - when there are already k-elements in the hashtable, we replace the largest with the new one (if it is smaller)
				if (count < kFactor) {
					kNearest.put(innerEntry, distance);
					count++;
				} else {
					// finding the largest element in the hashtable
					Object maxKey = null;
					Double maxValue = Double.MIN_VALUE;
					for (Map.Entry<EvaluationDataSetEntry, Double> mapEntry : kNearest.entrySet()) {
						if (mapEntry.getValue() > maxValue) {
							maxValue = mapEntry.getValue();
							maxKey = mapEntry.getKey();
						}
					}
					// replacing the largest element with the actual one (if it is smaller)
					if (distance < maxValue) {
						if (maxKey != null) {
							kNearest.remove(maxKey);
						}
						kNearest.put(innerEntry, distance);
					}
				}

				// add id as a kNearest to entry
				for (Map.Entry<EvaluationDataSetEntry, Double> mapEntry : kNearest.entrySet()) {
					entry.addKNearestByID(mapEntry.getKey().getID());
				}
			}
			
			entry.setEntropy(Entropy.get(entry.getGreyScaleData(), entry.getHistogramData()));	
		}

		// return the set, filled with all the entries and values:
		return this.set;
	}
}