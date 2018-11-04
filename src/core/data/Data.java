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
	 * create .xml file
	 * @return document							the document object
	 * 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	protected static Document create() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement("sets");
		document.appendChild(rootElement);
		saveDocument(document);
		
		return document;
	}
	
	/**
	 * resets the .xml file (deleting all data)
	 * 
	 * @throws IOException
	 */
	public static void reset() throws IOException {
		try {
			Document document = null;
			
			// load doc
			try {
				document = loadDocument();
			}
			// create
			catch (IOException ignore) {
				document = create();
			}
			
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
			Document document = null;
			
			// load doc
			try {
				document = loadDocument();
			}
			// create
			catch (IOException ignore) {
				document = create();
			}
			
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
			setElement.setAttribute("metric", set.getMetric());
			
			EvaluationDataSetEntry sourceEntry = set.getSourceEntry();
			
			Element sourceEntryElement = document.createElement("sourceEntry");
			sourceEntryElement.setAttribute("fileFolderPath", sourceEntry.getFileFolderPath());
			sourceEntryElement.setAttribute("fileName", sourceEntry.getFileName());
			sourceEntryElement.setAttribute("fileExtension", sourceEntry.getFileExtension());
			sourceEntryElement.setAttribute("fileFolderPath", sourceEntry.getFileFolderPath());
			
			Element greyScale = document.createElement("greyScale");
			greyScale.setTextContent(Utils.intArrayToString(sourceEntry.getGreyScaleValues()));
			sourceEntryElement.appendChild(greyScale);
			
			setElement.appendChild(sourceEntryElement);
			
			Element entries = document.createElement("entries");
			
			for (EvaluationDataSetEntry entry : set.getEntries()) {
				// create a new <entry> element
				Element entryElement = document.createElement("entry");
				// add attributes
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				entryElement.setAttribute("fileName", entry.getFileName());
				entryElement.setAttribute("fileExtension", entry.getFileExtension());
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				
				greyScale = document.createElement("greyScale");
				greyScale.setTextContent(Utils.intArrayToString(entry.getGreyScaleValues()));
				entryElement.appendChild(greyScale);
				
				entries.appendChild(entryElement);
			}
			
			setElement.appendChild(entries);
			
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
			Document document = null;
			
			// load doc
			try {
				document = loadDocument();
			}
			// create
			catch (IOException ignore) {
				document = create();
			};
			
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
					String metric = setElement.getAttribute("metric");
					
					// convert string to timestamp object
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date date = (Date) dateFormat.parse(timestamp);
				    Timestamp timestampObject = new Timestamp(date.getTime());
					
				    // create and add set to the list of sets
					EvaluationDataSet set = new EvaluationDataSet(timestampObject, name, imagePath, sourceFolder, kFactor, metric);
					
					Element sourceEntryElement = (Element) setElement.getElementsByTagName("sourceEntry").item(0);
					
					// get attributes
					String fileFolderPath = sourceEntryElement.getAttribute("fileFolderPath");
					String fileName = sourceEntryElement.getAttribute("fileName");
					String fileExtension = sourceEntryElement.getAttribute("fileExtension");

					Element greyScaleElement = (Element) sourceEntryElement.getElementsByTagName("greyScale").item(0);
					String greyScaleValues = greyScaleElement.getTextContent();
					
					EvaluationDataSetEntry sourceEntry = new EvaluationDataSetEntry(fileFolderPath, fileName, fileExtension, Utils.stringToIntArray(greyScaleValues));
					set.setSourceEntry(sourceEntry);
					
					Element entriesElement = (Element) setElement.getElementsByTagName("entries").item(0);
					
					// get entry nodes
					NodeList entryNodes = entriesElement.getElementsByTagName("entry");
					for (int j = 0; j < entryNodes.getLength(); ++j) {
						
						Node entryNode = entryNodes.item(j);
						
						if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
							Element entryElement = (Element) entryNode;
							
							// get attributes
							fileFolderPath = entryElement.getAttribute("fileFolderPath");
							fileName = entryElement.getAttribute("fileName");
							fileExtension = entryElement.getAttribute("fileExtension");
							
							greyScaleElement = (Element) entryElement.getElementsByTagName("greyScale").item(0);
							greyScaleValues = greyScaleElement.getTextContent();
							
							// create and add entry to the set
							EvaluationDataSetEntry entry = new EvaluationDataSetEntry(fileFolderPath, fileName, fileExtension, Utils.stringToIntArray(greyScaleValues));
							set.addEntry(entry);
						}
					}
					
					sets.add(set);
				}
			}
		} catch (Exception e) {
			throw new IOException("Errior " + e);
		}
		
		return sets;
	}
}