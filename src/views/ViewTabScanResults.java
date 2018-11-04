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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	protected EvaluationDataSet set;

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
		
		List<Text> texts = new ArrayList<Text>();
		
		TabPane tabPane = (TabPane) container;	
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
	// TODO implement -> evaluate data
		
		// verify data
		if (!(extraData instanceof EvaluationDataSet)) {
			throw new Exception("extraData doesnt seem to be of type EvaluationDataSet!");
		} else {
			set = (EvaluationDataSet) extraData;
		}
		
		// globally fire an event to let everybody know that we ENTER "loading" state
		EventManager.dispatch(new EventLoadingStarted(), null);
		
		// evaluation
		
		for (EvaluationDataSetEntry entry : set.getEntries()) {
			System.out.println(entry.getFileName());
			
		}
		
		if (set.save) {
			set = Evaluation.get(set);
		}
		
	// histogram
		
		Text textHistogram = new Text();
		textHistogram.setWrappingWidth(300);
		textHistogram.setFont(Font.font(textHistogram.getFont().getName(), FontWeight.BOLD, textHistogram.getFont().getSize()));
		textHistogram.setText(Translation.fetch("text_scan_results_histogram"));
		vBox.getChildren().add(textHistogram);
		// collect all texts
		texts.add(textHistogram);
		
		Canvas histogram = Histogram.get(set);
		vBox.getChildren().add(histogram);
		
	// source entry information
		
		EvaluationDataSetEntry sourceEntry = set.getSourceEntry();
		
		Text textSourceEntry = new Text();
		textSourceEntry.setWrappingWidth(300);
		textSourceEntry.setFont(Font.font(textSourceEntry.getFont().getName(), FontWeight.BOLD, textSourceEntry.getFont().getSize()));
		textSourceEntry.setText(Translation.fetch("text_scan_results_source_entry"));
		vBox.getChildren().add(textSourceEntry);
		// collect all texts
		texts.add(textSourceEntry);
		
		Text textSourceEntryInformations = new Text();
		textSourceEntryInformations.setWrappingWidth(300);
		textSourceEntryInformations.setText(sourceEntry.toString());
		vBox.getChildren().add(textSourceEntryInformations);
		// collect all texts
		texts.add(textSourceEntryInformations);
		
	// set information
		
		Text textSet = new Text();
		textSet.setWrappingWidth(300);
		textSet.setFont(Font.font(textSourceEntry.getFont().getName(), FontWeight.BOLD, textSourceEntry.getFont().getSize()));
		textSet.setText(Translation.fetch("text_scan_results_set"));
		vBox.getChildren().add(textSet);
		// collect all texts
		texts.add(textSet);
		
		Text textSetInformations = new Text();
		textSetInformations.setWrappingWidth(300);
		textSetInformations.setText(set.toString());
		vBox.getChildren().add(textSetInformations);
		// collect all texts
		texts.add(textSetInformations);
		
	// store set
		
		if (set.save) {
			Data.save(set);
		}
		
	// table
		
		Text textTable = new Text();
		textTable.setWrappingWidth(300);
		textTable.setFont(Font.font(textTable.getFont().getName(), FontWeight.BOLD, textTable.getFont().getSize()));
		textTable.setText(Translation.fetch("text_scan_results_table"));
		vBox.getChildren().add(textTable);
		// collect all texts
		texts.add(textTable);
		
		ObservableList<EvaluationDataSetEntry> dataEntry = FXCollections.observableArrayList(set.getEntries());
		
		TableColumn columnFileFolderPath = new TableColumn(Translation.fetch("file_folder_path"));
		columnFileFolderPath.setMinWidth(150);
		columnFileFolderPath.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileFolderPath"));
		
		TableColumn columnFileName = new TableColumn(Translation.fetch("file_name"));
		columnFileName.setMinWidth(150);
		columnFileName.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileName"));
		
		TableColumn columnFileExtension = new TableColumn(Translation.fetch("file_extension"));
		columnFileExtension.setMinWidth(50);
		columnFileExtension.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileExtension"));
		
		TableColumn columnSensorType = new TableColumn(Translation.fetch("sensor_type"));
		columnSensorType.setMinWidth(50);
		columnSensorType.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("sensorType"));
		
		// for grey scale we need a button
		// this button will open a new window and show the histogram
		// see: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
		TableColumn columnGreyScaleValue = new TableColumn(Translation.fetch("grey_scale_value"));
		columnGreyScaleValue.setMinWidth(50);
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
		                        
		                        Canvas histogram = Histogram.get(set);
		                        
		                        borderPane.setCenter(histogram);
		                        
		                        Scene scene = new Scene(borderPane, 600, 600);
		                        Stage stage = new Stage();
		                        stage.setScene(scene);
		                        stage.setTitle(Translation.fetch("grey_scale_value") + ": " + entry.getFileFolderPath() + "/" + entry.getFileName() + "." + entry.getFileExtension());
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
        table.setItems(dataEntry);
        table.getColumns().addAll(columnFileFolderPath, columnFileName, columnFileExtension, columnSensorType, columnGreyScaleValue);
        
        vBox.getChildren().add(table);
		
		// globally fire an event to let everybody know that we LEAVE "loading" state
		EventManager.dispatch(new EventLoadingFinished(), null);
		
	// setup
		
		scrollPane.setContent(vBox);
		// needs to be disabled in this case
		scrollPane.setFitToHeight(false);
		
		Tab tab = new Tab(set.getName() + ": " + Translation.fetch("scan_results"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		tabPane.getSelectionModel().select(tab);
		
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				for (Text text : texts) {
					text.setWrappingWidth(newBounds.getWidth() - 25);
				}
			}  
		});
	}
}
