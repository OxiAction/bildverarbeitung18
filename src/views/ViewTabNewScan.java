package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import events.*;
import utils.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * View: 
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabNewScan implements ViewInterface {

	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;

	/**
	 * initialize / show components
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof TabPane)) {
			throw new Exception("container doesnt seem to be of type TabPane!");
		}
		
		TabPane tabPane = (TabPane) container;	
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
//		CheckBox checkBox = new CheckBox();
//		checkBox.setText("Foo");
//		vBox.getChildren().add(checkBox);
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_new_scan"));
		vBox.getChildren().add(text);
		
	// image select
		
		Label labelImage = new Label(Translation.fetch("image") + ":");
		ImageView imageView = new ImageView();
		imageView.setFitWidth(100);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		HBox hBoxImage = new HBox();
		hBoxImage.getChildren().addAll(labelImage, imageView);
		hBoxImage.setSpacing(10);
		vBox.getChildren().add(hBoxImage);
		
		Button buttonSelectImage = new Button(Translation.fetch("button_new_scan_select_image"));
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Translation.fetch("filechooser_new_scan_type"), "*.jpg", "*.png"));
		fileChooser.setTitle(Translation.fetch("filechooser_new_scan_title"));
		buttonSelectImage.setOnAction(
			new EventHandler<ActionEvent>() {

				@Override
			    public void handle(final ActionEvent e) {
			        File selectedFile = fileChooser.showOpenDialog(null);
			        
			        if (selectedFile != null) {
			        	URI uri = selectedFile.toURI();
			        	image = new Image(uri.toString());
			        	imageView.setImage(image);
			        	Debug.log(uri.toString());
			        } else {
			        	// no file selected
			        }
			    }
			});
		vBox.getChildren().add(buttonSelectImage);
		
	// source folder select
		
		Label labelSourceFolder = new Label(Translation.fetch("folder") + ":");
		textFieldSourceFolder = new TextField();
		HBox hBoxSourceFolder = new HBox();
		hBoxSourceFolder.getChildren().addAll(labelSourceFolder, textFieldSourceFolder);
		hBoxSourceFolder.setSpacing(10);
		vBox.getChildren().add(hBoxSourceFolder);
		
		Button buttonSelectSourceFolder = new Button(Translation.fetch("button_new_scan_select_source_folder"));
		DirectoryChooser directoryChooser = new DirectoryChooser();
		buttonSelectSourceFolder.setOnAction(
				new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(final ActionEvent e) {
				    	File selectedDirectory = directoryChooser.showDialog(null);
				        
				        if (selectedDirectory != null) {
				        	URI imageURI = selectedDirectory.toURI();
				        	imagePath = imageURI.toString();
				        	textFieldSourceFolder.setText(imagePath);
				        	Debug.log("-> iamge path: " + imagePath);
				        } else {
				        	// no directory selected
				        }
				    }
				});
		
		textFieldSourceFolder.textProperty().addListener((observable, oldValue, newValue) -> {
			updateButtonStartScan();
		});
		
		vBox.getChildren().add(buttonSelectSourceFolder);
		
	// k-factor
		
		Label labelKFactor = new Label(Translation.fetch("k_factor") + ":");
		ComboBox<String> comboBoxKFactor = new ComboBox<String>(FXCollections.observableArrayList(
		        "2",
		        "4",
		        "8",
		        "16"
		    ));
		comboBoxKFactor.getSelectionModel().select(0);
		HBox hBoxKFactor = new HBox();
		hBoxKFactor.getChildren().addAll(labelKFactor, comboBoxKFactor);
		hBoxKFactor.setSpacing(10);
		vBox.getChildren().add(hBoxKFactor);
		
	// heuristic
		
		Label labelHeuristic = new Label(Translation.fetch("heuristic") + ":");
		ComboBox<String> comboBoxHeuristic = new ComboBox<String>(FXCollections.observableArrayList(
		        "A",
		        "B",
		        "C",
		        "D"
		    ));
		comboBoxHeuristic.getSelectionModel().select(0);
		HBox hBoxHeuristic = new HBox();
		hBoxHeuristic.getChildren().addAll(labelHeuristic, comboBoxHeuristic);
		hBoxHeuristic.setSpacing(10);
		vBox.getChildren().add(hBoxHeuristic);
		
	// start scan
		
		buttonStartScan = new Button(Translation.fetch("button_new_scan_start_scan"));
		if (!Debug.enabled) {
			buttonStartScan.setDisable(true);
		}
		buttonStartScan.setOnAction(
				new EventHandler<ActionEvent>() {

					@Override
				    public void handle(final ActionEvent e) {
						Debug.log("-> start scan");
						
						HashMap<String, String> data = new HashMap<String, String>();
						data.put("image_path", imagePath);
						data.put("source_folder", textFieldSourceFolder.getText().trim());
						data.put("k_factor", comboBoxKFactor.getValue());
						data.put("heuristic", comboBoxHeuristic.getValue());
						
						EventManager.dispatch(new EventButtonStartScanClicked(), data);
					}});
		vBox.getChildren().add(buttonStartScan);
		
		scrollPane.setContent(vBox);
		
		Tab tab = new Tab(Translation.fetch("new_scan"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		tab.setOnCloseRequest(new EventHandler<Event>() {
		    @Override
		    public void handle(Event e) {
		    	EventManager.dispatch(new EventTabNewScanClosed());
		    }
		});
		
		// @TODO just a quick fix for resizing Text Nodes...
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}  
		});
	}
	
	protected void updateButtonStartScan() {
		String sourceFolderValue = textFieldSourceFolder.getText().trim();
		
		if (!sourceFolderValue.isEmpty() && image != null) {
			buttonStartScan.setDisable(false);
		} else if (!Debug.enabled) {
			buttonStartScan.setDisable(true);
		}
	}
}
