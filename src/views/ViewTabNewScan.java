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
import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import core.data.*;
import core.evaluation.EvaluationDataSet;
import events.*;
import utils.*;

/**
 * Tab content for the "new scan" section of the App  
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabNewScan implements ViewInterface {

	protected TextField textFieldName;
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
		
	// intro text
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_new_scan"));
		vBox.getChildren().add(text);
		
	// name
		
		Label labelName = new Label(Translation.fetch("name") + ":");
		textFieldName = new TextField();
		HBox hBoxName = new HBox();
		hBoxName.getChildren().addAll(labelName, textFieldName);
		hBoxName.setSpacing(10);
		vBox.getChildren().add(hBoxName);
		
		textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
			// limit length
			if (textFieldName.getText().length() > 10) {
				textFieldName.setText(textFieldName.getText().substring(0, 10));
			}
			
			updateButtonStartScan();
		});
		
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
			        	imagePath = uri.toString();
			        	
			        	updateButtonStartScan();
			        	
			        	Debug.log("-> image path: " + uri.toString());
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
		vBox.getChildren().add(buttonSelectSourceFolder);
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		buttonSelectSourceFolder.setOnAction(
				new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(final ActionEvent e) {
				    	File selectedDirectory = directoryChooser.showDialog(null);
				        
				        if (selectedDirectory != null) {
				        	URI uri = selectedDirectory.toURI();
				        	textFieldSourceFolder.setText(uri.toString());
				        	
				        	updateButtonStartScan();
				        	
				        	Debug.log("-> source folder: " + uri.toString());
				        } else {
				        	// no directory selected
				        }
				    }
				});
		
		textFieldSourceFolder.textProperty().addListener((observable, oldValue, newValue) -> {
			updateButtonStartScan();
		});
		
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
		
	// button start scan
		
		buttonStartScan = new Button(Translation.fetch("button_new_scan_start_scan"));
		if (!Debug.enabled) {
			buttonStartScan.setDisable(true);
		}
		buttonStartScan.setOnAction(
				new EventHandler<ActionEvent>() {

					@Override
				    public void handle(final ActionEvent e) {
						Debug.log("-> start scan");
						
						EvaluationDataSet set = new EvaluationDataSet(
								new Timestamp(System.currentTimeMillis()),
								textFieldName.getText().trim(),
								imagePath,
								textFieldSourceFolder.getText().trim(),
								comboBoxKFactor.getValue(),
								comboBoxHeuristic.getValue());
						
						EventManager.dispatch(new EventButtonStartScanClicked(), set);
					}});
		vBox.getChildren().add(buttonStartScan);
		
	// setup
		
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
		if (
				textFieldName.getText().trim().length() > 0 
				&& !textFieldSourceFolder.getText().trim().isEmpty() 
				&& image != null
			) {
			buttonStartScan.setDisable(false);
		} else if (!Debug.enabled) {
			buttonStartScan.setDisable(true);
		}
	}
}
