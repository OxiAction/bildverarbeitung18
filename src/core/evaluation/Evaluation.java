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
		// get config values:
		String imagePath = set.getImagePath();
		String sourceFolder = set.getSourceFolder();
		int kFactor = Integer.parseInt(set.getKFactor());
		
		Debug.log("-> EvaluationDataSet values:");
		Debug.log(set.toString());
		
		EvaluationDataSetEntry entry;
		
		ImageReader ir = new ImageReader(imagePath);
		Pathfinder pf = new Pathfinder(sourceFolder);
		
		// update source entry
		EvaluationDataSetEntry sourceEntry = set.getSourceEntry();
		sourceEntry.setFileFolderPath(ir.getImagepath());
		sourceEntry.setFileName(ir.getImagename());
		sourceEntry.setFileExtension(ir.getImageextension());
		sourceEntry.setGreyScaleValues(ir.convertTo2DArray());
		
		
		//adds k-many images from the database to the dataset
		ArrayList<String> paths = pf.getk_paths(kFactor);
		for(int i=0; i<kFactor;i++) {
			if (i >= paths.size()) {
				break;
			}
			ImageReader temp = new ImageReader(paths.get(i));
			entry = new EvaluationDataSetEntry(temp.getImagepath(), temp.getImagename(), temp.getImageextension(), temp.convertTo2DArray());
			set.addEntry(entry);
		}
		
		// return the set, filled with all the entries and values:
		return set;
	}
}