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
	 * Returns evaluated data based on incoming image data
	 * TODO implement
	 * @throws IOException 
	 */
	public static EvaluationDataSet get(EvaluationDataSet data) throws IOException {
		// get data:
		String name = data.getName();
		String imagePath = data.getImagePath();
		String sourceFolder = data.getSourceFolder();
		int kFactor = Integer.parseInt(data.getKFactor());
		int heuristic = Integer.parseInt(data.getHeuristic());
		
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(data.toString());
		
		ImageReader ir = new ImageReader(imagePath);
		Pathfinder pf = new Pathfinder(sourceFolder);
		
		ArrayList<String> paths = pf.getk_paths(kFactor);
		EvaluationDataSetEntry entry;
		
		//adds the selected image to the dataset
		entry = new EvaluationDataSetEntry(ir.getImagepath(),ir.getImagename(),ir.getImageextension(), ir.convertTo2DArray());
		data.addEntry(entry);
		
		
		//adds k-many images from the database to the dataset
		for(int i=0; i<kFactor;i++) {
			ImageReader temp = new ImageReader(paths.get(i));
			entry = new EvaluationDataSetEntry(paths.get(i), pf.getFilenames().get(i), pf.getExtensions().get(i), temp.convertTo2DArray());
			data.addEntry(entry);
		}
		// you can either use the constructor to pass the entry arguments,
		// or use the implemented setter methods e.g.:
		// entry1.setGreyScaleValue(...);
		

		
		// return the set, filled with all the entries and values:
		return data;
	}
	
	
	
	
}