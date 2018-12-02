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
	protected double variance;
	protected double entropy;
	protected ArrayList<Integer> kNearestIDs;
	protected ArrayList<String> kNearestSensorTypes;
	protected double[][] slicedEntropies;

	/**
	 * Constructor for a new EvaluationDataSetEntry.
	 * 
	 * @param id 					the unique id for this entry (in the set)
	 * @param fileFolderPath 		the folder path of the file
	 * @param fileName 				the file name (without extension!)
	 * @param fileExtension 		the file extension (without .) - e.g. "jpg"
	 * @param sensorType 			the sensor type (full name as string)
	 * @param greyScaleData 		the grey scale data as 2d int array
	 * @param greyScaleSlicedData 	the grey scale data as sliced 4d int array
	 * @param histogramData 		the histogramData data 1d int array
	 * @param variance 				the variance value - given by the algorithm
	 * @param entropy 				the entropy value - given by the algorithm
	 * @param kNearestIDs 			the list with the k-nearest entries (ids as integers)
	 * @param kNearestSensorTypes 	the list with the k-nearest entries (full names as string)
	 * @param slicedEntropies 		2d double array with calculated entropies (from slices)
	 */
	public EvaluationDataSetEntry(
			int id,
			String fileFolderPath,
			String fileName,
			String fileExtension,
			String sensorType,
			int[][] greyScaleData,
			int[][][][] greyScaleSlicedData,
			int[] histogramData,
			double variance,
			double entropy,
			ArrayList<Integer> kNearestIDs,
			ArrayList<String> kNearestSensorTypes,
			double[][] slicedEntropies
			) {
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
		otherSymbols.setDecimalSeparator('.');
		this.decimalFormat = new DecimalFormat("0.0000", otherSymbols);
		
		this.id = id;
		this.fileFolderPath = fileFolderPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.sensorType = sensorType;
		this.greyScaleData = greyScaleData;
		this.greyScaleSlicedData = greyScaleSlicedData;
		this.histogramData = histogramData;
		this.variance = variance;
		this.entropy = entropy;
		this.kNearestIDs = kNearestIDs;
		this.kNearestSensorTypes = kNearestSensorTypes;
		this.slicedEntropies = slicedEntropies;
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
	 * Add a k-nearest entry by its ID.
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
	 * @param kNearestSensorTypes the kNearestSensorTypes to set
	 */
	public void setKNearestSensorTypes(ArrayList<String> kNearestSensorTypes) {
		this.kNearestSensorTypes = kNearestSensorTypes;
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
	 * Get the most used (k-nearest) sensor type as full name (String).
	 * 
	 * @return the most used sensor type or empty String
	 */
	public String getNearestSensorType() {
		if (this.getKNearestSensorTypes() == null || this.getKNearestSensorTypes().size() == 0) {
			return "";
		}
		
		String result = 
			this.getKNearestSensorTypes().stream()
			.collect(Collectors.groupingBy(w -> w, Collectors.counting()))
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry::getValue))
			.get()
			.getKey();
		
		
		return result;
	}
	
	/**
	 * @param variance the variance to set
	 */
	public void setVariance(double variance) {
		this.variance = variance;
	}
	
	/**
	 * @return the variance
	 */
	public double getVariance() {
		return this.variance;
	}
	
	/**
	 * Decimal formatted variance (with limited amount of decimal numbers).
	 * 
	 * @return  the variance as String (formatted)
	 */
	public String getVarianceAsString() {
		return this.decimalFormat.format(this.variance);
	}
	
	/**
	 * @param entropy the entropy to set
	 */
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	
	/**
	 * @return the entropy
	 */
	public double getEntropy() {
		return this.entropy;
	}
	
	/**
	 * Decimal formatted entropy (with limited amount of decimal numbers).
	 * 
	 * @return the entropy as String (formatted)
	 */
	public String getEntropyAsString() {
		return this.decimalFormat.format(this.entropy);
	}
	
	/**
	 * @param slicedEntropies the slicedEntropies to set
	 */
	public void setSlicedEntropies(double[][] slicedEntropies) {
		this.slicedEntropies = slicedEntropies;
	}
	
	/**
	 * @return the slicedEntropies
	 */
	public double[][] getSlicedEntropies() {
		return this.slicedEntropies;
	}
	
	/**
	 * Returns formatted String with sliced entropies.
	 * Using \n (newline) and \t (tab) to format the String.
	 * 
	 * @return the formatted sliced entropies or empty String
	 */
	public String getSlicedEntropiesAsString() {
		String result = "";
		
		if (this.slicedEntropies == null || this.slicedEntropies.length < 1) {
			return result;
		}
		
		for (int i = 0; i < this.slicedEntropies.length; ++i) {
			if (i > 0) {
				result += "\n";
			}
			
			for (int j = 0; j < this.slicedEntropies[i].length; ++j) {
				if (j > 0) {
					result += " \t";
				}
				
				result += String.valueOf(this.decimalFormat.format(this.slicedEntropies[i][j]));
			}
		}
		
		return result;
	}
	
	/**
	 * Returns file name + file extension.
	 * 
	 * @return the file name and its extension
	 */
	public String getFileNameAndFileExtension() {
		return this.getFileName() + this.getFileExtension();
	}

	@Override
	public String toString() {
		return Translation.fetch("file_folder_path") + ": " + this.getFileFolderPath() + 
				"\n" + Translation.fetch("file_name") + ": " + this.getFileName() + 
				"\n" + Translation.fetch("file_extension") + ": " + this.getFileExtension() + 
				"\n" + Translation.fetch("sensor_type") + ": " + this.getSensorType() + 
				"\n" + Translation.fetch("variance") + ": " + this.getVariance() + 
				"\n" + Translation.fetch("entropy") + ": " + this.getEntropy() + 
				"\n" + Translation.fetch("k_nearest_ids") + ": " + this.getKNearestIDsAsString() +
				"\n" + Translation.fetch("k_nearest_sensor_types") + ": " + this.getKNearestSensorTypesAsString();
	}
}