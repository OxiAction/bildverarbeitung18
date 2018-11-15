package core.evaluation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import utils.Translation;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class EvaluationDataSet {
	protected Timestamp timestamp;
	protected String name;
	protected String sourceFolder;
	protected String kFactor;
	protected String metric;
	protected EvaluationDataSetEntry sourceEntry;
	protected List<EvaluationDataSetEntry> entries = new ArrayList<EvaluationDataSetEntry>();
	// indicates if this set needs to be updated or is just read
	public boolean save = true;
	
	/**
	 * constructor for a new EvaluationDataSet
	 * 
	 * @param timestamp		the timestamp (creation date of this set)
	 * @param name			the name of the set
	 * @param sourceFolder	the full path of the selected scan folder
	 * @param kFactor		maximum number of files to be scanned
	 * @param metric		the metric which was used for the scan
	 */
	public EvaluationDataSet(Timestamp timestamp, String name, String sourceFolder, String kFactor, String metric) {
		this.timestamp = timestamp;
		this.name = name;
		this.sourceFolder = sourceFolder;
		this.kFactor = kFactor;
		this.metric = metric;
	}
	
	/**
	 * adds a new EvaluationDataSetEntry to the list of entries
	 * 
	 * @param entry	the EvaluationDataSetEntry to be added
	 */
	public void addEntry(EvaluationDataSetEntry entry) {
		entries.add(entry);
	}
	
	/**
	 * returns all EvaluationDataSetEntries as a List
	 * 
	 * @return	all EvaluationDataSetEntries as a List
	 */
	public List<EvaluationDataSetEntry> getEntries() {
		return entries;
	}
	
	/**
	 * returns the size of the EvaluationDataSetEntries list
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
	 * @return the metric
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * @param metric the metric to set
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}
	
	// general stuff
	
	@Override
	public String toString() {
		return Translation.fetch("timestamp") + ": " + this.getTimestamp() + 
				"\n" + Translation.fetch("name") + ": " + this.getName() + 
				"\n" + Translation.fetch("source_folder") + ": " + this.getSourceFolder() + 
				"\n" + Translation.fetch("k_factor") + ": " + this.getKFactor() + 
				"\n" + Translation.fetch("metric") + ": " + this.getMetric();
	}
	
}