package views;

import javafx.geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import utils.*;

/**
 * Tab content for the "welcome" (start screen) section of the App.
 */
public class ViewTabWelcome implements ViewInterface {

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
		
		VBox  vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_welcome"));
		vBox.getChildren().add(text);
		
		scrollPane.setContent(vBox);
		
		Tab tab = new Tab(Translation.fetch("welcome"));
		tab.setContent(scrollPane);
		
		tabPane.getTabs().add(tab);
		
		// @TODO just a quick fix for resizing Text Nodes...
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}  
		});
	}
}
