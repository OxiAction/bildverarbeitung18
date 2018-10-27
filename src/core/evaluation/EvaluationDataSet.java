package core.evaluation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import core.data.DataView;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class EvaluationDataSet implements DataView {
	protected Timestamp timestamp;
	protected String name;
	protected String imagePath;
	protected String sourceFolder;
	protected String kFactor;
	protected String heuristic;
	protected List<EvaluationDataSetEntry> entries = new ArrayList<EvaluationDataSetEntry>();
	
	/**
	 * constructor for a new EvaluationDataSet
	 * 
	 * @deprecated use {@link #EvaluationDataSet(String name, String imagePath, String sourceFolder, String kFactor, String heuristic)} instead. 
	 */
	public EvaluationDataSet() {
	}
	
	/**
	 * constructor for a new EvaluationDataSet
	 * 
	 * @param timestamp		the timestamp (creation date of this set)
	 * @param name			the name of the set
	 * @param imagePath		the full image path of the selected image
	 * @param sourceFolder	the full path of the selected scan folder
	 * @param kFactor		maximum number of files to be scanned
	 * @param heuristic		the heuristic which was used for the scan
	 */
	public EvaluationDataSet(Timestamp timestamp, String name, String imagePath, String sourceFolder, String kFactor, String heuristic) {
		this.timestamp = timestamp;
		this.name = name;
		this.imagePath = imagePath;
		this.sourceFolder = sourceFolder;
		this.kFactor = kFactor;
		this.heuristic = heuristic;
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * @return the heuristic
	 */
	public String getHeuristic() {
		return heuristic;
	}

	/**
	 * @param heuristic the heuristic to set
	 */
	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public Node toXMLNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataView fromXMLNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// general stuff
	
	@Override
	public String toString() {
		return "timestamp: " + this.getTimestamp() + 
				"\nname: " + this.getName() + 
				"\nimagePath: " + this.getImagePath() + 
				"\nsourceFolder: " + this.getSourceFolder() + 
				"\nkFactor: " + this.getKFactor() + 
				"\nheuristic: " + this.getHeuristic();
	}
	
}