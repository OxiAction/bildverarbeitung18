import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.Debug;
import utils.Translation;
import views.ViewMain;

/**
 * Main app entry point
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(final Stage stage) throws Exception {
		// enable / disable Debug mode
		Debug.enabled = false;
		
		// initialize translation singleton
		Translation.getInstance();
		
		BorderPane borderPane = new BorderPane();
		
		// initialize main view
		new ViewMain().init(borderPane, null);
		
		Scene scene = new Scene(borderPane, 800, 800);
		stage.setTitle("V" + Translation.fetch("version") + " " + Translation.fetch("main_window_title"));
		stage.getIcons().add(new Image("file:icon.png"));
		stage.setScene(scene);
		stage.show();
	}

}
