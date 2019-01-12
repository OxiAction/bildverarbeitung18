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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.evaluation.*;
import utils.Debug;

/**
 * Load data from .xml.
 */
public class Config {

	public static String xmlName = "config.xml";
	
	public static HashMap<String, EdgeDetectionConfig> sensorsEdgeDetectionConfig = new HashMap<String, EdgeDetectionConfig>();
	
	private static double var_max = 0.0;
	private static ArrayList<Double> vars = new ArrayList<Double>();

    public static void foo(double var) {
        synchronized (Config.class) {
        	vars.add(var);
        }
    }
    
    public static void foo2(double var) {
        synchronized (Config.class) {
            var_max = Math.max(var_max, var);
        }
    }
    
    /*public static void foog() {
    	PrintWriter writer = null;
		try {
			writer = new PrintWriter("C:\\Users\\Michi\\Desktop\\grundlagen_bildv_results\\variances.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (double var : vars) {
			writer.println(var);
		}
		
		writer.close();
    }*/
    
    public static void foor2() {
    	var_max = 0;
    }
    
    public static void foor() {
    	vars = new ArrayList<Double>();
    }


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
		Element rootElement = document.createElement("sensors");
		document.appendChild(rootElement);
		saveDocument(document);

		return document;
	}

	/**
	 * Loads the .xml file.
	 * 
	 * @throws IOException
	 */
	public static void load() throws IOException {
		Debug.log("--- load and store data from: " + xmlName);
		
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
			
			// sensors
			NodeList sensorNodes = document.getElementsByTagName("sensor");
			
			if (sensorNodes.getLength() > 0) {
				Debug.log("--- process sensors:");
				for (int i = 0; i < sensorNodes.getLength(); ++i) {
					Node sensorNode = sensorNodes.item(i);
	
					if (sensorNode.getNodeType() == Node.ELEMENT_NODE) {
						Element sensorElement = (Element) sensorNode;
						
						if (sensorElement.hasAttribute("name")) {
							EdgeDetectionConfig edgeDetectionConfig = new EdgeDetectionConfig();
							
							String name = sensorElement.getAttribute("name");
							
							if (sensorElement.hasAttribute("threshold1")) {
								edgeDetectionConfig.threshold1 = Integer.parseInt(sensorElement.getAttribute("threshold1"));
							}
							if (sensorElement.hasAttribute("threshold2")) {
								edgeDetectionConfig.threshold2 = Integer.parseInt(sensorElement.getAttribute("threshold2"));
							}
							if (sensorElement.hasAttribute("fingerPosition")) {
								edgeDetectionConfig.fingerPosition = Integer.parseInt(sensorElement.getAttribute("fingerPosition"));
							}
							if (sensorElement.hasAttribute("findAdjacentLinesFromMiddle")) {
								edgeDetectionConfig.findAdjacentLinesFromMiddle = Integer.parseInt(sensorElement.getAttribute("findAdjacentLinesFromMiddle")) == 1 ? true : false;
							}
							if (sensorElement.hasAttribute("offsetX")) {
								edgeDetectionConfig.offsetX = Integer.parseInt(sensorElement.getAttribute("offsetX"));
							}
							if (sensorElement.hasAttribute("offsetY")) {
								edgeDetectionConfig.offsetY = Integer.parseInt(sensorElement.getAttribute("offsetY"));
							}
							if (sensorElement.hasAttribute("clearTop")) {
								edgeDetectionConfig.clearTop = Integer.parseInt(sensorElement.getAttribute("clearTop")) == 1 ? true : false;
							}
							if (sensorElement.hasAttribute("clearBottom")) {
								edgeDetectionConfig.clearTop = Integer.parseInt(sensorElement.getAttribute("clearBottom")) == 1 ? true : false;
							}
							
							sensorsEdgeDetectionConfig.put(name, edgeDetectionConfig);
							
							Debug.log(String.format("%1$-25s", name) + edgeDetectionConfig);
						}
					}
				}
			} // end sensors
			
			Debug.log("--- done");
		} catch (Exception e) {
			throw new IOException("IOException: " + e);
		}
	}
}