package core.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import utils.Debug;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		// get set:
		String name = set.getName();
		String imagePath = set.getImagePath();
		String sourceFolder = set.getSourceFolder();
		int kFactor = Integer.parseInt(set.getKFactor());
		//String heuristic = set.getHeuristic();
		
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(set.toString());
		
		ImageReader ir = new ImageReader(imagePath);
		Pathfinder pf = new Pathfinder(sourceFolder);
		
		ArrayList<String> paths = pf.getk_paths(kFactor);
		EvaluationDataSetEntry entry;
		
		//adds the selected image to the dataset
		entry = new EvaluationDataSetEntry(ir.getImagepath(),ir.getImagename(),ir.getImageextension(), ir.convertTo2DArray());
		set.addEntry(entry);
		
		
		//adds k-many images from the database to the dataset
		for(int i=0; i<kFactor;i++) {
			ImageReader temp = new ImageReader(paths.get(i));
			entry = new EvaluationDataSetEntry(paths.get(i), pf.getFilenames().get(i), pf.getExtensions().get(i), temp.convertTo2DArray());
			set.addEntry(entry);
		}
		
		// return the set, filled with all the entries and values:
		return set;
	}
	
	
	
	
}