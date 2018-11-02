package core.evaluation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


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
        		paths.add(pathString.substring(0, pathString.length()-path.getFileName().toString().length()));
        		filenames.add(path.getFileName().toString());
        		extensions.add("jpg");
        			
        	}else if(pathString.contains(".png")) {
        		paths.add(pathString.substring(0, pathString.length()-path.getFileName().toString().length()));
        		filenames.add(path.getFileName().toString());
        		extensions.add("png");
        		
        	}else if(pathString.contains(".bmp")) {
        		paths.add(pathString.substring(0, pathString.length()-path.getFileName().toString().length())); 
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
    	
    	for(int i=0; i<k; i++)
    		subpaths.add(paths.get(i));	
    	return subpaths;
    }
    
    
    
    
}

