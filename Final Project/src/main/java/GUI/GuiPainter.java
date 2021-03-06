package GUI;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import loginfo.FrameInfo;
import space.BattleField;

import javafx.scene.canvas.Canvas;

public class GuiPainter implements Runnable {
	private BattleField battleField;
	private Canvas battleFieldCanvas;
	
	boolean isKilled = false;
	
	ArrayList<FrameInfo> frameInfos;
	
	public GuiPainter(Canvas canvas, BattleField field) {
		battleFieldCanvas = canvas;
		battleField = field;
	}
	
	public void drawBattleField() {
		battleField.guiDisplay(battleFieldCanvas, frameInfos);
	}

	@Override
	public void run() {
		System.out.println("guiPainter is running");
		frameInfos = new ArrayList<>();
		while (!isKilled) {
			synchronized (battleField) {
				drawBattleField();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("gui线程退出");
	}
	
	public void kill() {
		isKilled = true;
	}
	
	public ArrayList<FrameInfo> getFrameInfos() {
		return frameInfos;
	}
}
