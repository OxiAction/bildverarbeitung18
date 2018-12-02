package core.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.concurrent.Task;
import javafx.util.Pair;
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
			
			ArrayList<Pair<Integer, Double>> pairs = new ArrayList<Pair<Integer, Double>>();

			// compare all others with current entry	
			for (EvaluationDataSetEntry innerEntry : setEntries) {
				if (entry == innerEntry) {
					continue;
				}

				int[] innerHistogramData = innerEntry.getHistogramData();

				// compare histogramData of two entries by metric (which is choosen by metric name)
				double distance = Metric.getDataByName(this.set.getMetricName(), histogramData, innerHistogramData);
				
				Pair<Integer, Double> pair = new Pair<Integer, Double>(innerEntry.getID(), distance);
				pairs.add(pair);
			}
			
			// check if we found one or more pairs
			if (pairs.size() > 0) {
				Collections.sort(pairs, new Comparator<Pair<Integer, Double>>() {
				    @Override
				    public int compare(final Pair<Integer, Double> o1, final Pair<Integer, Double> o2) {
						return Double.compare(o1.getValue(), o2.getValue());
				    }
				});
				
				int kFactor = Integer.parseInt(this.set.getKFactor());
				for (int k = 0; k < pairs.size(); ++k) {
					if (k > kFactor - 1) {
						break;
					}
					
					int id = pairs.get(k).getKey();
					entry.addKNearestByID(id);
					
					for (EvaluationDataSetEntry innerEntry2 : setEntries) {
						if (innerEntry2.getID() == id) {
							entry.addKNearestBySensorType(innerEntry2.getSensorType());
						}
					}
						
				}
			}
		}

		// return the set, filled with all the entries and values:
		return this.set;
	}
}