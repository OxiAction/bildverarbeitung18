package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.geometry.Insets;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import core.data.*;
import core.evaluation.EvaluationDataSet;
import core.evaluation.Metric;
import events.*;
import utils.*;

/**
 * Tab content for the "new scan" section of the App.
 */
public class ViewTabNewScan implements ViewInterface {

	protected TextField textFieldName;
	protected TextField textFieldSourceFolder;
	protected Image image;
	protected Button buttonStartScan;
	protected String imagePath;

	protected Label labelNameNotUnique;
	protected VBox vBox;
	protected HBox hBoxName;
	protected ArrayList<EvaluationDataSet> sets;

	/**
	 * Initialize / show components.
	 * 
	 * @param borderPane
	 * @param extraData
	 * @throws Exception 
	 */
	public void init(Object container, Object extraData) throws Exception {
		if (!(container instanceof TabPane)) {
			throw new Exception("container doesnt seem to be of type TabPane!");
		}

		final double labelsWidth = 125;

		// load sets
		sets = Data.load();

		TabPane tabPane = (TabPane) container;

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));

		// intro text

		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_new_scan"));
		vBox.getChildren().add(text);

		// name

		Label labelName = new Label(Translation.fetch("name") + ":");
		labelName.setPrefWidth(labelsWidth);
		textFieldName = new TextField();
		hBoxName = new HBox();
		hBoxName.getChildren().addAll(labelName, textFieldName);
		hBoxName.setSpacing(10);
		vBox.getChildren().add(hBoxName);

		textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
			// limit length
			if (textFieldName.getText().length() > 15) {
				textFieldName.setText(textFieldName.getText().substring(0, 15));
			}

			updateButtonStartScan();
		});

		// labelNameNotUnique will be added / removed dynamically
		labelNameNotUnique = new Label(Translation.fetch("name_not_unique"));
		labelNameNotUnique.setTextFill(Color.RED);
		labelNameNotUnique.setFont(Font.font(labelNameNotUnique.getFont().getName(), FontWeight.BOLD, labelNameNotUnique.getFont().getSize()));

		// source folder select

		Label labelSourceFolder = new Label(Translation.fetch("folder") + ":");
		labelSourceFolder.setPrefWidth(labelsWidth);
		textFieldSourceFolder = new TextField();
		HBox hBoxSourceFolder = new HBox();
		hBoxSourceFolder.getChildren().addAll(labelSourceFolder, textFieldSourceFolder);
		hBoxSourceFolder.setSpacing(10);
		vBox.getChildren().add(hBoxSourceFolder);

		Label labelSelectSourceFolder = new Label(""); // just empty label for spacing
		labelSelectSourceFolder.setPrefWidth(labelsWidth);
		Button buttonSelectSourceFolder = new Button(Translation.fetch("button_new_scan_select_source_folder"));
		HBox hBoxSelectSourceFolder = new HBox();
		hBoxSelectSourceFolder.getChildren().addAll(labelSelectSourceFolder, buttonSelectSourceFolder);
		hBoxSelectSourceFolder.setSpacing(10);
		vBox.getChildren().add(hBoxSelectSourceFolder);

		DirectoryChooser directoryChooser = new DirectoryChooser();
		buttonSelectSourceFolder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File selectedDirectory = directoryChooser.showDialog(null);

				if (selectedDirectory != null) {
					// absolute path
					textFieldSourceFolder.setText(selectedDirectory.getAbsolutePath());

					updateButtonStartScan();

					Debug.log("-> source folder: " + textFieldSourceFolder.getText());
				} else {
					// no directory selected
				}
			}
		});

		textFieldSourceFolder.textProperty().addListener((observable, oldValue, newValue) -> {
			updateButtonStartScan();
		});
		
		// edge detection
		
		Label labelEdgeDetection = new Label(Translation.fetch("edge_detection") + ":");
		labelEdgeDetection.setPrefWidth(labelsWidth);
		CheckBox checkBoxEdgeDetection = new CheckBox();
		checkBoxEdgeDetection.setSelected(false);
		HBox hBoxEdgeDetection = new HBox();
		hBoxEdgeDetection.getChildren().addAll(labelEdgeDetection, checkBoxEdgeDetection);
		hBoxEdgeDetection.setSpacing(10);
		vBox.getChildren().add(hBoxEdgeDetection);
		
		// k-factor

		Label labelKFactor = new Label(Translation.fetch("k_factor") + ":");
		labelKFactor.setPrefWidth(labelsWidth);
		ComboBox<String> comboBoxKFactor = new ComboBox<String>(FXCollections.observableArrayList("1", "4", "8", "16", "32"));
		comboBoxKFactor.getSelectionModel().select(0);
		HBox hBoxKFactor = new HBox();
		hBoxKFactor.getChildren().addAll(labelKFactor, comboBoxKFactor);
		hBoxKFactor.setSpacing(10);
		vBox.getChildren().add(hBoxKFactor);

		// metric

		Label labelMetric = new Label(Translation.fetch("metric") + ":");
		labelMetric.setPrefWidth(labelsWidth);
		ObservableList<String> comboBoxMetricValues = FXCollections.observableArrayList(Metric.getNames());
		ComboBox<String> comboBoxMetric = new ComboBox<String>(comboBoxMetricValues);
		comboBoxMetric.getSelectionModel().select(0);
		HBox hBoxMetric = new HBox();
		hBoxMetric.getChildren().addAll(labelMetric, comboBoxMetric);
		hBoxMetric.setSpacing(10);
		vBox.getChildren().add(hBoxMetric);

		// slice-x

		Label labelSliceX = new Label(Translation.fetch("slice_x") + ":");
		labelSliceX.setPrefWidth(labelsWidth);
		ComboBox<String> comboBoxSliceX = new ComboBox<String>(FXCollections.observableArrayList("2", "4", "8", "10"));
		comboBoxSliceX.getSelectionModel().select(0);
		HBox hBoxSliceX = new HBox();
		hBoxSliceX.getChildren().addAll(labelSliceX, comboBoxSliceX);
		hBoxSliceX.setSpacing(10);
		vBox.getChildren().add(hBoxSliceX);

		// slice-y

		Label labelSliceY = new Label(Translation.fetch("slice_y") + ":");
		labelSliceY.setPrefWidth(labelsWidth);
		ComboBox<String> comboBoxSliceY = new ComboBox<String>(FXCollections.observableArrayList("2", "4", "8", "10"));
		comboBoxSliceY.getSelectionModel().select(0);
		HBox hBoxSliceY = new HBox();
		hBoxSliceY.getChildren().addAll(labelSliceY, comboBoxSliceY);
		hBoxSliceY.setSpacing(10);
		vBox.getChildren().add(hBoxSliceY);

		// histogram-size

		Label labelHistogramSize = new Label(Translation.fetch("histogram_size") + ":");
		labelHistogramSize.setPrefWidth(labelsWidth);
		ComboBox<String> comboBoxHistogramSize = new ComboBox<String>(FXCollections.observableArrayList("256", "128", "64", "32"));
		comboBoxHistogramSize.getSelectionModel().select(0);
		HBox hBoxHistogramSize = new HBox();
		hBoxHistogramSize.getChildren().addAll(labelHistogramSize, comboBoxHistogramSize);
		hBoxHistogramSize.setSpacing(10);
		vBox.getChildren().add(hBoxHistogramSize);

		// button start scan

		buttonStartScan = new Button(Translation.fetch("button_new_scan_start_scan"));
		if (!Debug.enabled) {
			buttonStartScan.setDisable(true);
		}
		buttonStartScan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent e) {
				Debug.log("-> start scan");

				EvaluationDataSet set = new EvaluationDataSet(
						new Timestamp(System.currentTimeMillis()),
						textFieldName.getText().trim(),
						textFieldSourceFolder.getText().trim().replace('\\', '/'),
						checkBoxEdgeDetection.isSelected() ? true : false,
						comboBoxKFactor.getValue(),
						comboBoxMetric.getValue(),
						Integer.parseInt(comboBoxSliceX.getValue()),
						Integer.parseInt(comboBoxSliceY.getValue()),
						Integer.parseInt(comboBoxHistogramSize.getValue())
						);

				EventManager.dispatch(new EventButtonStartScanClicked(), set);
			}
		});
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

		// update texts width on resize
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}
		});

		updateButtonStartScan();
	}

	protected void updateButtonStartScan() {
		if (isTextFieldNameValid() && !textFieldSourceFolder.getText().trim().isEmpty()) {
			buttonStartScan.setDisable(false);
		} else {
			buttonStartScan.setDisable(true);
		}
	}

	protected boolean isTextFieldNameValid() {
		vBox.getChildren().remove(labelNameNotUnique);
		String value = textFieldName.getText().trim();
		if (value.length() == 0) {
			return false;
		} else {
			for (EvaluationDataSet set : sets) {
				if (set.getName().trim().equals(value)) {
					if (vBox.getChildren().indexOf(hBoxName) != -1) {
						// append error message after hBoxName
						vBox.getChildren().add(vBox.getChildren().indexOf(hBoxName) + 1, labelNameNotUnique);
					}

					return false;
				}
			}
		}

		return true;
	}
}
