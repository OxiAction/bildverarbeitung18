package core.evaluation;

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
	 * @param name			the name of the set
	 * @param imagePath		the full image path of the selected image
	 * @param sourceFolder	the full path of the selected scan folder
	 * @param kFactor		maximum number of files to be scanned
	 * @param heuristic		the heuristic which was used for the scan
	 */
	public EvaluationDataSet(String name, String imagePath, String sourceFolder, String kFactor, String heuristic) {
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
	
}