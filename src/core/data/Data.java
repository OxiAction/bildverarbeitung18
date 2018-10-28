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
import utils.Utils;

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
		// init list for sets
		ArrayList<DataView> sets = new ArrayList<DataView>();
		
		try {
			// read file
			File file = new File("data.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			
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