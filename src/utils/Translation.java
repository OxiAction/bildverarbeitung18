package utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Be sure that the translations.xml file exists inside the root folder
 * 
 * XML-structure example:

<?xml version="1.0"?>
<translations>
	<language id="de">
		<first_name>Vorname</first_name>
		<last_name>Nachname</last_name>
	</language>
	<language id="en">
		<first_name>firstname</first_name>
		<last_name>lastname</last_name>
	</language>
</translations>

 *
 */
public class Translation {
	// fallback - in case OS detection fails
	private static final String DEFAULT_LANGUAGE = "en";

	private static String language;

	private static HashMap<String, HashMap<String, String>> translations = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, String> translation;
	
	// singleton instance
	private static Translation instance;

	private Translation() throws IOException {
		try {
			File file = new File("translations.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();

			NodeList languageNodes = document.getElementsByTagName("language");

			for (int i = 0; i < languageNodes.getLength(); ++i) {
				
				Node languageNode = languageNodes.item(i);

				if (languageNode.getNodeType() == Node.ELEMENT_NODE) {

					Element languageElement = (Element) languageNode;
					String languageID = languageElement.getAttribute("id");
					NodeList languageTranslationNodes = languageElement.getChildNodes();
					
					HashMap<String, String> languageTranslations = new HashMap<String, String>();
					for (int j = 0; j < languageTranslationNodes.getLength(); ++j) {
						
						Node languageTranslationNode = languageTranslationNodes.item(j);
						
						if (languageTranslationNode.getNodeType() == Node.ELEMENT_NODE) {
							String languageNodeID = languageTranslationNode.getNodeName();
							String languageNodeContent = languageTranslationNode.getTextContent();
							
							// fix for \n
							String fixedNewlineString = "";
							for (int f = 0; f < languageNodeContent.length(); ++f) {
								if (languageNodeContent.charAt(f) == '\\') {
									if (f + 1 < languageNodeContent.length()) {
										if (languageNodeContent.charAt(f + 1) == 'n') {
											fixedNewlineString += "\n";
											++f;
										}
									} else {
										fixedNewlineString += languageNodeContent.charAt(f);
									}
								} else {
									fixedNewlineString += languageNodeContent.charAt(f);
								}
							}
							
							languageTranslations.put(languageNodeID, fixedNewlineString);
						}
					}
					
					translations.put(languageID, languageTranslations);
				}

			}
		} catch (Exception e) {
			throw new IOException("An error occurred while trying to read the translations.xml file. Check if the translations XML file exists in the root folder of the app.");
		}
		
		// OS lang detection
		String osLanguage = System.getProperty("user.language").trim();
		
		// check if XML contains OS language
		if (translations.containsKey(osLanguage)) {
			setLanguage(osLanguage);
		} else {
			setLanguage(DEFAULT_LANGUAGE);
		}
	}

	public static synchronized Translation getInstance() throws IOException {
		if (Translation.instance == null) {
			Translation.instance = new Translation();
		}
		return Translation.instance;
	}

	/**
	 * change language - the language must be given in the XML otherwise throws exception
	 * 
	 * @param lang
	 */
	public static void setLanguage(String newLanguage) {
		// change
		if (!translations.containsKey(newLanguage)) {
			throw new NullPointerException("The language (" + newLanguage + ") is not available! Check the translations XML file.");
		} else {
			translation = translations.get(newLanguage);
			language = newLanguage;
		}
	}

	/**
	 * returns current selected language
	 * 
	 * @return
	 */
	public static String getLanguage() {
		return language;
	}

	/**
	 * get translation string by id
	 * 
	 * @param id
	 * @return
	 */
	public static String fetch(String id) {
		if (translation.containsKey(id)) {
			// translation exists -> fetch it
			return translation.get(id);
		} else {
			// this shows the dev if translations are missing
			return "(" + getLanguage() + ")" + id;
		}
	}
}