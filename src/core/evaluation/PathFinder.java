package core.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import utils.Debug;


public class PathFinder {
	
    public static ArrayList<String> getPaths(String sourceFolder) throws IOException {
    	ArrayList<String> paths = new ArrayList<String>();
    	String[] validExtensions = new String[] {".jpg", ".png", ".bmp"};
    	
        try {
			Files.walk(Paths.get(sourceFolder)).forEach(path -> {
				String pathString = path.toString();
				
				for (String extension : validExtensions) {
				  if (pathString.contains(extension)) {
					  Debug.log("adding path: " + pathString);
					  paths.add(pathString.replace('\\', '/'));
				    break;
				  }
				}	
			});
			
			return paths;
		} catch (IOException e) {
			throw new IOException("IOException: " + e);
		}
    } 
}

