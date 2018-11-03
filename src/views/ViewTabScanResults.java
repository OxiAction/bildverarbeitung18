package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.geometry.Insets;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import events.*;
import utils.*;
import core.data.*;
import core.evaluation.*;
import core.graphic.*;

/**
 * Tab content for the "scan results" section of the App
 * 
 * @author 
 *
 */
public class ViewTabScanResults implements ViewInterface {

	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;

	/**
	 * initialize / show components
	 * 
	 * @param container
	 * @param extraData
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
	// TODO implement -> evaluate data
		
		EvaluationDataSet set;
		
		// verify data
		if (!(extraData instanceof EvaluationDataSet)) {
			throw new Exception("extraData doesnt seem to be of type EvaluationDataSet!");
		} else {
			set = (EvaluationDataSet) extraData;
		}
		
		// globally fire an event to let everybody know that we ENTER "loading" state
		EventManager.dispatch(new EventLoadingStarted(), null);
		
		// evaluation
		set = Evaluation.get(set);
		
	// histogram

		Canvas histogram = Histogram.get(set);
		vBox.getChildren().add(histogram);
		
	// store set
		
		Data.save(set);
		
	// text
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText("Test");
		vBox.getChildren().add(text);
		
	// table
		
		ObservableList<EvaluationDataSetEntry> dataEntry = FXCollections.observableArrayList(set.getEntries());
		
		TableColumn columnFileFolderPath = new TableColumn(Translation.fetch("file_folder_path"));
		columnFileFolderPath.setMinWidth(150);
		columnFileFolderPath.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileFolderPath"));
		
		TableColumn columnFileName = new TableColumn(Translation.fetch("file_name"));
		columnFileName.setMinWidth(150);
		columnFileName.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileName"));
		
		TableColumn columnFileExtension = new TableColumn(Translation.fetch("file_extension"));
		columnFileExtension.setMinWidth(150);
		columnFileExtension.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileExtension"));
		
		// for grey scale we need a button
		// this button will open a new window and show the histogram
		// see: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
		TableColumn columnGreyScaleValue = new TableColumn(Translation.fetch("grey_scale_value"));
		columnGreyScaleValue.setMinWidth(150);
		columnGreyScaleValue.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<EvaluationDataSetEntry, String>, TableCell<EvaluationDataSetEntry, String>> cellFactory = new Callback<TableColumn<EvaluationDataSetEntry, String>, TableCell<EvaluationDataSetEntry, String>>() {
		    @Override
		    public TableCell call(final TableColumn<EvaluationDataSetEntry, String> param) {
		        final TableCell<EvaluationDataSetEntry, String> cell = new TableCell<EvaluationDataSetEntry, String>() {
		
		            final Button buttonShowGreyScale = new Button(Translation.fetch("show"));
		
		            @Override
		            public void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                
		                if (empty) {
		                    setGraphic(null);
		                    setText(null);
		                } else {
		                	buttonShowGreyScale.setOnAction(event -> {
		                    	EvaluationDataSetEntry entry = getTableView().getItems().get(getIndex());
		                        
		                    	// TODO implement histogram graphic into new window
		                    	
		                        BorderPane borderPane = new BorderPane();
		                        Scene scene = new Scene(borderPane, 600, 600);
		                        Stage stage = new Stage();
		                        stage.setScene(scene);
		                        stage.setTitle(entry.getFileFolderPath() + "/" + entry.getFileName() + "." + entry.getFileExtension());
		                        stage.show();
		                    });
		                	
		                    setGraphic(buttonShowGreyScale);
		                    setText(null);
		                }
		            }
		        };
		        return cell;
		    }
		};
		columnGreyScaleValue.setCellFactory(cellFactory);
        
		TableView<EvaluationDataSetEntry> table = new TableView();
		table.minHeight(300); // TODO does not work as expected
        table.setItems(dataEntry);
        table.getColumns().addAll(columnFileFolderPath, columnFileName, columnFileExtension, columnGreyScaleValue);
        
        vBox.getChildren().add(table);
		
		// globally fire an event to let everybody know that we LEAVE "loading" state
		EventManager.dispatch(new EventLoadingFinished(), null);
		
	// setup
		
		scrollPane.setContent(vBox);
		
		Tab tab = new Tab(set.getName() + ": " + Translation.fetch("scan_results"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}  
		});
	}
}
