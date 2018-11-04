package core.evaluation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import utils.Debug;


public class Pathfinder {
	private ArrayList<String> paths, filenames, extensions ;
	
	public Pathfinder(){
		this.paths = new ArrayList<String>();
		this.filenames = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
	}
	
	
	//walks through all subfolders of the given rootfolder and returns arraylist of images
    public Pathfinder(String rootFolder) throws IOException{
    	this.paths = new ArrayList<String>();
		this.filenames = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
    	
		//adds paths
        Files.walk(Paths.get(rootFolder)).forEach(path -> {
        	String pathString = path.toString();
        	
        	if(pathString.contains(".jpg")) {
        		paths.add(pathString);
        		filenames.add(path.getFileName().toString());
        		extensions.add("jpg");
        			
        	}else if(pathString.contains(".png")) {
        		paths.add(pathString);
        		filenames.add(path.getFileName().toString());
        		extensions.add("png");
        		
        	}else if(pathString.contains(".bmp")) {
        		paths.add(pathString); 
        		filenames.add(path.getFileName().toString());
        		extensions.add("bmp");
        	}  	
        });         	       
    }
    
    public ArrayList<String> getPaths(){
    	return this.paths;
    }
    
    public ArrayList<String> getFilenames(){
    	return this.filenames;
    }
    
    public ArrayList<String> getExtensions(){
    	return this.extensions;
    }
    
    
    //returns k imagepaths
    ArrayList<String> getk_paths(int k) {
    	ArrayList<String> subpaths = new ArrayList<String>();
    	Debug.log("paths.size(): " + paths.size());
    	Debug.log("kFactor: " + k);
    	for(int i=0; i<k; i++) {
    		if (i >= paths.size()) {
				break;
			}
    		subpaths.add(paths.get(i));
    	}
    	return subpaths;
    }
    
    
    
    
}

