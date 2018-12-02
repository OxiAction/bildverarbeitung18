package views;

import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.*;
import core.data.Data;
import core.evaluation.*;
import core.graphic.Histogram;

/**
 * Tab content for the "scan results" section of the App.
 */
public class ViewTabScanResults implements ViewInterface {

	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;
	
	protected TabPane container;
	protected EvaluationDataSet set;
	
	/**
	 * Initialize / show components.
	 * 
	 * @param container
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof TabPane)) {
			throw new Exception("container doesnt seem to be of type TabPane!");
		}
		
		this.container = (TabPane) container;
		
		// verify data
		if (!(extraData instanceof EvaluationDataSet)) {
			throw new Exception("extraData doesnt seem to be of type EvaluationDataSet!");
		} else {
			set = (EvaluationDataSet) extraData;
		}
		
		if (set.save) {
			
			Evaluation evaluation = new Evaluation(set);

			evaluation.setOnRunning(event -> {
				Debug.log("Evaluation running...");
				
				Loader.show();
			});

			evaluation.setOnSucceeded(event -> {
				Debug.log("Evaluation success...");
				set = evaluation.getValue();
				this.process();
				
				Loader.hide();
			});
			
			evaluation.setOnFailed(event -> {
				Debug.log("Evaluation failed...");
				throw new RuntimeException(evaluation.getException());
			});
			evaluation.setOnCancelled(event -> {
				Debug.log("Evaluation cancelled...");
				
			});
			
			Thread t1 = new Thread(evaluation);
			t1.start();
		} else {
			this.process();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void process() {
		List<Text> texts = new ArrayList<Text>();
		
		TabPane tabPane = (TabPane) container;	
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
		// histogram
		Canvas histogram = Histogram.get(set);
		if (histogram != null) {
			Text textHistogram = new Text();
			textHistogram.setWrappingWidth(300);
			textHistogram.setFont(Font.font(textHistogram.getFont().getName(), FontWeight.BOLD, textHistogram.getFont().getSize()));
			textHistogram.setText(Translation.fetch("text_scan_results_histogram"));
			vBox.getChildren().add(textHistogram);
			// collect all texts
			texts.add(textHistogram);
		
			vBox.getChildren().add(histogram);
		}
			
		// source entry information
			
			/*
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
			*/
			
		// set information
			
			Text textSet = new Text();
			textSet.setWrappingWidth(300);
			textSet.setFont(Font.font(textSet.getFont().getName(), FontWeight.BOLD, textSet.getFont().getSize()));
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

				Task task = new Task<Void>() {
				    @Override public Void call() {
				    	try {
							Data.save(set);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
				        return null;
				    }
				};
				
				new Thread(task).start();
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
			
			TableColumn columnID = new TableColumn(Translation.fetch("id"));
			columnID.setMinWidth(50);
			columnID.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("ID"));
			
			TableColumn columnFileNameAndFileExtension = new TableColumn(Translation.fetch("file_name_and_file_extension"));
			columnFileNameAndFileExtension.setMinWidth(150);
			columnFileNameAndFileExtension.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("fileNameAndFileExtension"));
			
			TableColumn columnSensorType = new TableColumn(Translation.fetch("sensor_type"));
			columnSensorType.setMinWidth(50);
			columnSensorType.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("sensorType"));
			
			TableColumn columnVariance = new TableColumn(Translation.fetch("variance"));
			columnVariance.setMinWidth(50);
			columnVariance.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("varianceAsString"));
			
			TableColumn columnEntropy = new TableColumn(Translation.fetch("entropy"));
			columnEntropy.setMinWidth(50);
			columnEntropy.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("entropyAsString"));
			
			TableColumn columnSlicedEntropies = new TableColumn(Translation.fetch("sliced_entropies") + " (x: " + set.getSliceX() + " / y: " + set.getSliceY() + ")");
			columnSlicedEntropies.setMinWidth(50);
			columnSlicedEntropies.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("slicedEntropiesAsString"));
			
			TableColumn columnKNearestIDsAsString = new TableColumn(Translation.fetch("k_nearest_ids"));
			columnKNearestIDsAsString.setMinWidth(50);
			columnKNearestIDsAsString.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("KNearestIDsAsString"));
			
			TableColumn columnKNearestSensorTypesAsString = new TableColumn(Translation.fetch("k_nearest_sensor_types"));
			columnKNearestSensorTypesAsString.setMinWidth(50);
			columnKNearestSensorTypesAsString.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("KNearestSensorTypesAsString"));
			
			TableColumn columnNearestSensorType = new TableColumn(Translation.fetch("nearest_sensor_type"));
			columnNearestSensorType.setMinWidth(50);
			columnNearestSensorType.setCellValueFactory(new PropertyValueFactory<EvaluationDataSetEntry, String>("nearestSensorType"));
			
			/*
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
	        */
			
			TableView<EvaluationDataSetEntry> table = new TableView();
	        table.setItems(dataEntry);
	        table.getColumns().addAll(columnID, columnFileNameAndFileExtension, columnSensorType, columnVariance, columnEntropy, columnSlicedEntropies, columnKNearestIDsAsString, columnKNearestSensorTypesAsString, columnNearestSensorType);
	        
	        vBox.getChildren().add(table);
			
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
