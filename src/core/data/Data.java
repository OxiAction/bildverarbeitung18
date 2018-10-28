package core.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.evaluation.*;
import utils.Utils;

/**
 * Save / load data to / from .xml based on EvaluationDataSet objects
 * TODO could be improved by adding more layers of abstraction. Atm. everything is pretty much hard-coded.
 * 
 * @author Michael Schreiber
 *
 */
public class Data {
	
	public static String xmlName = "data.xml";
	
	/**
	 * loads a .xml file and returns its document object
	 * 
	 * @return document							the document object
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	protected static Document loadDocument() throws SAXException, IOException, ParserConfigurationException {
		File file = new File(xmlName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		document.getDocumentElement().normalize();
		
		return document;
	}
	
	/**
	 * saves a document object to .xml file
	 * 
	 * @param document				the document object
	 * @throws TransformerException
	 */
	protected static void saveDocument(Document document) throws TransformerException {
		DOMSource source = new DOMSource(document);
		
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        StreamResult result = new StreamResult(xmlName);
        transformer.transform(source, result);
	}
	
	/**
	 * resets the .xml file (deleting all data)
	 * 
	 * @throws IOException
	 */
	public static void reset() throws IOException {
		try {
			// load doc
			Document document = loadDocument();
			
			NodeList setEntries = document.getElementsByTagName("set");
			
			// its important to do it reverse order
			for (int i = setEntries.getLength() - 1; i >= 0; --i) {
				Node setEntry = setEntries.item(i);
				// remove this <set...> from parent <sets...>
				setEntry.getParentNode().removeChild(setEntry);
			}
			
			// save doc
			saveDocument(document);
			
		} catch (Exception e) {
			throw new IOException("An error occurred while trying to read the data.xml file. Check if the data XML file exists in the root folder of the app.");
		}
	}
	
	/**
	 * saves the EvaluationDataSet in the .xml file
	 * 
	 * @param set			the EvaluationDataSet to be saved
	 * @throws IOException
	 */
	public static void save(EvaluationDataSet set) throws IOException {
		try {
			// load doc
			Document document = loadDocument();
			
			// get entry point
			Element root = document.getDocumentElement();
			
			// create a new <set> element
			Element setElement = document.createElement("set");
			// add attributes
			setElement.setAttribute("timestamp", set.getTimestamp().toString());
			setElement.setAttribute("name", set.getName());
			setElement.setAttribute("imagePath", set.getImagePath());
			setElement.setAttribute("sourceFolder", set.getSourceFolder());
			setElement.setAttribute("kFactor", set.getKFactor());
			setElement.setAttribute("heuristic", set.getHeuristic());
			
			for (EvaluationDataSetEntry entry : set.getEntries()) {
				// create a new <entry> element
				Element entryElement = document.createElement("entry");
				// add attributes
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				entryElement.setAttribute("fileName", entry.getFileName());
				entryElement.setAttribute("fileExtension", entry.getFileExtension());
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				entryElement.setAttribute("greyScaleValues", Utils.intArrayToString(entry.getGreyScaleValues()));
				
				setElement.appendChild(entryElement);
			}
			
			// make sure to append!
			root.appendChild(setElement);
			
			// save doc
			saveDocument(document);
			
		} catch (Exception e) {
			throw new IOException("An error occurred while trying to read the data.xml file. Check if the data XML file exists in the root folder of the app.");
		}
	}
	
	/**
	 * loads the .xml file and returns its EvaluationDataSet(s)
	 * 
	 * @return sets			the EvaluationDataSet(s) as a list
	 * @throws IOException
	 */
	public static ArrayList<EvaluationDataSet> load() throws IOException {
		// init list for sets
		ArrayList<EvaluationDataSet> sets = new ArrayList<EvaluationDataSet>();
		
		try {
			// load doc
			Document document = loadDocument();
			
			// get set nodes
			NodeList setNodes = document.getElementsByTagName("set");
			for (int i = 0; i < setNodes.getLength(); ++i) {
				Node setNode = setNodes.item(i);

				if (setNode.getNodeType() == Node.ELEMENT_NODE) {
					Element setElement = (Element) setNode;
					
					// get attributes
					String timestamp = setElement.getAttribute("timestamp");
					String name = setElement.getAttribute("name");
					String imagePath = setElement.getAttribute("imagePath");
					String sourceFolder = setElement.getAttribute("sourceFolder");
					String kFactor = setElement.getAttribute("kFactor");
					String heuristic = setElement.getAttribute("heuristic");
					
					// convert string to timestamp object
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date date = (Date) dateFormat.parse(timestamp);
				    Timestamp timestampObject = new Timestamp(date.getTime());
					
				    // create and add set to the list of sets
					EvaluationDataSet set = new EvaluationDataSet(timestampObject, name, imagePath, sourceFolder, kFactor, heuristic);
					sets.add(set);
					
					// get entry nodes
					NodeList entryNodes = setElement.getElementsByTagName("entry");
					for (int j = 0; j < entryNodes.getLength(); ++j) {
						
						Node entryNode = entryNodes.item(i);
						
						if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
							Element entryElement = (Element) entryNode;
							
							// get attributes
							String fileFolderPath = entryElement.getAttribute("fileFolderPath");
							String fileName = entryElement.getAttribute("fileName");
							String fileExtension = entryElement.getAttribute("fileExtension");
							String greyScaleValues = entryElement.getAttribute("greyScaleValues");
							
							// create and add entry to the set
							EvaluationDataSetEntry entry = new EvaluationDataSetEntry(fileFolderPath, fileName, fileExtension, Utils.stringToIntArray(greyScaleValues));
							set.addEntry(entry);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IOException("An error occurred while trying to read the data.xml file. Check if the data XML file exists in the root folder of the app.");
		}
		
		return sets;
	}
}