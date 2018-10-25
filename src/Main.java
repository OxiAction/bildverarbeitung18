import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import utils.Translation;
import views.ViewMain;

import config.Config;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(final Stage stage) throws Exception {
		Config.stage = stage;
		
		// initialize translation singleton
		Translation.getInstance();
		
		BorderPane borderPane = new BorderPane();
		
		// initialize main view
		new ViewMain().init(borderPane, null);
		
		Scene scene = new Scene(borderPane, 500, 400);
		stage.setTitle("V" + Translation.fetch("version") + " " + Translation.fetch("main_window_title"));
		stage.getIcons().add(new Image("file:icon.png"));
		stage.setScene(scene);
		stage.show();
	}

}
