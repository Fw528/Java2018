package GUI;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import space.HuluWorld;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class MainController implements Initializable {

	@FXML
	private Pane pane;
	@FXML
	private Button ChangsheButton;
	@FXML
	private Button HeyiButton;
	@FXML
	private Button FangmenButton;
	@FXML
	private Button HengeButton;

	@FXML
	private Button replayGameBtn;   //没写好
	@FXML
	private Button startBattleBtn;
	@FXML
	private Button restartGameBtn;   //任何时候都可以重新开始
	@FXML
	private Canvas battleFieldCanvas;
	@FXML
	private Button saveLogBtn;
	@FXML
	private Button discardBtn;

	
	private HuluWorld huluWorld;

	ReplayPainter replayPainter;
	String form;

	public MainController() {
		//默认以长蛇初始化
		form ="Changshe";
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		huluWorld = new HuluWorld(battleFieldCanvas, saveLogBtn, discardBtn);
		pane.setFocusTraversable(true);

	}

	@FXML
	private void ChangsheAction(){
		System.out.println("选择长蛇阵型！");
		form ="Changshe";
		huluWorld.setFormname(form);
		huluWorld.ChangeFormation();
	}

	@FXML
	private void HeyiAction(){
		System.out.println("选择鹤翼阵型！");
		form ="Heyi";
		huluWorld.setFormname(form);
		huluWorld.ChangeFormation();
	}

	@FXML
	private void FangmenAction(){
		System.out.println("选择方门阵型！");
		form ="Fangmen";
		huluWorld.setFormname(form);
		huluWorld.ChangeFormation();
	}

	@FXML
	private void HengeAction(){
		System.out.println("选择衡轭阵型！");
		form ="Henge";
		huluWorld.setFormname(form);
		huluWorld.ChangeFormation();
	}
	
	@FXML
	private void handleRestartGame() {
		System.out.println("按下了重新开始");
		if (replayPainter != null) {
			replayPainter.kill();
		}

		startBattleBtn.setDisable(false);
		replayGameBtn.setDisable(false);
		restartGameBtn.setDisable(false);
		
		saveLogBtn.setVisible(false);
		discardBtn.setVisible(false);
		
		huluWorld.killAllTheThread();
		huluWorld = new HuluWorld(battleFieldCanvas, saveLogBtn, discardBtn);
	}
	
	@FXML
	public void handleReplayGame() {
		System.out.println("游戏回放");
	}
	
	@FXML
	private void handleStartBattle() {
		System.out.println("开始战斗");
		/* 开始战斗后不可变阵，也不可再点击开始战斗，也不可回放 */

		ChangsheButton.setDisable(true);
		HengeButton.setDisable(true);
		FangmenButton.setDisable(true);
		HeyiButton.setDisable(true);

		replayGameBtn.setDisable(true);
		startBattleBtn.setDisable(true);
		ExecutorService game = Executors.newSingleThreadExecutor();
		game.execute(huluWorld);
		game.shutdown();
	}
	
	@FXML
	private void handleKeyPressEvent(KeyEvent event) {
		/**
		 * 题目中要求完成的空格键和L建方别读档和开始
		 */
		System.out.println("按下了"+event.getCode()+"键");
    	if(event.getCode() == KeyCode.SPACE) {
    		handleStartBattle();
    	} else if (event.getCode() == KeyCode.L) {
    		handleReplayGame();
    	}
	}
	
	public void killAllThread() {
		System.out.println("killAllThread");

		if (replayPainter != null) {
			replayPainter.kill();
		}
		huluWorld.killAllTheThread();
	}
	
	@FXML
	private void handleSaveLog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存记录");
		/**
		 * 打开文件夹展示保存地点
		 */
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("游戏记录文件", "*.record");
		fileChooser.getExtensionFilters().add(fileExtensions);

		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		System.out.println(currentPath);
		fileChooser.setInitialDirectory(new File(currentPath));
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				huluWorld.saveGameLog(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("保存成功");
			handleRestartGame();
		}

	}
	
	@FXML
	private void handleDiscard() {
		handleRestartGame();
	}
}
