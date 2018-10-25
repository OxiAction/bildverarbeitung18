package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import config.Config;
import events.*;
import utils.*;

/**
 * View: 
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabNewScan implements ViewInterface {

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
		
		CheckBox checkBox = new CheckBox();
		checkBox.setText("Foo");
		vBox.getChildren().add(checkBox);
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_new_scan"));
		vBox.getChildren().add(text);
		
		Button buttonSelectImage = new Button(Translation.fetch("button_new_scan_select_image"));
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Translation.fetch("filechooser_new_scan_type"), "*.jpg", "*.png"));
		fileChooser.setTitle(Translation.fetch("filechooser_new_scan_title"));
		buttonSelectImage.setOnAction(
			new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(final ActionEvent e) {
			        File selectedFile = fileChooser.showOpenDialog(Config.stage);
			        
			        if (selectedFile != null) {
			        	System.out.println(selectedFile.getName());
			        }
			    }
			});
		
		vBox.getChildren().add(buttonSelectImage);
		
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
}
