package core.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

import core.evaluation.*;

/**
 * Save / load data to / from .xml based on objects which implement DataView interface
 * 
 * @author Michael Schreiber
 *
 */
public class Data {
	
	public static void save(DataView data) {
	}
	
	public static ArrayList<DataView> load() throws IOException {
		ArrayList<DataView> sets = new ArrayList<DataView>();
		
		try {
			File file = new File("data.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
	
			NodeList setNodes = document.getElementsByTagName("set");
	
			for (int i = 0; i < setNodes.getLength(); ++i) {
				Node setNode = setNodes.item(i);

				if (setNode.getNodeType() == Node.ELEMENT_NODE) {
					Element setElement = (Element) setNode;
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
					
					EvaluationDataSet set = new EvaluationDataSet(timestampObject, name, imagePath, sourceFolder, kFactor, heuristic);
					
					// TODO read / add entries
					
					sets.add(set);
				}
			}
		} catch (Exception e) {
			throw new IOException("An error occurred while trying to read the data.xml file. Check if the data XML file exists in the root folder of the app.");
		}
		
		
		return sets;
	}
}