package core.evaluation;

import java.util.ArrayList;
import java.util.stream.Collectors;

import utils.Translation;

/**
 * TODO description
 * 
 * @author
 *
 */
public class EvaluationDataSetEntry {
	protected int id;
	protected String fileFolderPath;
	protected String fileName;
	protected String fileExtension;
	protected String sensorType;
	protected int[][] greyScaleData;
	protected int[] histogramData;
	protected ArrayList<Integer> kNearestIDs;

	/**
	 * constructor for a new EvaluationDataSetEntry
	 * 
	 * @param id the unique id for this entry (in the set)
	 * @param fileFolderPath the folder path of the file
	 * @param fileName the file name (without extension!)
	 * @param fileExtension the file extension (without .) - e.g. "jpg"
	 * @param sensorType the sensor type
	 * @param greyScaleData the grey scale data as 2d int array
	 * @param histogramData the histogramData data 1d int array
	 * @param kNearestIDs the list with the k-nearest entries (ids)
	 */
	public EvaluationDataSetEntry(int id, String fileFolderPath, String fileName, String fileExtension, String sensorType, int[][] greyScaleData, int[] histogramData, ArrayList<Integer> kNearestIDs) {
		this.id = id;
		this.fileFolderPath = fileFolderPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.sensorType = sensorType;
		this.greyScaleData = greyScaleData;
		this.histogramData = histogramData;
		this.kNearestIDs = kNearestIDs;
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the fileFolderPath
	 */
	public String getFileFolderPath() {
		return this.fileFolderPath;
	}

	/**
	 * @param fileFolderPath the fileFolderPath to set
	 */
	public void setFileFolderPath(String fileFolderPath) {
		this.fileFolderPath = fileFolderPath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return this.fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the sensorType
	 */
	public String getSensorType() {
		return this.sensorType;
	}

	/**
	 * @param sensorType the sensorType to set
	 */
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	/**
	 * @return the greyScaleData
	 */
	public int[][] getGreyScaleData() {
		return this.greyScaleData;
	}

	/**
	 * @param greyScaleValues the greyScaleValues to set
	 */
	public void setGreyScaleData(int[][] greyScaleData) {
		this.greyScaleData = greyScaleData;
	}

	/**
	 * @return the histogramData
	 */
	public int[] getHistogramData() {
		return this.histogramData;
	}

	/**
	 * @param histogramData the metricData to set
	 */
	public void setHistogramData(int[] histogramData) {
		this.histogramData = histogramData;
	}

	/**
	 * @return the kNearestIDs
	 */
	public ArrayList<Integer> getKNearestIDs() {
		return this.kNearestIDs;
	}

	/**
	 * @return the kNearestIDs as String, divided by ", " - returns empty String if not IDs found
	 */
	public String getKNearestIDsAsString() {
		if (this.getKNearestIDs() == null || this.getKNearestIDs().size() == 0) {
			return "";
		}
		return this.getKNearestIDs().stream().map(Object::toString).collect(Collectors.joining(", "));
	}

	/**
	 * @param kNearestIDs the kNearestIDs to set
	 */
	public void setKNearestIDs(ArrayList<Integer> kNearestIDs) {
		this.kNearestIDs = kNearestIDs;
	}

	/**
	 * add a k-nearest entry by its id
	 * 
	 * @param id
	 */
	public void addKNearestByID(int id) {
		if (this.kNearestIDs == null) {
			this.kNearestIDs = new ArrayList<Integer>();
		}

		this.kNearestIDs.add(id);
	}

	public String getFileNameAndFileExtension() {
		return this.getFileName() + this.getFileExtension();
	}

	// general stuff

	@Override
	public String toString() {
		return Translation.fetch("file_folder_path") + ": " + this.getFileFolderPath() + 
				"\n" + Translation.fetch("file_name") + ": " + this.getFileName() + 
				"\n" + Translation.fetch("file_extension") + ": " + this.getFileExtension() + 
				"\n" + Translation.fetch("sensor_type") + ": " + this.getSensorType() + 
				"\n" + Translation.fetch("k_nearest_ids") + ": " + this.getKNearestIDsAsString();
	}

}