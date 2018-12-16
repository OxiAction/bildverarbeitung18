package views;

import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.io.IOException;
import core.data.*;
import events.*;
import utils.*;

/**
 * Tab content for the "config" section of the App.
 */
public class ViewTabConfig implements ViewInterface {

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

		TabPane tabPane = (TabPane) container;

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));

		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_config"));
		vBox.getChildren().add(text);

		Button buttonDataReset = new Button(Translation.fetch("button_data_reset"));
		buttonDataReset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent e) {
				try {
					Data.reset();
					buttonDataReset.setDisable(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		vBox.getChildren().add(buttonDataReset);

		scrollPane.setContent(vBox);

		Tab tab = new Tab(Translation.fetch("config"));
		tab.setContent(scrollPane);

		tabPane.getTabs().add(tab);

		tabPane.getSelectionModel().select(tab);

		tab.setOnCloseRequest(new EventHandler<Event>() {
			@Override
			public void handle(Event e) {
				EventManager.dispatch(new EventTabConfigClosed());
			}
		});

		// update texts width on resize
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}
		});
	}
}
