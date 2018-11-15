package core.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import utils.Debug;
import utils.Utils;

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
		
		for(int i = 0; i < paths.size(); i++) {
			String absoluteFilePath = paths.get(i);
			if (absoluteFilePath != null && absoluteFilePath != "") {
				HashMap<String, String> infos = Utils.getAbsoluteFilePathInfos(absoluteFilePath);
				String fileName = infos.get("fileName");
				String fileFolderPath = infos.get("fileFolderPath");
				String fileExtension = infos.get("fileExtension");
				String sensorType = null;
				
				Debug.log("Entry -> fileName: " + fileName);
				Debug.log("Entry -> fileFolderPath: " + fileFolderPath);
				Debug.log("Entry -> fileExtension: " + fileExtension);
				
				// get sensor type by folder structure...
				String[] partAfterSourceFolder = fileFolderPath.split(sourceFolder);
				if (partAfterSourceFolder.length > 0) {
					partAfterSourceFolder = partAfterSourceFolder[1].split("/");
					if (partAfterSourceFolder.length > 0) {
						sensorType = partAfterSourceFolder[1];
					}
				}
				
				if (sensorType == null) {
					throw new IOException("IOException: Could not determine the sensorType by folder structure!");
				}
				
				EvaluationDataSetEntry entry = new EvaluationDataSetEntry(fileFolderPath, fileName, fileExtension, sensorType, ImageReader.read(absoluteFilePath));
				set.addEntry(entry);
			}
		}
		
		// return the set, filled with all the entries and values:
		return set;
	}
}