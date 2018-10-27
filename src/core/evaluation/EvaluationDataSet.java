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
	 * @deprecated use {@link #EvaluationDataSet(String name, String imagePath, String sourceFolder, String kFactor, String heuristic)} instead. 
	 */
	public EvaluationDataSet() {
		
	}
	
	public EvaluationDataSet(String name, String imagePath, String sourceFolder, String kFactor, String heuristic) {
		this.name = name;
		this.imagePath = imagePath;
		this.sourceFolder = sourceFolder;
		this.kFactor = kFactor;
		this.heuristic = heuristic;
	}
	
	public void addEntry(EvaluationDataSetEntry entry) {
		entries.add(entry);
	}
	
	public List<EvaluationDataSetEntry> getEntries() {
		return entries;
	}
	
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