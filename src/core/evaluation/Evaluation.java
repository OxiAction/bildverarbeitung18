package core.evaluation;

import java.util.ArrayList;
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
		
		for (int j = 0; j < paths.size(); j++) {
			String absoluteFilePath = paths.get(j);
			if (absoluteFilePath != null && absoluteFilePath != "") {
				EvaluationThread evaluationThread = new EvaluationThread(j, sourceFolder, absoluteFilePath);
				evaluationThreads.add(evaluationThread);
				evaluationThread.start();
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
		
		
		// return the set, filled with all the entries and values:
		return set;
	}
}