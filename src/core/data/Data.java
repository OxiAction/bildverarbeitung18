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
			setElement.setAttribute("edgeDetection", set.getEdgeDetection() ? "1" : "0");
			setElement.setAttribute("kFactor", set.getKFactor());
			setElement.setAttribute("metricName", set.getMetricName());
			setElement.setAttribute("sliceX", String.valueOf(set.getSliceX()));
			setElement.setAttribute("sliceY", String.valueOf(set.getSliceY()));
			setElement.setAttribute("histogramSize", String.valueOf(set.getHistogramSize()));

			Element entries = document.createElement("entries");

			for (EvaluationDataSetEntry entry : set.getEntries()) {
				// create a new <entry> element
				// IMPORTANT: greyScaleData is NOT being saved / loaded (due to performance reasons)
				Element entryElement = document.createElement("entry");
				
				// add attributes
				entryElement.setAttribute("id", String.valueOf(entry.getID()));
				entryElement.setAttribute("fileFolderPath", entry.getFileFolderPath());
				entryElement.setAttribute("fileName", entry.getFileName());
				entryElement.setAttribute("fileExtension", entry.getFileExtension());
				entryElement.setAttribute("sensorType", entry.getSensorType());
				
				// add elements
				Element histogramData = document.createElement("histogramData");
				histogramData.setTextContent(Utils.intArray1DToString(entry.getHistogramData()));
				entryElement.appendChild(histogramData);
				
				Element variance = document.createElement("variance");
				variance.setTextContent(String.valueOf(entry.getVariance()));
				entryElement.appendChild(variance);
				
				Element slicedVariances = document.createElement("slicedVariances");
				slicedVariances.setTextContent(Utils.doubleArray2DToString(entry.getSlicedVariances()));
				entryElement.appendChild(slicedVariances);
				
				Element entropy = document.createElement("entropy");
				entropy.setTextContent(String.valueOf(entry.getEntropy()));
				entryElement.appendChild(entropy);
				
				Element slicedEntropies = document.createElement("slicedEntropies");
				slicedEntropies.setTextContent(Utils.doubleArray2DToString(entry.getSlicedEntropies()));
				entryElement.appendChild(slicedEntropies);
				
				Element kNearestIDs = document.createElement("kNearestIDs");
				kNearestIDs.setTextContent(Utils.intArrayListToString(entry.getKNearestIDs()));
				entryElement.appendChild(kNearestIDs);
				
				Element kNearestSensorTypes = document.createElement("kNearestSensorTypes");
				kNearestSensorTypes.setTextContent(Utils.stringArrayListToString(entry.getKNearestSensorTypes()));
				entryElement.appendChild(kNearestSensorTypes);
				
				// append <entry>
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
					boolean edgeDetection = Integer.parseInt(setElement.getAttribute("edgeDetection")) == 1 ? true : false;
					String kFactor = setElement.getAttribute("kFactor");
					String metricName = setElement.getAttribute("metricName");
					int sliceX = Integer.parseInt(setElement.getAttribute("sliceX"));
					int sliceY = Integer.parseInt(setElement.getAttribute("sliceY"));
					int histogramSize = Integer.parseInt(setElement.getAttribute("histogramSize"));

					// convert string to timestamp object
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date date = (Date) dateFormat.parse(timestamp);
					Timestamp timestampObject = new Timestamp(date.getTime());

					// create and add set to the list of sets
					EvaluationDataSet set = new EvaluationDataSet(timestampObject, name, sourceFolder, edgeDetection, kFactor, metricName, sliceX, sliceY, histogramSize);

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
							
							Element histogramDataElement = (Element) entryElement.getElementsByTagName("histogramData").item(0);
							int[] histogramData = Utils.stringToIntArray1D(histogramDataElement.getTextContent());
							
							Element varianceElement = (Element) entryElement.getElementsByTagName("variance").item(0);
							double variance = Double.parseDouble(varianceElement.getTextContent());
							
							Element slicedVariancesElement = (Element) entryElement.getElementsByTagName("slicedVariances").item(0);
							double[][] slicedVariances = Utils.stringToDoubleArray2D(slicedVariancesElement.getTextContent());
							
							Element entropyElement = (Element) entryElement.getElementsByTagName("entropy").item(0);
							double entropy = Double.parseDouble(entropyElement.getTextContent());
							
							Element slicedEntropiesElement = (Element) entryElement.getElementsByTagName("slicedEntropies").item(0);
							double[][] slicedEntropies = Utils.stringToDoubleArray2D(slicedEntropiesElement.getTextContent());
							
							Element kNearestIDsElement = (Element) entryElement.getElementsByTagName("kNearestIDs").item(0);
							ArrayList<Integer> kNearestIDs = Utils.stringToIntArrayList(kNearestIDsElement.getTextContent());
							
							Element kNearestSensorTypesElement = (Element) entryElement.getElementsByTagName("kNearestSensorTypes").item(0);
							ArrayList<String> kNearestSensorTypes = Utils.stringToStringArrayList(kNearestSensorTypesElement.getTextContent());
							
							// create and add entry to the set
							// IMPORTANT: greyScaleData is NOT being saved / loaded (due to performance reasons) -> this means it will be "null"
							EvaluationDataSetEntry entry = new EvaluationDataSetEntry(
									id,
									fileFolderPath,
									fileName,
									fileExtension,
									sensorType,
									null,
									null,
									histogramData,
									variance,
									slicedVariances,
									entropy,
									slicedEntropies,
									kNearestIDs,
									kNearestSensorTypes
									);
							
							// add entry to set
							set.addEntry(entry);
						}
					}
					
					// add set to overall sets
					sets.add(set);
				}
			}
		} catch (Exception e) {
			throw new IOException("IOException: " + e);
		}

		return sets;
	}
}