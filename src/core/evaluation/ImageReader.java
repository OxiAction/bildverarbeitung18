package core.evaluation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.imageio.ImageIO;

import utils.Debug;


public class ImageReader {
	private BufferedImage image;
	private String imagepath, imagename, imageextension;

	
	ImageReader(){
		this.image = null;
		this.setImagepath(null);
		this.setImagename(null);
		this.setImageextension(null);
	}
	
	ImageReader(String imagesource){
		Path path = Paths.get(imagesource);
		
		this.imagename = path.getFileName().toString();
		this.imagepath = imagesource.substring(0, imagesource.length()-imagename.length());
		
		if(imagesource.contains(".jpg"))
			this.imageextension = "jpg";
		else if(imagesource.contains(".png"))
			this.imageextension = "png";
		else if(imagesource.contains(".bmp"))
			this.imageextension = "bmp";
		
		try{
			this.image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			Debug.log("image path: " + imagesource);
			Debug.log("image name: " + imagename);
			Debug.log("image extension: " + imageextension);
			this.image = ImageIO.read(new File(imagesource));
			
			
			System.out.println("Reading complete.");	
			
		}catch(IOException e){
			System.out.println("Error: "+e);
		}
		
	}
		
	public void read(String imagesource) {
	try{
		this.image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
		
		this.image = ImageIO.read(new File(imagesource));
		
		System.out.println("Reading complete.");	
		
	}catch(IOException e){
		System.out.println("Error: "+e);
	}
	}
	
	int [][] convertTo2DArray(){
		int height = image.getHeight();
		int width= image.getWidth();
		int[][] result = new int [width][height];
		Raster raster = image.getData();
		
		for(int col = 0; col < height; col++) {
			for(int row = 0; row < width; row++) {
				result[row][col] = raster.getSample(row,col,0);
			}	
		}
		return result;	
	}
	
	public void printArray() {
		int[][]array = this.convertTo2DArray();
		for(int col=0; col<array[0].length; col++) {
			for(int row=0; row<array.length; row++) {
				System.out.print(array[row][col] + " ");
			}
			System.out.println();
		}
	}
	
	public void write() {
	    try{
	        ImageIO.write(image, "jpg", new File("output.jpg"));
	        System.out.println("Writing complete.");
	      }catch(IOException e){
	        System.out.println("Error: "+e);
	      }
	}
	
	
	public int imgHeight() {
		return image.getHeight();
	}
	
	public int imgWidth() {
		return image.getWidth();
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getImageextension() {
		return imageextension;
	}

	public void setImageextension(String imageextension) {
		this.imageextension = imageextension;
	}
	
}
