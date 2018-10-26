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

import events.*;
import utils.*;
import core.parsers.*;

/**
 * Tab content for the "config" section of the App 
 * 
 * @author Michael Schreiber
 *
 */
public class ViewTabConfig implements ViewInterface {

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
		
		Text text = new Text();
		text.setWrappingWidth(300);
		text.setText(Translation.fetch("text_config"));
		vBox.getChildren().add(text);
		
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
		
		// @TODO just a quick fix for resizing Text Nodes...
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
				text.setWrappingWidth(newBounds.getWidth() - 25);
			}  
		});
	}
}
