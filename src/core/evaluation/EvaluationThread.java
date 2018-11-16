package core.evaluation;

import java.io.IOException;
import java.util.HashMap;

import utils.Debug;
import utils.Utils;

/**
 * TODO description
 * 
 * @author
 *
 */
public class EvaluationThread extends Thread {
	protected int threadID;
	protected String sourceFolder;
	protected String absoluteFilePath;
	protected EvaluationDataSetEntry entry;

	public EvaluationThread(int threadID, String sourceFolder, String absoluteFilePath) {
		this.threadID = threadID;
		this.sourceFolder = sourceFolder;
		this.absoluteFilePath = absoluteFilePath;
	}

	@Override
	public void run() {
		Debug.log("EvaluationThread @ running with ID: " + threadID);

		try {
			HashMap<String, String> infos = Utils.getAbsoluteFilePathInfos(this.absoluteFilePath);
			String fileName = infos.get("fileName");
			String fileFolderPath = infos.get("fileFolderPath");
			String fileExtension = infos.get("fileExtension");
			String sensorType = null;

			// get sensor type by folder structure...
			String[] partAfterSourceFolder = fileFolderPath.split(this.sourceFolder);

			if (partAfterSourceFolder.length > 0) {
				partAfterSourceFolder = partAfterSourceFolder[1].split("/");
				if (partAfterSourceFolder.length > 0) {
					sensorType = partAfterSourceFolder[1];
				}
			}

			if (sensorType == null) {
				throw new IOException("IOException: Could not determine the sensorType by folder structure!");
			}

			this.entry = new EvaluationDataSetEntry(fileFolderPath, fileName, fileExtension, sensorType, ImageReader.getGreyScaleValues(absoluteFilePath));
		} catch (IOException e) {
			Debug.log("IOException: : " + e);
		}

		Debug.log("EvaluationThread @ exiting with ID: " + threadID);
	}
	
	public EvaluationDataSetEntry getEntry() {
		return this.entry;
	}
}