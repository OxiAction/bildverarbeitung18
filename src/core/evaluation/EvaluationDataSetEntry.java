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
	protected String fileFolderPath;
	protected String fileName;
	protected String fileExtension;
	protected int[][] greyScaleValues;
	
	/**
	 * constructor for a new EvaluationDataSet
	 * 
	 * @deprecated use {@link #EvaluationDataSetEntry(String filePath, String fileName, String fileExtension, int[][] greyScaleValues)} instead. 
	 */
	public EvaluationDataSetEntry() {
	}
	
	/**
	 * constructor for a new EvaluationDataSetEntry
	 * 
	 * @param fileFolderPath	the folder path of the file
	 * @param fileName			the file name (without extension!)
	 * @param fileExtension		the file extension (without .) - e.g. "jpg"
	 * @param greyScaleValues	the grey scale values as 2d int array
	 */
	public EvaluationDataSetEntry(String fileFolderPath, String fileName, String fileExtension, int[][] greyScaleValues) {
		this.fileFolderPath = fileFolderPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.greyScaleValues = greyScaleValues;
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