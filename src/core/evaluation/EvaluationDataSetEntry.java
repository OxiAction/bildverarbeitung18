package core.evaluation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import utils.Translation;

/**
 * TODO description
 */
public class EvaluationDataSetEntry {
	protected DecimalFormat decimalFormat;
	
	protected int id;
	protected String fileFolderPath;
	protected String fileName;
	protected String fileExtension;
	protected String sensorType;
	protected int[][] greyScaleData;
	protected int[][][][] greyScaleSlicedData;
	protected int[] histogramData;
	protected ArrayList<Integer> kNearestIDs;
	protected ArrayList<String> kNearestSensorTypes;
	
	protected String nearestSensorType;

	/**
	 * Constructor for a new EvaluationDataSetEntry.
	 * 
	 * @param id 					the unique id for this entry (in the set)
	 * @param fileFolderPath 		the folder path of the file
	 * @param fileName 				the file name (without extension!)
	 * @param fileExtension 		the file extension (without .) - e.g. "jpg"
	 * @param sensorType 			the sensor type (full name as string)
	 * @param greyScaleData 		the grey scale data as 2d int array
	 * @param histogramData 		the histogramData data 1d int array
	 * @param kNearestIDs 			the list with the k-nearest entries (ids as integers)
	 * @param kNearestSensorTypes 	the list with the k-nearest entries (full names as string)
	 */
	public EvaluationDataSetEntry(
			int id,
			String fileFolderPath,
			String fileName,
			String fileExtension,
			String sensorType,
			int[][] greyScaleData,
			int[] histogramData,
			ArrayList<Integer> kNearestIDs,
			ArrayList<String> kNearestSensorTypes
			) {
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
		otherSymbols.setDecimalSeparator('.');
		this.decimalFormat = new DecimalFormat("0.0000", otherSymbols);
		
		this.setID(id);
		this.setFileFolderPath(fileFolderPath);
		this.setFileName(fileName);
		this.setFileExtension(fileExtension);
		this.setSensorType(sensorType);
		this.setGreyScaleData(greyScaleData);
		this.setHistogramData(histogramData);
		this.setKNearestIDs(kNearestIDs);
		this.setKNearestSensorTypes(kNearestSensorTypes);
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
	 * @return the greyScaleSlicedData
	 */
	public int[][][][] getGreyScaleSlicedData() {
		return this.greyScaleSlicedData;
	}

	/**
	 * @param greyScaleSlicedData the greyScaleSlicedData to set
	 */
	public void setGreyScaleSlicedData(int[][][][] greyScaleSlicedData) {
		this.greyScaleSlicedData = greyScaleSlicedData;
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
	 * Returns the k-nearest IDs as String (divided by ", ").
	 * 
	 * @return the formatted IDs or empty String
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
	 * Adds an ID to the ArrayList.
	 * 
	 * @param id
	 */
	public void addKNearestByID(int id) {
		if (this.kNearestIDs == null) {
			this.kNearestIDs = new ArrayList<Integer>();
		}

		this.kNearestIDs.add(id);
	}
	
	/**
	 * @return the kNearestSensorTypes
	 */
	public ArrayList<String> getKNearestSensorTypes() {
		return this.kNearestSensorTypes;
	}
	
	/**
	 * Returns the k-nearest sensor types as String (divided by ", ").
	 * 
	 * @return the formatted sensor types or empty String
	 */
	public String getKNearestSensorTypesAsString() {
		if (this.getKNearestSensorTypes() == null || this.getKNearestSensorTypes().size() == 0) {
			return "";
		}
		return this.getKNearestSensorTypes().stream().map(Object::toString).collect(Collectors.joining(", "));
	}
	
	/**
	 * Set the kNearestSensorTypes.
	 * Also sets the nearestSensorType.
	 * 
	 * @param kNearestSensorTypes the kNearestSensorTypes to set
	 */
	public void setKNearestSensorTypes(ArrayList<String> kNearestSensorTypes) {
		this.kNearestSensorTypes = kNearestSensorTypes;
		
		if (this.kNearestSensorTypes == null || this.kNearestSensorTypes.size() == 0) {
			return;
		}
		
		String result = 
			this.getKNearestSensorTypes().stream()
			.collect(Collectors.groupingBy(w -> w, Collectors.counting()))
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry::getValue))
			.get()
			.getKey();
		
		this.setNearestSensorType(result);
	}
	
	/**
	 * Adds a sensor type to the ArrayList.
	 * 
	 * @param sensorType
	 */
	public void addKNearestBySensorType(String sensorType) {
		if (this.kNearestSensorTypes == null) {
			this.kNearestSensorTypes = new ArrayList<String>();
		}

		this.kNearestSensorTypes.add(sensorType);
	}
	
	/**
	 * Set nearestSensorType
	 * 
	 * @param nearestSensorType
	 */
	public void setNearestSensorType(String nearestSensorType) {
		this.nearestSensorType = nearestSensorType;
	}
	
	/**
	 * Get the most used (k-nearest) sensor type as full name (String).
	 * 
	 * @return the most used sensor type or empty String
	 */
	public String getNearestSensorType() {
		return this.nearestSensorType;
	}
	
	/**
	 * Returns file name + file extension.
	 * 
	 * @return the file name and its extension
	 */
	public String getFileNameAndFileExtension() {
		return this.getFileName() + this.getFileExtension();
	}
	
	/**
	 * Compares if the sensorType equals the nearestSensorType.
	 * 
	 * @return true or false
	 */
	public boolean getIsSensorTypeEqualToNearestSensorType() {
		return this.getSensorType().equals(this.getNearestSensorType());
	}

	@Override
	public String toString() {
		return Translation.fetch("file_folder_path") + ": " + this.getFileFolderPath() + 
				"\n" + Translation.fetch("file_name") + ": " + this.getFileName() + 
				"\n" + Translation.fetch("file_extension") + ": " + this.getFileExtension() + 
				"\n" + Translation.fetch("sensor_type") + ": " + this.getSensorType() + 
				"\n" + Translation.fetch("k_nearest_ids") + ": " + this.getKNearestIDsAsString() +
				"\n" + Translation.fetch("k_nearest_sensor_types") + ": " + this.getKNearestSensorTypesAsString();
	}
}