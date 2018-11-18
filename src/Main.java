import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import utils.Debug;
import utils.Loader;
import utils.Translation;
import views.ViewMain;

/**
 * Class for main app.
 */
public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * JavaFX entry point.
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		// enable / disable Debug mode
		Debug.enabled = true;
		
		// initialize translation singleton
		Translation.getInstance();
		
		// setup panes
		StackPane stackPane = new StackPane();
		BorderPane borderPane = new BorderPane();
		stackPane.getChildren().add(borderPane);
		
		// setup loader
		Loader.setPane(stackPane);
		
		// initialize main view
		new ViewMain().init(borderPane, null);
		
		// setup scene
		Scene scene = new Scene(stackPane, 800, 800);
		stage.setTitle("V" + Translation.fetch("version") + " " + Translation.fetch("main_window_title"));
		stage.getIcons().add(new Image("file:icon.png"));
		stage.setScene(scene);
		stage.show();
	}
}
