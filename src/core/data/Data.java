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
 * Save / load data to / from .xml based on EvaluationDataSet objects.
 */
public class Data {

	public static String xmlName = "data.xml";

	/**
	 * Loads a .xml file and returns its document object.
	 * 
	 * @return document the document object
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
	 * Saves a document object to .xml file.
	 * 
	 * @param document the document object
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
	 * Create .xml file.
	 * 
	 * @return document the document object
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
	 * Resets the .xml file (deleting all data).
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
			throw new IOException("IOException: " + e);
		}
	}

	/**
	 * Saves the EvaluationDataSet in the .xml file.
	 * 
	 * @param set the EvaluationDataSet to be saved
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
			setElement.setAttribute("sourceFolder", set.getSourceFolder());
			setElement.setAttribute("kFactor", set.getKFactor());
			setElement.setAttribute("metricName", set.getMetricName());
			setElement.setAttribute("sliceX", set.getSliceX());
			setElement.setAttribute("sliceY", set.getSliceY());
			setElement.setAttribute("histogramSize", set.getHistogramSize());

			Element entries = document.createElement("entries");

			for (EvaluationDataSetEntry entry : set.getEntries()) {
				
				// create a new <entry> element
				Element entryElement = document.createElement("entry");
				// add attributes
				entryElement.setAttribute("id", String.valueOf(entry.getID()));
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				entryElement.setAttribute("fileName", entry.getFileName());
				entryElement.setAttribute("fileExtension", entry.getFileExtension());
				entryElement.setAttribute("sensorType", entry.getSensorType());
				
				//Element greyScaleData = document.createElement("greyScaleData");
				//greyScaleData.setTextContent(Utils.intArray2DToString(entry.getGreyScaleData()));
				//entryElement.appendChild(greyScaleData);
				
				Element histogramData = document.createElement("histogramData");
				histogramData.setTextContent(Utils.intArray1DToString(entry.getHistogramData()));
				entryElement.appendChild(histogramData);
				
				Element variance = document.createElement("variance");
				variance.setTextContent(String.valueOf(entry.getVariance()));
				entryElement.appendChild(variance);
				
				Element entropy = document.createElement("entropy");
				entropy.setTextContent(String.valueOf(entry.getEntropy()));
				entryElement.appendChild(entropy);
				
				Element localEntropy = document.createElement("localEntropy");
				localEntropy.setTextContent(String.valueOf(entry.getLocalEntropy()));
				entryElement.appendChild(localEntropy);
				
				Element kNearestIDs = document.createElement("kNearestIDs");
				kNearestIDs.setTextContent(Utils.intArrayListToString(entry.getKNearestIDs()));
				entryElement.appendChild(kNearestIDs);

				entries.appendChild(entryElement);
			}

			setElement.appendChild(entries);

			// make sure to append!
			root.appendChild(setElement);

			// save doc
			saveDocument(document);

		} catch (Exception e) {
			throw new IOException("IOException: " + e);
		}
	}

	/**
	 * Loads the .xml file and returns its EvaluationDataSet(s).
	 * 
	 * @return sets the EvaluationDataSet(s) as a list
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
			}
			;

			// get set nodes
			NodeList setNodes = document.getElementsByTagName("set");
			for (int i = 0; i < setNodes.getLength(); ++i) {
				Node setNode = setNodes.item(i);

				if (setNode.getNodeType() == Node.ELEMENT_NODE) {
					Element setElement = (Element) setNode;

					// get attributes
					String timestamp = setElement.getAttribute("timestamp");
					String name = setElement.getAttribute("name");
					String sourceFolder = setElement.getAttribute("sourceFolder");
					String kFactor = setElement.getAttribute("kFactor");
					String metricName = setElement.getAttribute("metricName");
					String sliceX = setElement.getAttribute("sliceX");
					String sliceY = setElement.getAttribute("sliceY");
					String histogramSize = setElement.getAttribute("histogramSize");

					// convert string to timestamp object
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date date = (Date) dateFormat.parse(timestamp);
					Timestamp timestampObject = new Timestamp(date.getTime());

					// create and add set to the list of sets
					EvaluationDataSet set = new EvaluationDataSet(timestampObject, name, sourceFolder, kFactor, metricName, sliceX, sliceY, histogramSize);

					Element entriesElement = (Element) setElement.getElementsByTagName("entries").item(0);

					// get entry nodes
					NodeList entryNodes = entriesElement.getElementsByTagName("entry");
					for (int j = 0; j < entryNodes.getLength(); ++j) {

						Node entryNode = entryNodes.item(j);

						if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
							Element entryElement = (Element) entryNode;
							
							// get attributes
							int id = Integer.parseInt(entryElement.getAttribute("id"));
							String fileFolderPath = entryElement.getAttribute("fileFolderPath");
							String fileName = entryElement.getAttribute("fileName");
							String fileExtension = entryElement.getAttribute("fileExtension");
							String sensorType = entryElement.getAttribute("sensorType");
							
							//Element greyScaleDataElement = (Element) entryElement.getElementsByTagName("greyScaleData").item(0);
							//int[][] greyScaleData = Utils.stringToIntArray2D(greyScaleDataElement.getTextContent());
							
							Element histogramDataElement = (Element) entryElement.getElementsByTagName("histogramData").item(0);
							int[] histogramData = Utils.stringToIntArray1D(histogramDataElement.getTextContent());
							
							Element varianceElement = (Element) entryElement.getElementsByTagName("variance").item(0);
							double variance = Double.parseDouble(varianceElement.getTextContent());
							
							Element entropyElement = (Element) entryElement.getElementsByTagName("entropy").item(0);
							double entropy = Double.parseDouble(entropyElement.getTextContent());
							
							Element localEntropyElement = (Element) entryElement.getElementsByTagName("localEntropy").item(0);
							double localEntropy = Double.parseDouble(localEntropyElement.getTextContent());
							
							Element kNearestIDsElement = (Element) entryElement.getElementsByTagName("kNearestIDs").item(0);
							ArrayList<Integer> kNearestIDs = Utils.stringToIntArrayList(kNearestIDsElement.getTextContent());
							
							// create and add entry to the set
							EvaluationDataSetEntry entry = new EvaluationDataSetEntry(id, fileFolderPath, fileName, fileExtension, sensorType, null, histogramData, variance, entropy, localEntropy, kNearestIDs, null);
							set.addEntry(entry);
						}
					}

					sets.add(set);
				}
			}
		} catch (Exception e) {
			throw new IOException("IOException: " + e);
		}

		return sets;
	}
}