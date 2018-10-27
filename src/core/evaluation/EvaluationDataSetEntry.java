package core.evaluation;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import core.data.DataView;

/**
 * TODO description
 * 
 * @author 
 *
 */
public class EvaluationDataSetEntry implements DataView {
	protected String filePath;
	protected String fileName;
	protected String fileExtension;
	protected int[][] greyScaleValues;
	
	/**
	 * @deprecated use {@link #EvaluationDataSetEntry(String filePath, String fileName, String fileExtension, int[][] greyScaleValues)} instead. 
	 */
	public EvaluationDataSetEntry() {
		
	}
	
	public EvaluationDataSetEntry(String filePath, String fileName, String fileExtension, int[][] greyScaleValues) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.greyScaleValues = greyScaleValues;
	}
	

	@Override
	public Node toXMLNode() {
//		Element element = (Element) new Node();
//		element.setAttribute("file_path", this.filePath);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataView fromXMLNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
}