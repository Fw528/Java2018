package GUI;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartPageController {

	@FXML
	private Button Bstart;
	@FXML
	private Button replayBtn;

	private MainController mainController;
	
	public void init(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("startpage.fxml"));
		root.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());	
		Scene scene = new Scene(root,980,600);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	@FXML
	private void handleStartGame() throws IOException {
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("mainwindow.fxml"));
		Parent root = fxmlLoader.load();

		root.getStylesheets().add(getClass().getClassLoader().getResource("mainwindow.css").toExternalForm());
        Scene scene = new Scene(root,980,600);
		Stage stage = (Stage)Bstart.getScene().getWindow();
		stage.setScene(scene);
		stage.show();


		mainController = (MainController)fxmlLoader.getController();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("监听到窗口关闭");
                mainController.killAllThread();
            }
        });
	}
	
	@FXML
	private void handleReplayGame() throws IOException {
		/*
		handleStartGame();
		if (mainWindowController != null) {
			mainWindowController.handleReplayGame();
		}*/
	}

}
