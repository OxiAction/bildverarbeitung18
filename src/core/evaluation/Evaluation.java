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
	 * After all threads have finished their work, we get the distance of the metric (based on histogram data) for each entry. 
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

		// get all valid image (absolute) paths from the source folder
		ArrayList<String> paths = PathFinder.getPaths(this.set.getSourceFolder());
		// list which contains all evaluation threads
		ArrayList<EvaluationThread> evaluationThreads = new ArrayList<EvaluationThread>();

		// the threads unique id
		int idCounter = 1;
		// setup and start threads
		for (String absoluteFilePath : paths) {
			if (absoluteFilePath != "") {
				EvaluationThread evaluationThread = new EvaluationThread(idCounter, absoluteFilePath, this.set);
				evaluationThreads.add(evaluationThread);
				evaluationThread.start();

				idCounter++;
			}
		}

		// wait for all threads to die, before we continue with the code
		for (EvaluationThread evaluationThread : evaluationThreads) {
			try {
				evaluationThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// leave one out cross validation:
		// compare each entry with all other entries (by metric) and set k-nearest entries ids in entry

		int i;
		int kFactor = Integer.parseInt(this.set.getKFactor());
		String metricName = this.set.getMetricName();

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
				// sanity check
				if (innerHistogramData == null) {
					continue;
				}

				// compare histogramData of two entries by metric (which is chosen by metric name)
				double distance = Metric.getDataByName(metricName, histogramData, innerHistogramData);

				// store innerEntry id together with its distance (required for k-nearest stuff)
				Pair<Integer, Double> pair = new Pair<Integer, Double>(innerEntry.getID(), distance);
				pairs.add(pair);
			}

			// check if we found one or more pairs
			if (pairs.size() > 0) {
				if (metricName == Metric.NAME_CORRELATION || metricName == Metric.NAME_INTERSECTION) {
					// sort the pairs by distance (descending)
					// pairs with HIGHER distance come first (when iterating from 0 to n)
					Collections.sort(pairs, new Comparator<Pair<Integer, Double>>() {
						@Override
						public int compare(final Pair<Integer, Double> o1, final Pair<Integer, Double> o2) {
							return Double.compare(o2.getValue(), o1.getValue());
						}
					});
				} else {
					// sort the pairs by distance (ascending)
					// pairs with LOWER distance come first (when iterating from 0 to n)
					Collections.sort(pairs, new Comparator<Pair<Integer, Double>>() {
						@Override
						public int compare(final Pair<Integer, Double> o1, final Pair<Integer, Double> o2) {
							return Double.compare(o1.getValue(), o2.getValue());
						}
					});
				}
				
				// list of collected sensor types
				ArrayList<String> kNearestSensorTypes = new ArrayList<String>();
				
				for (i = 0; i < pairs.size(); ++i) {
					// make sure we do not exceed the specified k-factor
					if (i > kFactor - 1) {
						break;
					}
					
					int id = pairs.get(i).getKey();

					// k-nearest id
					entry.addKNearestByID(id);

					// k-nearest sensor type
					for (EvaluationDataSetEntry innerEntry2 : setEntries) {
						if (innerEntry2.getID() == id) {
							// we found a sensor type - add it to collection
							kNearestSensorTypes.add(innerEntry2.getSensorType());
						}
					}
				}

				// set the collected sensor types
				// note: this also sets the nearestSensorType (most common sensor type)
				entry.setKNearestSensorTypes(kNearestSensorTypes);
			}
		}

		// return the set, filled with all the entries and values:
		return this.set;
	}
}