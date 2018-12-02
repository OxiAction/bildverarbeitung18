package core.evaluation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import utils.Translation;

/**
 * TODO description
 */
public class EvaluationDataSet {
	protected Timestamp timestamp;
	protected String name;
	protected String sourceFolder;
	protected String kFactor;
	protected String metricName;
	protected int sliceX;
	protected int sliceY;
	protected int histogramSize;
	protected EvaluationDataSetEntry sourceEntry;
	protected List<EvaluationDataSetEntry> entries = new ArrayList<EvaluationDataSetEntry>();
	
	/**
	 * indicates if this set needs to be updated or is just read
	 */
	public boolean save = true;
	
	/**
	 * Constructor for a new EvaluationDataSet.
	 * 
	 * @param timestamp		the timestamp (creation date of this set)
	 * @param name			the name of the set
	 * @param sourceFolder	the full path of the selected scan folder
	 * @param kFactor		maximum number of files to be scanned
	 * @param metricName	the metric name which was used for the scan
	 * @param sliceX		TODO
	 * @param sliceY		TODO
	 * @param histogramSize	TODO
	 */
	public EvaluationDataSet(Timestamp timestamp, String name, String sourceFolder, String kFactor, String metricName, int sliceX, int sliceY, int histogramSize) {
		this.timestamp = timestamp;
		this.name = name;
		this.sourceFolder = sourceFolder;
		this.kFactor = kFactor;
		this.metricName = metricName;
		this.sliceX = sliceX;
		this.sliceY = sliceY;
		this.histogramSize = histogramSize;
	}
	
	/**
	 * Adds a new EvaluationDataSetEntry to the list of entries.
	 * 
	 * @param entry	the EvaluationDataSetEntry to be added
	 */
	public void addEntry(EvaluationDataSetEntry entry) {
		entries.add(entry);
	}
	
	/**
	 * Returns all EvaluationDataSetEntries as a List.
	 * 
	 * @return	all EvaluationDataSetEntries as a List
	 */
	public List<EvaluationDataSetEntry> getEntries() {
		return entries;
	}
	
	/**
	 * Returns the size of the EvaluationDataSetEntries list.
	 * 
	 * @return	size of the EvaluationDataSetEntries list
	 */
	public Integer getEntriesSize() {
		return entries.size();
	}
	
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sourceFolder
	 */
	public String getSourceFolder() {
		return sourceFolder;
	}

	/**
	 * @param sourceFolder the sourceFolder to set
	 */
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	/**
	 * @return the kFactor
	 */
	public String getKFactor() {
		return kFactor;
	}

	/**
	 * @param kFactor the kFactor to set
	 */
	public void setKFactor(String kFactor) {
		this.kFactor = kFactor;
	}

	/**
	 * @return the metric name
	 */
	public String getMetricName() {
		return metricName;
	}

	/**
	 * @param metric the metric name to set
	 */
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	
	/**
	 * @return the sliceX
	 */
	public int getSliceX() {
		return sliceX;
	}

	/**
	 * @param sliceX the sliceX to set
	 */
	public void setSliceX(int sliceX) {
		this.sliceX = sliceX;
	}
	
	/**
	 * @return the sliceY
	 */
	public int getSliceY() {
		return sliceY;
	}

	/**
	 * @param sliceY the sliceY to set
	 */
	public void setSliceY(int sliceY) {
		this.sliceY = sliceY;
	}
	
	/**
	 * @return the histogramSize
	 */
	public int getHistogramSize() {
		return histogramSize;
	}

	/**
	 * @param histogramSize the histogramSize to set
	 */
	public void setHistogramSize(int histogramSize) {
		this.histogramSize = histogramSize;
	}
	
	@Override
	public String toString() {
		return Translation.fetch("timestamp") + ": " + this.getTimestamp() + 
				"\n" + Translation.fetch("name") + ": " + this.getName() + 
				"\n" + Translation.fetch("source_folder") + ": " + this.getSourceFolder() + 
				"\n" + Translation.fetch("k_factor") + ": " + this.getKFactor() + 
				"\n" + Translation.fetch("metric") + ": " + this.getMetricName() + 
				"\n" + Translation.fetch("slice_x") + ": " + this.getSliceX() + 
				"\n" + Translation.fetch("slice_y") + ": " + this.getSliceY() + 
				"\n" + Translation.fetch("histogram_size") + ": " + this.getHistogramSize();
	}
	
}