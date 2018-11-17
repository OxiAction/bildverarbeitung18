package core.evaluation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import utils.Debug;
import java.io.IOException;

/**
 * The Evaluation class is responsible for processing every entry in a set.
 */
public class Evaluation {

	/**
	 * Returns evaluated set based on incoming set. Threads (EvaluationThread) are used to speed up the calculation process (e.g. for grey scale /
	 * histogram data gathering). After all threads have finished their work, we get the distance of the metric (based on histogram datas) for each
	 * entry. The k-factor determines how many "nearest" (which means "similar distance") entries are stored within an entry.
	 * 
	 * @param set the set to be processed
	 * @return the processed set
	 * @throws IOException
	 */
	public static EvaluationDataSet get(EvaluationDataSet set) throws IOException {
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(set.toString());

		String sourceFolder = set.getSourceFolder();

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
				e.printStackTrace();
			}
		}

		// leave one out cross validation:
		// - compare each entry with all other entries (by metric) and set k-nearest entries ids in entry
		List<EvaluationDataSetEntry> setEntries = set.getEntries();
		for (EvaluationDataSetEntry entry : setEntries) {
			int[] histogramData = entry.getHistogramData();

			// sanity check
			if (histogramData == null) {
				continue;
			}

			int kFactor = Integer.parseInt(set.getKFactor());
			Hashtable<EvaluationDataSetEntry, Double> kNearest = new Hashtable<EvaluationDataSetEntry, Double>();
			int count = 0;

			// compare all others with current entry	
			for (EvaluationDataSetEntry innerEntry : setEntries) {
				if (entry == innerEntry) {
					continue;
				}

				int[] innerHistogramData = innerEntry.getHistogramData();

				// compare histogramData of two entries by metric (which is choosen by metric name)
				double distance = Metric.getDataByName(set.getMetricName(), histogramData, innerHistogramData);

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
						kNearest.remove(maxKey);
						kNearest.put(innerEntry, distance);
					}
				}

				// add id as a kNearest to entry
				for (Map.Entry<EvaluationDataSetEntry, Double> mapEntry : kNearest.entrySet()) {
					entry.addKNearestByID(mapEntry.getKey().getID());
				}
			}
			
			entry.setEntropyData(ImageReader.getEntropyData(entry.getGreyScaleData(), entry.getHistogramData()));	
		}

		// return the set, filled with all the entries and values:
		return set;
	}
}