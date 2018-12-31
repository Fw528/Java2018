package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	//private StartPageController startPageController;
	
	public static void main(String[] args) {
        launch(args);
    }

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("葫芦娃再次来袭");
		StartPageController startPageController = new StartPageController();
		startPageController.init(primaryStage);
    }
}
