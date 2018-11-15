package core.evaluation;

import utils.Translation;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class EvaluationDataSetEntry {
	protected String fileFolderPath;
	protected String fileName;
	protected String fileExtension;
	protected String sensorType;
	protected int[][] greyScaleValues;
	
	/**
	 * constructor for a new EvaluationDataSetEntry
	 * 
	 * @param fileFolderPath	the folder path of the file
	 * @param fileName			the file name (without extension!)
	 * @param fileExtension		the file extension (without .) - e.g. "jpg"
	 * @param greyScaleValues	the grey scale values as 2d int array
	 */
	public EvaluationDataSetEntry(String fileFolderPath, String fileName, String fileExtension, String sensorType, int[][] greyScaleValues) {
		this.fileFolderPath = fileFolderPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.sensorType = sensorType;
		this.greyScaleValues = greyScaleValues;
	}
	
	/**
	 * @return the fileFolderPath
	 */
	public String getFileFolderPath() {
		return fileFolderPath;
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
		return fileName;
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
		return fileExtension;
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
	 * @return the greyScaleValues
	 */
	public int[][] getGreyScaleValues() {
		return greyScaleValues;
	}

	/**
	 * @param greyScaleValues the greyScaleValues to set
	 */
	public void setGreyScaleValues(int[][] greyScaleValues) {
		this.greyScaleValues = greyScaleValues;
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