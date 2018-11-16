package core.evaluation;

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
	protected int[][] metricData;
	
	/**
	 * constructor for a new EvaluationDataSetEntry
	 * 
	 * @param id				the unique id for this entry (in the set)
	 * @param fileFolderPath	the folder path of the file
	 * @param fileName			the file name (without extension!)
	 * @param fileExtension		the file extension (without .) - e.g. "jpg"
	 * @param sensorType		the sensor type
	 * @param greyScaleData		the grey scale data as 2d int array
	 * @param metricData		the metric data as 2d int array
	 */
	public EvaluationDataSetEntry(int id, String fileFolderPath, String fileName, String fileExtension, String sensorType, int[][] greyScaleData, int[][] metricData) {
		this.id = id;
		this.fileFolderPath = fileFolderPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.sensorType = sensorType;
		this.greyScaleData = greyScaleData;
		this.metricData = metricData;
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
	 * @return the metricData
	 */
	public int[][] getMetricData() {
		return this.metricData;
	}

	/**
	 * @param metricData the metricData to set
	 */
	public void setMetricData(int[][] metricData) {
		this.metricData = metricData;
	}
	
	// general stuff
	
		@Override
		public String toString() {
			return Translation.fetch("file_folder_path") + ": " + this.getFileFolderPath() + 
					"\n" + Translation.fetch("file_name") + ": " + this.getFileName() + 
					"\n" + Translation.fetch("file_extension") + ": " + this.getFileExtension() + 
					"\n" + Translation.fetch("sensor_type") + ": " + this.getSensorType();
		}
	
}