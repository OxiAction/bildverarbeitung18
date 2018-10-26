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
	
	List<EvaluationDataSetEntry> entries = new ArrayList<EvaluationDataSetEntry>();
	
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