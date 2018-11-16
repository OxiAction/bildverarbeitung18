package core.evaluation;

import java.io.IOException;
import java.util.HashMap;

import utils.Debug;
import utils.Utils;

/**
 * Thread, calculating everything required for an EvaluationDataSetEntry:
 * - grey scale data
 * - metric data
 * 
 * @author
 *
 */
public class EvaluationThread extends Thread {
	protected int id;
	protected String absoluteFilePath;
	protected EvaluationDataSet set;
	protected EvaluationDataSetEntry entry;
	
	/**
	 * create a new thread which will create a new EvaluationDataSetEntry after finished
	 * 
	 * @param id				the unique id for this thread
	 * @param absoluteFilePath	the absolute (image) file path (e.g. "C:/test_folder/test_image.jpg"
	 * @param set				the parent set, which will contain the created EvaluationDataSetEntry
	 */
	public EvaluationThread(int id, String absoluteFilePath, EvaluationDataSet set) {
		this.id = id;
		this.absoluteFilePath = absoluteFilePath;
		this.set = set;
	}

	@Override
	public void run() {
		Debug.log("EvaluationThread @ running with ID: " + this.id);

		try {
			HashMap<String, String> infos = Utils.getAbsoluteFilePathInfos(this.absoluteFilePath);
			String fileName = infos.get("fileName");
			String fileFolderPath = infos.get("fileFolderPath");
			String fileExtension = infos.get("fileExtension");
			String sensorType = null;

			// get sensor type by folder structure...
			String[] partAfterSourceFolder = fileFolderPath.split(set.getSourceFolder());
			if (partAfterSourceFolder.length > 0) {
				partAfterSourceFolder = partAfterSourceFolder[1].split("/");
				if (partAfterSourceFolder.length > 0) {
					sensorType = partAfterSourceFolder[1];
				}
			}
			
			// error...
			if (sensorType == null) {
				throw new IOException("IOException: Could not determine the sensorType by folder structure!");
			}

			// create entry
			this.entry = new EvaluationDataSetEntry(this.id, fileFolderPath, fileName, fileExtension, sensorType, ImageReader.getGreyScaleData(absoluteFilePath), Metric.getDataByName(set.getMetricName(), 1));
		} catch (IOException e) {
			Debug.log("IOException: : " + e);
		}

		Debug.log("EvaluationThread @ exiting with ID: " + this.id);
	}
	
	public EvaluationDataSetEntry getEntry() {
		return this.entry;
	}
}